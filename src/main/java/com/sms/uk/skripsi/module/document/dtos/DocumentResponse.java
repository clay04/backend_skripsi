package com.sms.uk.skripsi.module.document.dtos;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.UUID;
import java.time.LocalDateTime;

@Data
@SuperBuilder
public class DocumentResponse {
    private String uuid;
    private String fileName;
    private String fileType;
    private String category;
    private String uploadedBy;
    private LocalDateTime uploadedAt;
    private byte[] file_data;
    private Boolean isVerified;
    private String note;


}
