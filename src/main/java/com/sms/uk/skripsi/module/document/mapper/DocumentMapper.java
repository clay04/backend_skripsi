package com.sms.uk.skripsi.module.document.mapper;

import com.sms.uk.skripsi.module.document.dtos.DocumentResponse;
import com.sms.uk.skripsi.module.document.entities.Document;
import org.springframework.stereotype.Component;

@Component
public class DocumentMapper {

    public DocumentResponse toResponse(Document entity) {
        return DocumentResponse.builder()
                .uuid(entity.getUuid())
                .fileName(entity.getFileName())
                .fileType(entity.getFileType())
                .category(entity.getCategory())
                .uploadedBy(entity.getUploadedBy())
                .uploadedAt(entity.getUploadedAt())
                .file_data(entity.getFileData())
                .isVerified(entity.getIsVerified())
                .note(entity.getNote())
                .build();
    }

}
