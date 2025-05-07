package com.sms.uk.skripsi.module.faculty_major.services.impl;

import com.sms.uk.skripsi.base.exception.BaseException;
import com.sms.uk.skripsi.config.response_messages.localization_messages.EnumMessagesKey;
import com.sms.uk.skripsi.core.util.PagingUtil;
import com.sms.uk.skripsi.module.faculty_major.dtos.FacultyRequest;
import com.sms.uk.skripsi.module.faculty_major.entities.Faculty;
import com.sms.uk.skripsi.module.faculty_major.mapper.FacultyMapper;
import com.sms.uk.skripsi.module.faculty_major.repositories.FacultyRepository;
import com.sms.uk.skripsi.module.faculty_major.services.FacultyService;
import com.sms.uk.skripsi.module.faculty_major.spec.FacultySpecification;
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
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository repository;
    private final FacultyMapper mapper;
    private final FacultySpecification specification;

    @Override
    public Faculty create(FacultyRequest request) {

        log.info("faculty create request: {}", request);

        this.isExistByFacultyCode(request);
        Faculty faculty = mapper.convertRequestToEntity(request);

        return repository.save(faculty);
    }

    @Override
    public Faculty update(FacultyRequest request) {

        log.info("faculty update request: {}", request);
        this.isExistByFacultyCode(request);

        Faculty faculty = this.getDetail(request.getUuid());

        Faculty facultyAboutToEdit = mapper.convertRequestToUpdateEntity(request, faculty);

        return repository.save(facultyAboutToEdit);
    }

    @Override
    public Page<Faculty> paging(Integer page, Integer size, String sortBy, String orderBy, String searchByCode, String searchByName) {

        try {

            // build page request
            PageRequest pageRequest = PagingUtil.buildPageRequest(page, size);

            List<Pair<String, String>> sorts = new ArrayList<>();

            // set default sort & order direction
            if (StringUtil.isBlank(sortBy) && StringUtil.isBlank(orderBy)){

                sorts.add(Pair.of("facultyCode", "ascend"));
            }else {
                sorts.add(Pair.of(sortBy, orderBy));
            }

            Sort sort = PagingUtil.listToSort(sorts);

            // spec for feature search
            Specification<Faculty> spec = specification.search(searchByCode, searchByName);

            return repository.findAll(spec, pageRequest.withSort(sort));
        }catch (Exception e){
            log.error("Error getting paging data with params, page: {}, size: {}, sortBy: {}, orderBy: {}",
                    page, size, sortBy, orderBy);
            throw new BaseException(EnumMessagesKey.ERROR_DEFAULT);
        }
    }

    @Override
    public boolean delete(String uuid) {

        Faculty faculty = this.getDetail(uuid);

        try {

            faculty.setDeleted(true);
            faculty.setDeletedAt(LocalDateTime.now());
            repository.save(faculty);

            return true;
        }catch (Exception e){
            throw new BaseException(EnumMessagesKey.ERROR_DEFAULT);
        }
    }

    @Override
    public Faculty detail(String uuid) {
        return this.getDetail(uuid);
    }

    private Faculty getDetail(String uuid){

        return repository.findById(uuid)
                .orElseThrow(() -> new BaseException(EnumMessagesKey.ERROR_NOT_FOUND));
    }

    private void isExistByFacultyCode(FacultyRequest request){

        boolean isExist = request.getUuid() == null? repository.existsByFacultyCode(request.getFacultyCode())
                : repository.existsByFacultyCodeAndUuidNot(request.getFacultyCode(), request.getUuid());

        if (isExist){
            throw new BaseException(EnumMessagesKey.ERROR_DUPLICATED_FACULTY_CODE);
        }
    }
}
