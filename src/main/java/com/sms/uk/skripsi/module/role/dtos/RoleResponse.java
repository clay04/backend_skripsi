package com.sms.uk.skripsi.module.role.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleResponse {

    private String roleId;

    private String name;

    private String description;

    private String createdAt;

    private String createdBy;

    private String updatedAt;

    private String updatedBy;
}
