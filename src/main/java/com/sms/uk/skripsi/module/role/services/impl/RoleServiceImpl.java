package com.sms.uk.skripsi.module.role.services.impl;

import com.sms.uk.skripsi.base.exception.BaseException;
import com.sms.uk.skripsi.config.response_messages.localization_messages.EnumMessagesKey;
import com.sms.uk.skripsi.core.util.UserUtil;
import com.sms.uk.skripsi.module.role.dtos.RoleRequest;
import com.sms.uk.skripsi.module.role.entities.Roles;
import com.sms.uk.skripsi.module.role.repositories.RolesRepository;
import com.sms.uk.skripsi.module.role.services.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {
    private final RolesRepository rolesRepository;

    @Override
    public Roles create(RoleRequest request) {
        return null;
    }

    @Override
    public Roles update(RoleRequest request) {
        return null;
    }

    @Override
    public List<Roles> list(String roleId) {
        return rolesRepository.findAllByRoleIdNot(roleId);
    }

    @Override
    public Roles detail(String roleId) {
        return this.getDetail(roleId);
    }

    @Override
    public boolean delete(String roleId) {

        Roles roles = this.getDetail(roleId);

        try {

            roles.setDeleted(true);
            roles.setDeletedAt(LocalDateTime.now());
            roles.setDeletedBy(UserUtil.principal().getName());

            rolesRepository.save(roles);
        }catch (Exception e){
            log.error("Error deleting role with id: {}", roleId);

            throw new BaseException(EnumMessagesKey.ERROR_DEFAULT);
        }
        return true;
    }

    private Roles getDetail(String roleId){
        return rolesRepository.findById(roleId)
                .orElseThrow(() -> new BaseException(EnumMessagesKey.ERROR_NOT_FOUND));
    }
}
