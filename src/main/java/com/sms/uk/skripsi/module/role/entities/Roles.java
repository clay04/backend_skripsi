package com.sms.uk.skripsi.module.role.entities;

import com.sms.uk.skripsi.base.models.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "t_roles")
@Where(clause = "is_deleted = false")
public class Roles extends BaseEntity<String> {

    @Id
    @Column(name = "role_id")
    private String roleId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}
