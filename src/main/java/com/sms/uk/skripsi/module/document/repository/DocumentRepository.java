package com.sms.uk.skripsi.module.document.repository;

import com.sms.uk.skripsi.module.document.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DocumentRepository extends JpaRepository<Document, String>, JpaSpecificationExecutor<Document> {

    Optional<Document> findByUuid(String uuid);

    @Query("SELECT d.category FROM Document d WHERE d.uploadedBy = :uploadedBy")
    List<String> findUploadedCategoriesByUser(@Param("uploadedBy") String uploadedBy);

    @Query("SELECT d FROM Document d WHERE d.uploadedBy = :uploadedBy")
    List<Document> findUploadedByUser(@Param("uploadedBy") String uploadedBy);

    @Query("SELECT d FROM Document d WHERE d.uploadedBy = :uploadedBy AND d.isVerified = :status")
    List<Document> findByUploadedByAndIsVerified(@Param("uploadedBy") String uploadedBy, @Param("status") Boolean status);

    //@Query("SELECT d FROM Document d WHERE d.uuid = :uuid")
    //Optional<Document> findByUuid(@Param("uuid") String uuid);

}

