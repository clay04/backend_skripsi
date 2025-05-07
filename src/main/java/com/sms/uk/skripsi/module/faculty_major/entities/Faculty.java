package com.sms.uk.skripsi.module.faculty_major.entities;

import com.sms.uk.skripsi.base.models.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.Where;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "t_faculty")
@Where(clause = "is_deleted = false")
public class Faculty extends BaseEntity<String> {

    @Id
    @UuidGenerator
    @Column(name = "uuid")
    private String uuid;

    @Column(name = "faculty_code")
    private String facultyCode;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "faculty")
    private List<Major> majors;
}
