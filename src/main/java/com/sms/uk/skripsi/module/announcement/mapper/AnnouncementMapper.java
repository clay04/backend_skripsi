package com.sms.uk.skripsi.module.announcement.mapper;

import com.sms.uk.skripsi.core.util.DateTimeUtil;
import com.sms.uk.skripsi.module.announcement.dtos.AnnouncementRequest;
import com.sms.uk.skripsi.module.announcement.dtos.AnnouncementResponse;
import com.sms.uk.skripsi.module.announcement.entities.Announcement;
import org.springframework.stereotype.Component;

@Component
public class AnnouncementMapper {

    public Announcement convertRequestToEntity(AnnouncementRequest request){

        return Announcement.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .publishDate(request.getPublishDate())
                .displayDate(request.getDisplayDate())
                .build();
    }

    public Announcement convertRequestToUpdateEntity(AnnouncementRequest request, Announcement announcement){

        return Announcement.builder()
                .uuid(announcement.getUuid())
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .publishDate(request.getPublishDate())
                .displayDate(request.getDisplayDate())
                .createdAt(announcement.getCreatedAt())
                .createdBy(announcement.getCreatedBy())
                .isDeleted(announcement.isDeleted())
                .deletedAt(announcement.getDeletedAt())
                .deletedBy(announcement.getDeletedBy())
                .build();
    }

    public AnnouncementResponse convertEntityToResponse(Announcement announcement){

        return AnnouncementResponse.builder()
                .uuid(announcement.getUuid())
                .title(announcement.getTitle())
                .description(announcement.getDescription())
                .category(announcement.getCategory())
                .publishDate(announcement.getPublishDate())
                .displayDate(announcement.getDisplayDate())
                .createdAt(DateTimeUtil.convertToDetailDateTime(announcement.getCreatedAt()))
                .createdBy(announcement.getCreatedBy())
                .updatedAt(DateTimeUtil.convertToDetailDateTime(announcement.getUpdatedAt()))
                .updatedBy(announcement.getUpdatedBy())
                .build();
    }
}
