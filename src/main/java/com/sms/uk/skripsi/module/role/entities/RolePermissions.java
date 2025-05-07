package com.sms.uk.skripsi.module.role.entities;

import com.sms.uk.skripsi.base.models.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "t_roles_permissions")
@Where(clause = "is_deleted = false")
public class RolePermissions extends BaseEntity<String> {

    @Id
    @Column(name = "role_permission_id")
    private String rolePermissionId;

    @Column(name = "role_id")
    private String roleId;

    @Column(name = "permission_id")
    private String permissionId;
}
