package com.sms.uk.skripsi.module.user.repositories;

import com.sms.uk.skripsi.module.user.entities.MasterUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MasterUserRepository extends JpaRepository<MasterUser, String>, JpaSpecificationExecutor<MasterUser> {
    Optional<MasterUser> findByUsernameIgnoreCase(String username);

    Optional<MasterUser> findByEmail(String email);

    Optional<MasterUser> findByUsername(String username);

    Optional<MasterUser> findByVerificationCode(String token);

    @Query("select (count(m) > 0) from MasterUser m where upper(m.email) = upper(?1)")
    boolean existsByEmailIgnoreCase(String email);

    @Query("select (count(m) > 0) from MasterUser m where m.phoneNumber = ?1")
    boolean existsByPhoneNumber(String phoneNumber);

    @Query("select (count(m) > 0) from MasterUser m where upper(m.username) = upper(?1)")
    boolean existsByUsernameIgnoreCase(String userName);

    @Query("select (count(m) > 0) from MasterUser m where upper(m.email) = upper(?1) and m.uuid <> ?2")
    boolean existsByEmailIgnoreCaseAndUuidNot(String email, String uuid);

    @Query("select (count(m) > 0) from MasterUser m where m.phoneNumber = ?1 and m.uuid <> ?2")
    boolean existsByPhoneNumberAndUuidNot(String phoneNumber, String uuid);
}