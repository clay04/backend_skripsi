package com.sms.uk.skripsi.module.scholarship.dtos;

import com.sms.uk.skripsi.module.faculty_major.dtos.MajorResponse;
import com.sms.uk.skripsi.module.user.dtos.MasterUserResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScholarshipResponse {

    private String uuid;

    private String nim;

    private String noRegistration;

    private String scholarshipType;

    private String status;

    private String addressLine1;

    private String addressLine2;

    private String highSchoolName;

    private String fatherName;

    private String motherName;

    private String createdAt;

    private String createdBy;

    private String updatedAt;

    private String updatedBy;

    private MajorResponse major;

    private Boolean documentCompletionStatus;

    private MasterUserResponse masterUser;

}
