package com.sms.uk.skripsi.module.faculty_major.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MajorRequest {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("major_code")
    private String majorCode;

    @JsonProperty("major_name")
    private String majorName;

    @JsonProperty("faculty_uuid")
    private String facultyUuid;
}
