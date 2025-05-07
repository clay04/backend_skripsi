package com.sms.uk.skripsi.module.scholarship.services.impl;

import com.sms.uk.skripsi.base.exception.BaseException;
import com.sms.uk.skripsi.config.response_messages.localization_messages.EnumMessagesKey;
import com.sms.uk.skripsi.core.util.PagingUtil;
import com.sms.uk.skripsi.core.util.UserUtil;
import com.sms.uk.skripsi.module.document.entities.Document;
import com.sms.uk.skripsi.module.document.repository.DocumentRepository;
import com.sms.uk.skripsi.module.email.service.EmailService;
import com.sms.uk.skripsi.module.scholarship.constant.ScholarshipConstant;
import com.sms.uk.skripsi.module.scholarship.dtos.ScholarshipFilterRequest;
import com.sms.uk.skripsi.module.scholarship.dtos.ScholarshipRequest;
import com.sms.uk.skripsi.module.scholarship.entities.Scholarship;
import com.sms.uk.skripsi.module.scholarship.mapper.ScholarshipMapper;
import com.sms.uk.skripsi.module.scholarship.repositories.ScholarshipRepository;
import com.sms.uk.skripsi.module.scholarship.services.ScholarshipService;
import com.sms.uk.skripsi.module.scholarship.spec.ScholarshipSpecification;
import com.sms.uk.skripsi.module.user.entities.MasterUser;
import com.sms.uk.skripsi.module.user.repositories.MasterUserRepository;
import jakarta.mail.MessagingException;
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

import static com.sms.uk.skripsi.module.document.constant.DocumentConstants.REQUIRED_CATEGORIES;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScholarshipServiceImpl implements ScholarshipService {
    private final MasterUserRepository masterUserRepository;

    private final ScholarshipRepository repository;
    private final ScholarshipMapper mapper;
    private final ScholarshipSpecification specification;
    private final EmailService emailService;
    private final DocumentRepository documentRepository;

    @Override
    public Scholarship create(ScholarshipRequest request) {

        log.info("scholarship create request: {}", request);

        MasterUser masterUser = masterUserRepository.findByUsername(UserUtil.principal().getName())
                .orElseThrow(() -> new BaseException(EnumMessagesKey.ERROR_NOT_FOUND));

        this.isExistByUser(request, masterUser);
        Scholarship scholarship = mapper.convertRequestToEntity(request);

        return repository.save(scholarship);
    }

    @Override
    public Page<Scholarship> paging(ScholarshipFilterRequest filterRequest) {
        try {

            // build page request
            PageRequest pageRequest = PagingUtil.buildPageRequest(filterRequest.getPage(), filterRequest.getSize());

            List<Pair<String, String>> sorts = new ArrayList<>();

            // set default sort & order direction
            if (StringUtil.isBlank(filterRequest.getSortBy()) && StringUtil.isBlank(filterRequest.getOrderBy())){

                sorts.add(Pair.of("user.lastName", "ascend"));
            }else {
                sorts.add(Pair.of(filterRequest.getSortBy(), filterRequest.getOrderBy()));
            }

            Sort sort = PagingUtil.listToSort(sorts);

            Specification<Scholarship> spec = specification.search(filterRequest)
                    .and(specification.searchByUser(filterRequest))
                    .and(specification.searchByMajor(filterRequest));

            return repository.findAll(spec, pageRequest.withSort(sort));
        }catch (Exception e){
            log.error("Error getting paging data with params, page: {}, size: {}, sortBy: {}, orderBy: {}",
                    filterRequest.getPage(), filterRequest.getSize(), filterRequest.getSortBy(), filterRequest.getOrderBy());
            throw new BaseException(EnumMessagesKey.ERROR_DEFAULT);
        }
    }

    @Override
    public Scholarship approveReject(String uuid, boolean isApproved) throws MessagingException {
        Scholarship scholarship = this.getDetail(uuid);

        if (isApproved){

            scholarship.setStatus(ScholarshipConstant.STATUS_APPROVED);
        }else {

            scholarship.setStatus(ScholarshipConstant.STATUS_REJECTED);
        }

        emailService.sendEmailApprovalNotification(scholarship.getUser(), isApproved);
        return repository.save(scholarship);
    }

    @Override
    public Scholarship update(ScholarshipRequest request) {

        log.info("scholarship update request: {}", request);

        MasterUser masterUser = masterUserRepository.findByUsername(UserUtil.principal().getName())
                .orElseThrow(() -> new BaseException(EnumMessagesKey.ERROR_NOT_FOUND));

        this.isExistByUser(request, masterUser);

        Scholarship scholarship = this.getDetail(request.getUuid());

        Scholarship scholarshipAboutToEdit = mapper.convertRequestToUpdateEntity(request, scholarship);

        return repository.save(scholarshipAboutToEdit);
    }

    @Override
    public boolean delete(String uuid) {

        Scholarship scholarship = this.getDetail(uuid);

        try {

            scholarship.setDeleted(true);
            scholarship.setDeletedAt(LocalDateTime.now());
            repository.save(scholarship);

            return true;
        } catch (Exception e) {
            throw new BaseException(EnumMessagesKey.ERROR_DEFAULT);
        }
    }

    @Override
    public Scholarship detail(String uuid) {
        return this.getDetail(uuid);
    }

    private Scholarship getDetail(String uuid) {

        return repository.findById(uuid)
                .orElseThrow(() -> new BaseException(EnumMessagesKey.ERROR_NOT_FOUND));
    }

    private void isExistByUser(ScholarshipRequest request, MasterUser masterUser) {

        boolean isExist = request.getUuid() == null ? repository.existsByUserUuid(masterUser.getUuid())
                : repository.existsByUserUuidAndUuidNot(masterUser.getUuid(), request.getUuid());

        if (isExist) {
            throw new BaseException(EnumMessagesKey.SCHOLARSHIP_APPLICATION_ALREADY_EXIST);
        }
    }

}
