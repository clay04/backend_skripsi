package com.sms.uk.skripsi.module.scholarship.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ScholarshipRequest {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("nim")
    private String nim;

    @JsonProperty("nomor_registrasi")
    private String noRegistration;

    @JsonProperty("scholarship_type")
    private String scholarshipType;

    @JsonProperty("address_line_1")
    private String addressLine1;

    @JsonProperty("address_line_2")
    private String addressLine2;

    @JsonProperty("high_school_name")
    private String highSchoolName;

    @JsonProperty("father_name")
    private String fatherName;

    @JsonProperty("mother_name")
    private String motherName;

    @JsonProperty("major_id")
    private String majorId;

    @Column(name = "document_completion_status")
    private Boolean documentCompletionStatus;
}
