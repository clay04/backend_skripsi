package com.sms.uk.skripsi.module.user.entities;

import com.sms.uk.skripsi.base.models.BaseEntity;
import com.sms.uk.skripsi.module.scholarship.entities.Scholarship;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "t_user")
@Where(clause = "is_deleted = false")
public class MasterUser extends BaseEntity<String> {

    @Id
    @UuidGenerator
    @Column(name = "uuid")
    private String uuid;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "age")
    private Integer age;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "user_name")
    private String username;

    @Column(name = "role_id")
    private String roleId;

    @Column(unique = true, name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "token")
    private String token;

    @Column(name = "exp_token")
    private LocalDateTime expToken;

    @Column(name = "verification_code")
    private String verificationCode;

    @Column(name = "exp_verification_code")
    private LocalDateTime expVerificationCode;

    @Column(name = "status")
    private boolean status;

    @OneToMany(mappedBy = "user")
    private List<Scholarship> scholarships;
}
