package com.sms.uk.skripsi.module.role.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PermissionGroupResponse {

    private String uuid;

    private String name;

    private boolean isCreate;

    private boolean isRead;

    private boolean isUpdate;

    private boolean isDelete;
}
