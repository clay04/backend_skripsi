package com.sms.uk.skripsi.module.document.services;

import com.sms.uk.skripsi.module.document.dtos.DocumentResponse;
import com.sms.uk.skripsi.module.document.dtos.DocumentUploadRequest;

import com.sms.uk.skripsi.module.document.entities.Document;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DocumentServices {

    DocumentResponse uploadDocument(MultipartFile file, DocumentUploadRequest request) throws IOException;
    byte[] downloadDocument(UUID uuid) throws IOException;
    Object checkDocumentCompleteness(String uploadedBy, String category);
    List<DocumentResponse> getAllDocumentsByUser(String uploadedBy);

    Optional<Document> getDocumentByUuid(String uuid);
    DocumentResponse saveDocument(Document document);

    DocumentResponse verifyDocument(String uuid, boolean isVerified, String note);

    Object updateDocumentStatus(String uploadedBy, boolean isApproved);

    void updateDocumentCompletionStatus(String uploadedBy, boolean isComplete);
}
