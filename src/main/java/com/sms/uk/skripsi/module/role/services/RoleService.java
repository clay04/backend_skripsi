package com.sms.uk.skripsi.module.role.services;

import com.sms.uk.skripsi.module.role.dtos.RoleRequest;
import com.sms.uk.skripsi.module.role.entities.Roles;

import java.util.List;

public interface RoleService {

    Roles create(RoleRequest request);

    Roles update(RoleRequest request);

    List<Roles> list(String roleId);

    Roles detail(String roleId);

    boolean delete(String roleId);
}
