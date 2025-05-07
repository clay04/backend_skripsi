package com.sms.uk.skripsi.module.faculty_major.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FacultyRequest {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("faculty_code")
    private String facultyCode;

    @JsonProperty("faculty_name")
    private String facultyName;
}
