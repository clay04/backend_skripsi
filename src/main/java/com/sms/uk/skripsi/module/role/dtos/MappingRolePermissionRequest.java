package com.sms.uk.skripsi.module.role.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MappingRolePermissionRequest {

    @JsonProperty("role_uuid")
    private String roleUuid;

    @JsonProperty("permission_uuid")
    private String permissionUuid;
}
