package com.sms.uk.skripsi.module.scholarship.dtos;

import lombok.Data;

@Data
public class ScholarshipFilterRequest {

    private Integer page;

    private Integer size;

    private String sortBy;

    private String orderBy;

    private String searchByNim;

    private String searchByNoRegis;

    private String searchByLastName;

    private String searchByFirstName;

    private String searchByType;

    private String searchByStatus;

    private String searchByMajor;

    private String searchByFaculty;
}
