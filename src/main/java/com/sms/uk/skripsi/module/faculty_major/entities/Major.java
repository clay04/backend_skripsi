package com.sms.uk.skripsi.module.faculty_major.entities;

import com.sms.uk.skripsi.base.models.BaseEntity;
import com.sms.uk.skripsi.module.scholarship.entities.Scholarship;
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
@Table(name = "t_major")
@Where(clause = "is_deleted = false")
public class Major extends BaseEntity<String> {

    @Id
    @UuidGenerator
    @Column(name = "uuid")
    private String uuid;

    @Column(name = "major_code")
    private String majorCode;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "faculity_uuid", referencedColumnName = "uuid")
    private Faculty faculty;

    @OneToMany(mappedBy = "major")
    private List<Scholarship> scholarships;

}
