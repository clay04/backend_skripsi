package com.sms.uk.skripsi.module.faculty_major.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FacultyResponse {

    private String uuid;

    private String facultyCode;

    private String facultyName;

    private String createdAt;

    private String createdBy;

    private String updatedAt;

    private String updatedBy;
}
