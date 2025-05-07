package com.sms.uk.skripsi.module.user.service.impl;

import com.sms.uk.skripsi.base.exception.BaseException;
import com.sms.uk.skripsi.config.response_messages.localization_messages.EnumMessagesKey;
import com.sms.uk.skripsi.core.util.DateTimeUtil;
import com.sms.uk.skripsi.core.util.PagingUtil;
import com.sms.uk.skripsi.module.email.service.EmailService;
import com.sms.uk.skripsi.module.user.dtos.MasterUserRequest;
import com.sms.uk.skripsi.module.user.entities.MasterUser;
import com.sms.uk.skripsi.module.user.mapper.UserManagementMapper;
import com.sms.uk.skripsi.module.user.repositories.MasterUserRepository;
import com.sms.uk.skripsi.module.user.service.UserService;
import com.sms.uk.skripsi.module.user.spec.MasterUserSpecification;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final MasterUserRepository repository;
    private final MasterUserSpecification specification;
    private final EmailService emailService;
    private final UserManagementMapper mapper;

    @Override
    @Transactional
    public MasterUser create(MasterUserRequest request) throws MessagingException {

        log.info("Create user request: {}", request.toString());
        validateRequest(request);

        MasterUser user = mapper.convertRequestToEntity(request);
        String createPasswordCode = generateVerificationCode();
        user.setVerificationCode(createPasswordCode);
        user.setExpVerificationCode(DateTimeUtil.getCurrentDateTimePlusOneHour());

        // Send email registration
        emailService.sendEmailRegistration(user);

        return repository.save(user);
    }

    @Override
    @Transactional
    public MasterUser update(MasterUserRequest request) {

        log.info("Update user request: {}", request.toString());
        validateRequestUpdateUser(request);
        MasterUser updateMasterUser = this.detail(request.getUuid());

        MasterUser user = mapper.convertRequestToUpdateEntity(request, updateMasterUser);

        return repository.save(user);
    }

    @Override
    public Page<MasterUser> paging(Integer page, Integer size, String sortBy, String orderBy, String searchByRoleId, String searchByFirstName, String searchByLastName) {

        try {

            // build page request
            PageRequest pageRequest = PagingUtil.buildPageRequest(page, size);

            List<Pair<String, String>> sorts = new ArrayList<>();

            // set default sort & order direction
            if (StringUtil.isBlank(sortBy) && StringUtil.isBlank(orderBy)){

                sorts.add(Pair.of("lastName", "ascend"));
            }else {
                sorts.add(Pair.of(sortBy, orderBy));
            }

            Sort sort = PagingUtil.listToSort(sorts);

            // spec for feature search
            Specification<MasterUser> spec = specification.search(searchByRoleId, searchByFirstName, searchByLastName);

            return repository.findAll(spec, pageRequest.withSort(sort));
        }catch (Exception e){
            log.error("Error getting paging data with params, page: {}, size: {}, sortBy: {}, orderBy: {}",
                    page, size, sortBy, orderBy);
            throw new BaseException(EnumMessagesKey.ERROR_DEFAULT);
        }
    }

    @Override
    @Transactional
    public boolean delete(String uuid) {

        MasterUser masterUser = this.getDetail(uuid);

        try {

            masterUser.setDeleted(true);
            masterUser.setDeletedAt(LocalDateTime.now());
            repository.save(masterUser);

            return true;
        }catch (Exception e){
            throw new BaseException(EnumMessagesKey.ERROR_DEFAULT);
        }
    }

    @Override
    public MasterUser detail(String uuid) {
        return this.getDetail(uuid);
    }


    private MasterUser getDetail(String uuid){

        return repository.findById(uuid)
                .orElseThrow(() -> new BaseException(EnumMessagesKey.USER_MASTER_NOT_FOUND));
    }

    private void validateUserFields(String email, String phoneNumber, String username, String uuid, boolean isUpdate) {
        if (isUpdate) {
            if (repository.existsByEmailIgnoreCaseAndUuidNot(email, uuid)) {
                throw new BaseException(EnumMessagesKey.EMAIL_ALREADY_EXISTS);
            }
            if (repository.existsByPhoneNumberAndUuidNot(phoneNumber, uuid)) {
                throw new BaseException(EnumMessagesKey.PHONE_NUMBER_ALREADY_EXIST);
            }
        } else {
            if (repository.existsByEmailIgnoreCase(email)) {
                throw new BaseException(EnumMessagesKey.EMAIL_ALREADY_EXISTS);
            }
            if (repository.existsByPhoneNumber(phoneNumber)) {
                throw new BaseException(EnumMessagesKey.PHONE_NUMBER_ALREADY_EXIST);
            }
            if (repository.existsByUsernameIgnoreCase(username)) {
                throw new BaseException(EnumMessagesKey.USER_NAME_ALREADY_EXISTS);
            }
        }
    }

    private void validateRequest(MasterUserRequest request) {
        validateUserFields(request.getEmail(), request.getPhoneNumber(), request.getUsername(), null, false);
    }

    private void validateRequestUpdateUser(MasterUserRequest request) {
        validateUserFields(request.getEmail(), request.getPhoneNumber(), request.getUsername(), request.getUuid(), true);
    }


    private String generateVerificationCode() {
        return java.util.UUID.randomUUID().toString();
    }
}
