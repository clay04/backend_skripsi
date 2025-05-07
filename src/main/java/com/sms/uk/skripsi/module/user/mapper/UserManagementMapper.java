package com.sms.uk.skripsi.module.user.mapper;

import com.sms.uk.skripsi.base.exception.BaseException;
import com.sms.uk.skripsi.config.response_messages.localization_messages.EnumMessagesKey;
import com.sms.uk.skripsi.core.util.DateTimeUtil;
import com.sms.uk.skripsi.core.util.PasswordUtil;
import com.sms.uk.skripsi.module.role.entities.Roles;
import com.sms.uk.skripsi.module.role.mapper.RoleMapper;
import com.sms.uk.skripsi.module.role.repositories.RolesRepository;
import com.sms.uk.skripsi.module.user.dtos.MasterUserResponse;
import com.sms.uk.skripsi.module.user.entities.MasterUser;
import com.sms.uk.skripsi.module.user.dtos.MasterUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserManagementMapper {

    private final RolesRepository rolesRepository;
    private final RoleMapper roleMapper;

    public MasterUser convertRequestToEntity(MasterUserRequest request) {
        return MasterUser.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .username(request.getUsername())
                .roleId(request.getRoleId())
                .email(request.getNonStudentEmail())
                .password(PasswordUtil.generatePassword())
                .status(true)
                .build();
    }

    public MasterUser convertRequestToUpdateEntity(MasterUserRequest request, MasterUser updateMasterUser) {

        return MasterUser.builder()
                .uuid(updateMasterUser.getUuid())
                .username(updateMasterUser.getUsername())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getNonStudentEmail())
                .roleId(updateMasterUser.getRoleId())
                .password(updateMasterUser.getPassword())
                .token(updateMasterUser.getToken())
                .expToken(updateMasterUser.getExpToken())
                .status(updateMasterUser.isStatus())
                .createdAt(updateMasterUser.getCreatedAt())
                .createdBy(updateMasterUser.getCreatedBy())
                .deletedAt(updateMasterUser.getDeletedAt())
                .isDeleted(updateMasterUser.isDeleted())
                .deletedBy(updateMasterUser.getDeletedBy())
                .build();
    }

    public MasterUserResponse convertEntityToResponse(MasterUser user) {

        Roles roles = rolesRepository.findById(user.getRoleId())
                .orElseThrow(() -> new BaseException(EnumMessagesKey.ERROR_ENTITY_NOT_FOUND));

        return MasterUserResponse.builder()
                .uuid(user.getUuid())
                .userName(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .role(roleMapper.convertEntityToResponse(roles))
                .email(user.getEmail())
                .status(user.isStatus())
                .createdAt(DateTimeUtil.convertToDetailDateTime(user.getCreatedAt()))
                .createdBy(user.getCreatedBy())
                .updatedAt(DateTimeUtil.convertToDetailDateTime(user.getUpdatedAt()))
                .updatedBy(user.getUpdatedBy())
                .build();
    }
}
