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
@Table(name = "t_permission")
@Where(clause = "is_deleted = false")
public class Permission extends BaseEntity<String> {

    @Id
    @Column(name = "permission_id")
    private String permissionId;

    @Column(name = "permission_group_id")
    private String permissionGroupId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}
