package com.sms.uk.skripsi.module.authentication.mapper;

import com.sms.uk.skripsi.base.exception.BaseException;
import com.sms.uk.skripsi.config.response_messages.localization_messages.EnumMessagesKey;
import com.sms.uk.skripsi.core.util.DateTimeUtil;
import com.sms.uk.skripsi.core.util.PasswordUtil;
import com.sms.uk.skripsi.module.authentication.dtos.LoginResponse;
import com.sms.uk.skripsi.module.role.entities.Roles;
import com.sms.uk.skripsi.module.role.mapper.RoleMapper;
import com.sms.uk.skripsi.module.role.repositories.RolesRepository;
import com.sms.uk.skripsi.module.user.dtos.MasterUserRequest;
import com.sms.uk.skripsi.module.user.entities.MasterUser;
import com.sms.uk.skripsi.module.user.dtos.MasterUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationMapper {

    private final RolesRepository rolesRepository;
    private final RoleMapper roleMapper;

    public LoginResponse convertEntityToResponse(MasterUser user){
        return LoginResponse.builder()
                .user(this.convertUserEntityToResponse(user))
                .token(user.getToken())
                .build();
    }

    private MasterUserResponse convertUserEntityToResponse(MasterUser user){

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

    public MasterUser convertUserRequestToEntity(MasterUserRequest request) {
        return MasterUser.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .username(request.getUsername())
                .roleId(request.getRoleId())
                .email(request.getEmail())
                .password(PasswordUtil.generatePassword())
                .status(false)
                .createdBy(request.getUsername())
                .updatedBy(request.getUsername())
                .build();
    }
}
