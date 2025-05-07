package com.sms.uk.skripsi.module.faculty_major.services;


import com.sms.uk.skripsi.module.faculty_major.dtos.MajorRequest;
import com.sms.uk.skripsi.module.faculty_major.entities.Major;
import org.springframework.data.domain.Page;

public interface MajorService {

    Major create(MajorRequest request);

    Major update(MajorRequest request);

    boolean delete(String uuid);

    Major detail(String uuid);

    Page<Major> paging(Integer page, Integer size, String sortBy, String orderBy, String searchByCode, String searchByName);
}
