package com.sms.uk.skripsi.module.faculty_major.repositories;

import com.sms.uk.skripsi.module.faculty_major.entities.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MajorRepository extends JpaRepository<Major, String>, JpaSpecificationExecutor<Major> {

    @Query("select (count(m) > 0) from Major m where m.majorCode = ?1")
    boolean existsByMajorCode(String majorCode);

    @Query("select (count(m) > 0) from Major m where m.majorCode = ?1 and m.uuid <> ?2")
    boolean existsByMajorCodeAndUuidNot(String majorCode, String uuid);
}