package com.sms.uk.skripsi.module.faculty_major.repositories;

import com.sms.uk.skripsi.module.faculty_major.entities.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, String>, JpaSpecificationExecutor<Faculty> {

    @Query("select (count(f) > 0) from Faculty f where f.facultyCode = ?1")
    boolean existsByFacultyCode(String facultyCode);

    @Query("select (count(f) > 0) from Faculty f where f.facultyCode = ?1 and f.uuid <> ?2")
    boolean existsByFacultyCodeAndUuidNot(String facultyCode, String uuid);
}