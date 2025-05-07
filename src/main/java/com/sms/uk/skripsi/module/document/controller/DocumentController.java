package com.sms.uk.skripsi.module.document.controller;

import com.sms.uk.skripsi.base.responses.SingleRecordResp;
import com.sms.uk.skripsi.config.response_messages.localization_messages.EnumMessagesKey;
import com.sms.uk.skripsi.module.document.dtos.DocumentCompletionStatusRequest;
import com.sms.uk.skripsi.module.document.dtos.DocumentUploadRequest;
import com.sms.uk.skripsi.module.document.dtos.DocumentVerificationRequest;
import com.sms.uk.skripsi.module.document.entities.Document;
import com.sms.uk.skripsi.module.document.repository.DocumentRepository;
import com.sms.uk.skripsi.module.document.services.DocumentServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/document")
@RequiredArgsConstructor
@Tag(name ="Document")
public class DocumentController {

    private final DocumentServices documentServices;
    private final DocumentRepository documentRepository;


    @PostMapping("/upload")
    public ResponseEntity<Object> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam String category,
            @RequestParam String uploadedBy) throws IOException, java.io.IOException {

        DocumentUploadRequest request = new DocumentUploadRequest();
        request.setCategory(category);
        request.setUploadedBy(uploadedBy);

        var response = documentServices.uploadDocument(file, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/download/{uuid}")
    public ResponseEntity<Resource> download(@PathVariable UUID uuid) throws IOException {
        byte[] data = documentServices.downloadDocument(uuid);
        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=document")
                .contentLength(data.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body((Resource) resource);
    }

    @GetMapping("/check-completeness")
    public ResponseEntity<Object> checkCompleteness(
            @RequestParam String uploadedBy,
            @RequestParam(required = false) String category) {

        var response = documentServices.checkDocumentCompleteness(uploadedBy, category);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get")
    public ResponseEntity<Object> listDocuments(@RequestParam String uploadedBy) {
        var list = documentServices.getAllDocumentsByUser(uploadedBy);
        Map<String, Object> output = Map.of(
                "output_schema", Map.of("records", list)
        );
        return ResponseEntity.ok(output);
    }

    @GetMapping("/preview/{uuid}")
    public ResponseEntity<Resource> preview(@PathVariable UUID uuid) throws IOException {
        Document document = documentRepository.findByUuid(uuid.toString())
                .orElseThrow(() -> new RuntimeException("Dokumen tidak ditemukan"));

        Path filePath = Paths.get(document.getPath());
        Resource resource = new UrlResource(filePath.toUri());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + document.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(document.getFileType()))
                .contentLength(Files.size(filePath))
                .body(resource);
    }

    @PatchMapping("/verify")
    public ResponseEntity<Object> verifyDocument(@RequestParam String uuid,
                                                 @RequestParam boolean isVerified,
                                                 @RequestParam String note) {
        // Fetch the document from the database
        Optional<Document> documentOptional = documentServices.getDocumentByUuid(uuid);

        // Check if document exists
        if (documentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dokumen tidak ditemukan.");
        }

        Document document = documentOptional.get();
        document.setIsVerified(isVerified);
        document.setNote(note);

        // Save the updated documen
        documentServices.saveDocument(document);

        return ResponseEntity.ok("Dokumen berhasil diverifikasi.");
    }

    @PatchMapping("/completion-status")
    public ResponseEntity<Object> updateDocumentCompletionStatus(
            @RequestParam String uploadedBy, @RequestParam boolean isComplete) {
        try {
            documentServices.updateDocumentCompletionStatus(uploadedBy, isComplete);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update document status.");
        }
    }

}
