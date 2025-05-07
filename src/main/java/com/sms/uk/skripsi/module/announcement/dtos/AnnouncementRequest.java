package com.sms.uk.skripsi.module.announcement.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AnnouncementRequest {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("category")
    private String category;

    @JsonProperty("publish_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate publishDate;

    @JsonProperty("display_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate displayDate;

}
