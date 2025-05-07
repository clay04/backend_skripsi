package com.sms.uk.skripsi.module.faculty_major.services.impl;

import com.sms.uk.skripsi.base.exception.BaseException;
import com.sms.uk.skripsi.config.response_messages.localization_messages.EnumMessagesKey;
import com.sms.uk.skripsi.core.util.PagingUtil;
import com.sms.uk.skripsi.module.faculty_major.dtos.MajorRequest;
import com.sms.uk.skripsi.module.faculty_major.entities.Major;
import com.sms.uk.skripsi.module.faculty_major.mapper.MajorMapper;
import com.sms.uk.skripsi.module.faculty_major.repositories.MajorRepository;
import com.sms.uk.skripsi.module.faculty_major.services.MajorService;
import com.sms.uk.skripsi.module.faculty_major.spec.MajorSpecification;
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
public class MajorServiceImpl implements MajorService {

    private final MajorRepository repository;
    private final MajorMapper mapper;
    private final MajorSpecification specification;

    @Override
    public Major create(MajorRequest request) {

        log.info("major create request: {}", request);

        this.isExistByMajorCode(request);
        Major major = mapper.convertRequestToEntity(request);

        return repository.save(major);
    }

    private void isExistByMajorCode(MajorRequest request) {

        boolean isExist = request.getUuid() == null? repository.existsByMajorCode(request.getMajorCode())
                : repository.existsByMajorCodeAndUuidNot(request.getMajorCode(), request.getUuid());

        if (isExist){
            throw new BaseException(EnumMessagesKey.ERROR_DUPLICATED_MAJOR_CODE);
        }
    }

    @Override
    public Major update(MajorRequest request) {

        log.info("major update request: {}", request);
        this.isExistByMajorCode(request);

        Major major = this.getDetail(request.getUuid());

        Major majorAboutToEdit = mapper.convertRequestToUpdateEntity(request, major);

        return repository.save(majorAboutToEdit);
    }

    @Override
    public Page<Major> paging(Integer page, Integer size, String sortBy, String orderBy, String searchByCode, String searchByName) {

        try {
            // build page request
            PageRequest pageRequest = PagingUtil.buildPageRequest(page, size);

            List<Pair<String, String>> sorts = new ArrayList<>();

            // daftar kolom yang diperbolehkan untuk sort
            List<String> allowedSortFields = List.of("uuid", "majorCode", "name");

            // validasi sortBy dan orderBy
            if (StringUtil.isBlank(sortBy) || !allowedSortFields.contains(sortBy)) {
                sortBy = "majorCode";
            }

            if (StringUtil.isBlank(orderBy)) {
                orderBy = "ascend";
            }

            sorts.add(Pair.of(sortBy, orderBy));

            Sort sort = PagingUtil.listToSort(sorts);

            // spec for search
            Specification<Major> spec = specification.search(searchByCode, searchByName);

            return repository.findAll(spec, pageRequest.withSort(sort));

        } catch (Exception e) {
            log.error("Error getting paging data with params, page: {}, size: {}, sortBy: {}, orderBy: {}", page, size, sortBy, orderBy, e);
            throw new BaseException(EnumMessagesKey.ERROR_DEFAULT);
        }
    }


    @Override
    public boolean delete(String uuid) {

        Major major = this.getDetail(uuid);

        try {

            major.setDeleted(true);
            major.setDeletedAt(LocalDateTime.now());
            repository.save(major);

            return true;
        }catch (Exception e){
            throw new BaseException(EnumMessagesKey.ERROR_DEFAULT);
        }
    }

    @Override
    public Major detail(String uuid) {
        return this.getDetail(uuid);
    }

    private Major getDetail(String uuid){

        return repository.findById(uuid)
                .orElseThrow(() -> new BaseException(EnumMessagesKey.ERROR_NOT_FOUND));
    }
}
