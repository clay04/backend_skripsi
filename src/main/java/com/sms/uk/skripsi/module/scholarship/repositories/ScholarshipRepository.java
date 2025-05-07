package com.sms.uk.skripsi.module.scholarship.repositories;

import com.sms.uk.skripsi.module.scholarship.entities.Scholarship;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScholarshipRepository extends JpaRepository<Scholarship, String>, JpaSpecificationExecutor<Scholarship> {

    @Query("select (count(s) > 0) from Scholarship s where s.user.uuid = ?1")
    boolean existsByUserUuid(String userUuid);

    @Query("select (count(s) > 0) from Scholarship s where s.user.uuid = ?1 and s.uuid <> ?2")
    boolean existsByUserUuidAndUuidNot(String userUuid, String uuid);

    @Modifying
    @Query("UPDATE Scholarship s SET s.documentCompletionStatus = :isComplete WHERE s.user.uuid = :uploadedBy")
    void updateDocumentCompletionStatusByUuid(@Param("uploadedBy") String uploadedBy, @Param("isComplete") boolean isComplete);

    Optional<Scholarship> findByUserUuid(String userUuid);

}