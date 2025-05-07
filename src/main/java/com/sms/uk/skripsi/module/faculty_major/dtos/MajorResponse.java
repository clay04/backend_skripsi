package com.sms.uk.skripsi.module.faculty_major.dtos;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MajorResponse {

    private String uuid;

    private String facultyName;

    private String facultyCode;

    private String majorCode;

    private String majorName;

    private String createdAt;

    private String createdBy;

    private String updatedAt;

    private String updatedBy;
}
