package com.sms.uk.skripsi.module.faculty_major.services;

import com.sms.uk.skripsi.module.faculty_major.dtos.FacultyRequest;
import com.sms.uk.skripsi.module.faculty_major.entities.Faculty;
import org.springframework.data.domain.Page;

public interface FacultyService {

    Faculty create(FacultyRequest request);

    Faculty update(FacultyRequest request);

    boolean delete(String uuid);

    Faculty detail(String uuid);

    Page<Faculty> paging(Integer page, Integer size, String sortBy, String orderBy, String searchByCode, String searchByName);
}
