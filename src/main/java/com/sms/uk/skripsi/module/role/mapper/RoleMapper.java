package com.sms.uk.skripsi.module.role.mapper;

import com.sms.uk.skripsi.core.util.DateTimeUtil;
import com.sms.uk.skripsi.module.role.dtos.RoleResponse;
import com.sms.uk.skripsi.module.role.entities.Roles;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public RoleResponse convertEntityToResponse(Roles roles){

        return RoleResponse.builder()
                .roleId(roles.getRoleId())
                .name(roles.getName())
                .description(roles.getDescription())
                .createdAt(DateTimeUtil.convertToDetailDateTime(roles.getCreatedAt()))
                .createdBy(roles.getCreatedBy())
                .updatedAt(DateTimeUtil.convertToDetailDateTime(roles.getUpdatedAt()))
                .updatedBy(roles.getUpdatedBy())
                .build();
    }
}
