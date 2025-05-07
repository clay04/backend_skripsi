package com.sms.uk.skripsi.module.user.dtos;

import com.sms.uk.skripsi.module.role.dtos.RoleResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MasterUserResponse {

    private String uuid;

    private String firstName;

    private String lastName;

    private String userName;

    private String phoneNumber;

    private String email;

    private RoleResponse role;

    private Boolean status;

    private String createdAt;

    private String createdBy;

    private String updatedAt;

    private String updatedBy;
}
