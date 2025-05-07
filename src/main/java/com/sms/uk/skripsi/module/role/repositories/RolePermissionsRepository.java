package com.sms.uk.skripsi.module.role.repositories;

import com.sms.uk.skripsi.module.role.entities.RolePermissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionsRepository extends JpaRepository<RolePermissions, String> {
}