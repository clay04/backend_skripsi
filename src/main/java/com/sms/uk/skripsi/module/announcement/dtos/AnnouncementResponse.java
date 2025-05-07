package com.sms.uk.skripsi.module.announcement.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AnnouncementResponse {

    private String uuid;

    private String title;

    private String description;

    private String category;

    private LocalDate publishDate;

    private LocalDate displayDate;

    private String createdAt;

    private String createdBy;

    private String updatedAt;

    private String updatedBy;
}
