package com.sms.uk.skripsi.module.scholarship.entities;

import com.sms.uk.skripsi.base.models.BaseEntity;
import com.sms.uk.skripsi.module.faculty_major.entities.Major;
import com.sms.uk.skripsi.module.user.entities.MasterUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "t_scholarship")
@Where(clause = "is_deleted = false")
public class Scholarship extends BaseEntity<String> {

    @Id
    @UuidGenerator
    @Column(name = "uuid")
    private String uuid;

    @Column(name = "nim")
    private String nim;

    @Column(name = "nomor_registrasi")
    private String noRegistration;

    @Column(name = "scholarship_type")
    private String scholarshipType;

    @Column(name = "status")
    private String status;

    @Column(name = "address_line_1")
    private String addressLine1;

    @Column(name = "address_line_2")
    private String addressLine2;

    @Column(name = "highschool_name")
    private String highschoolName;

    @Column(name = "father_name")
    private String fatherName;

    @Column(name = "mother_name")
    private String motherName;

    @ManyToOne
    @JoinColumn(referencedColumnName = "uuid", name = "user_uuid")
    private MasterUser user;

    @ManyToOne
    @JoinColumn(name = "major_uuid", referencedColumnName = "uuid")
    private Major major;

    @Column(name = "document_completion_status")
    private Boolean documentCompletionStatus;

}
