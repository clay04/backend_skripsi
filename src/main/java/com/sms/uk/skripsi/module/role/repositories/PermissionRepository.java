package com.sms.uk.skripsi.module.role.repositories;

import com.sms.uk.skripsi.module.role.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
}