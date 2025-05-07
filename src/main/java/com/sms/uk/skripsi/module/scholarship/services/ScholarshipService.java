package com.sms.uk.skripsi.module.scholarship.services;


import com.sms.uk.skripsi.module.scholarship.dtos.ScholarshipFilterRequest;
import com.sms.uk.skripsi.module.scholarship.dtos.ScholarshipRequest;
import com.sms.uk.skripsi.module.scholarship.entities.Scholarship;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;


public interface ScholarshipService {

    Scholarship create(ScholarshipRequest request);

    Page<Scholarship> paging(ScholarshipFilterRequest filterRequest);

    Scholarship approveReject(String uuid, boolean isApproved) throws MessagingException;

    Scholarship update(ScholarshipRequest request);

    boolean delete(String uuid);

    Scholarship detail(String uuid);

    //Object updateScholarshipDocStatus(String uploadedBy);
}
