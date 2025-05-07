package com.sms.uk.skripsi.module.role.repositories;

import com.sms.uk.skripsi.module.role.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RolesRepository extends JpaRepository<Roles, String> {

    @Query("select r from Roles r where r.roleId <> ?1")
    List<Roles> findAllByRoleIdNot(String roleId);

    boolean existsByRoleId(String roleId);
}