package com.sms.uk.skripsi.module.document.services.impl;
import com.sms.uk.skripsi.module.document.dtos.DocumentResponse;
import com.sms.uk.skripsi.module.document.dtos.DocumentUploadRequest;
import com.sms.uk.skripsi.module.document.entities.Document;
import com.sms.uk.skripsi.module.document.mapper.DocumentMapper;
import com.sms.uk.skripsi.module.document.repository.DocumentRepository;
import com.sms.uk.skripsi.module.document.services.DocumentServices;
import com.sms.uk.skripsi.module.scholarship.entities.Scholarship;
import com.sms.uk.skripsi.module.scholarship.repositories.ScholarshipRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentServiceImpl implements DocumentServices {

    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;

    private final ScholarshipRepository scholarshipRepository;

    //String text = stripper.getText(document).toLowerCase();


    private final String BASE_DIR = "D:\\documents\\";

    @Override
    public DocumentResponse uploadDocument(MultipartFile file, DocumentUploadRequest request) throws IOException {
        String userUuid = request.getUploadedBy();

        Path userDir = Paths.get(BASE_DIR, userUuid);

        if (!Files.exists(userDir)) {
            Files.createDirectories(userDir);
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = userDir.resolve(fileName);

        Files.write(filePath, file.getBytes());

        boolean isValid = validateDocumentContent(file, request.getCategory());

        Document entity = Document.builder()
                .fileName(file.getOriginalFilename())
                .fileType(file.getContentType())
                .fileData(file.getBytes())
                .path(filePath.toString())
                .category(request.getCategory())
                .uploadedBy(userUuid)
                .uploadedAt(LocalDateTime.now())
                .isVerified(isValid)
                .note(isValid ? "Dokumen valid." : "Dokumen tidak valid.")
                .build();

        return documentMapper.toResponse(documentRepository.save(entity));
    }

    private boolean validateDocumentContent(MultipartFile file, String category) throws IOException {
        String contentType = file.getContentType();

        if (contentType != null && contentType.startsWith("image/")) {
            return validateImage(file);
        } else if (contentType != null && contentType.equals("application/pdf")) {
            return validatePdf(file, category);
        }

        return false;
    }

    private boolean validateImage(MultipartFile file) {
        try (InputStream in = file.getInputStream()) {
            BufferedImage image = ImageIO.read(in);
            return image != null && image.getWidth() >= 100 && image.getHeight() >= 100;
        } catch (IOException e) {
            return false;
        }
    }

    private boolean validatePdf(MultipartFile file, String category) {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document).toLowerCase();

            Map<String, List<String>> categoryKeywords = new HashMap<>();
            categoryKeywords.put("Motivation Letter", List.of("motivation", "beasiswa", "saya"));
            categoryKeywords.put("Form Biodata A1", List.of("biodata", "nama", "nim"));
            categoryKeywords.put("Resume Pribadi", List.of("resume", "pendidikan", "pengalaman"));

            List<String> keywords = categoryKeywords.getOrDefault(category, List.of());
            return keywords.stream().anyMatch(text::contains);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public byte[] downloadDocument(UUID uuid) throws IOException {
        Document document = documentRepository.findByUuid(uuid.toString())
                .orElseThrow(() -> new RuntimeException("Document not found with UUID: " + uuid));

        Path filePath = Paths.get(document.getPath());
        return Files.readAllBytes(filePath);
    }

    @Override
    public Object checkDocumentCompleteness(String uploadedBy, String category) {
        List<String> requiredCategories = List.of(
                "Pass Foto", "Form Biodata A1", "Form Keterampilan", "Surat Keterangan Tidak Mampu", "Resume Pribadi",
                "Motivation Letter", "Surat Pernyataan Tidak Menerima Beasiswa Lain", "Surat Pernyataan Bermetrai",
                "Fotocopy KTP", "Fotocopy KTM", "Transkrip Nilai"
        );

        List<String> uploadedCategories = documentRepository.findUploadedCategoriesByUser(uploadedBy);

        if (uploadedCategories == null || uploadedCategories.isEmpty()) {
            throw new RuntimeException("User with uploadedBy '" + uploadedBy + "' has not uploaded any documents.");
        }

        if (category != null && !category.isEmpty()) {
            boolean isUploaded = uploadedCategories.contains(category);
            Map<String, Object> result = new HashMap<>();
            result.put("category", category);
            result.put("isUploaded", isUploaded);
            return result;
        }

        List<String> missingCategories = new ArrayList<>();
        for (String required : requiredCategories) {
            if (!uploadedCategories.contains(required)) {
                missingCategories.add(required);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("uploadedBy", uploadedBy);
        result.put("isComplete", missingCategories.isEmpty());
        result.put("missingCategories", missingCategories);

        return result;
    }

    @Override
    public List<DocumentResponse> getAllDocumentsByUser(String uploadedBy) {
        List<Document> documents = documentRepository.findUploadedByUser(uploadedBy);
        return documents.stream()
                .map(documentMapper::toResponse)
                .toList();
    }

    @Override
    public Optional<Document> getDocumentByUuid(String uuid) {
        return documentRepository.findByUuid(uuid);
    }

    @Override
    public DocumentResponse saveDocument(Document document) {
        return null;
    }


    @Override
    public DocumentResponse verifyDocument(String uuid, boolean isVerified, String note) {
        // Get the document by UUID
        Optional<Document> documentOptional = documentRepository.findByUuid(uuid);

        if (documentOptional.isEmpty()) {
            throw new RuntimeException("Dokumen tidak ditemukan");
        }

        Document document = documentOptional.get();

        // Set the verified status and note
        document.setIsVerified(isVerified); // Set the verification status
        document.setNote(note); // Set the verification note

        // Save the updated document
        documentRepository.save(document); // Save document using the repository

        // Return the response as DocumentResponse
        return documentMapper.toResponse(document); // Convert the entity to response
    }


    @Override
    public Object updateDocumentStatus(String uploadedBy, boolean isApproved) {
        List<Document> documents = documentRepository.findUploadedByUser(uploadedBy);

        // Ensure all documents are verified before changing the status
        boolean allVerified = documents.stream().allMatch(Document::getIsVerified);

        if (!allVerified) {
            throw new RuntimeException("Not all documents are verified.");
        }

        // Update scholarship status to approved/rejected based on verification
        // Assuming you have a method to update scholarship status (not shown in this example)
        // updateScholarshipStatus(uploadedBy, isApproved);

        return Map.of("status", isApproved ? "Approved" : "Rejected");
    }

    @Override
    public void updateDocumentCompletionStatus(String uploadedBy, boolean isComplete) {
        List<Document> documents = documentRepository.findUploadedByUser(uploadedBy);

        boolean allVerified = documents.stream().allMatch(Document::getIsVerified);

        Optional<Scholarship> scholarshipOptional = scholarshipRepository.findByUserUuid(uploadedBy);
        if (scholarshipOptional.isEmpty()) {
            throw new NoSuchElementException("Scholarship not found for user: " + uploadedBy);
        }

        Scholarship scholarship = scholarshipOptional.get();

        scholarship.setDocumentCompletionStatus(Boolean.valueOf(allVerified ? "LENGKAP" : "TIDAK LENGKAP"));
        scholarshipRepository.save(scholarship);
    }

}
