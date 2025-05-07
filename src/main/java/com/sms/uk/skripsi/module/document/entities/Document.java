package com.sms.uk.skripsi.module.document.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_document")
@SuperBuilder
public class Document {

    @Id
    @UuidGenerator
    @Column(name = "uuid")
    private String uuid;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "path")
    private String path;

    @Column(name = "category")
    private String category; // misal: 'proposal', 'resume', dll

    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;

    @Column(name = "uploaded_by")
    private String uploadedBy; // bisa simpan nim / uuid mahasiswa

    //@Lob
    @Column(name = "file_data", columnDefinition="bytea")
    private byte[] fileData;

    @Column(name = "is_verified")
    private Boolean isVerified = false;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;


}
