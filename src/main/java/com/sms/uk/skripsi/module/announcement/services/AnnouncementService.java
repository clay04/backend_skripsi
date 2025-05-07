package com.sms.uk.skripsi.module.announcement.services;

import com.sms.uk.skripsi.module.announcement.dtos.AnnouncementRequest;
import com.sms.uk.skripsi.module.announcement.entities.Announcement;

import java.util.List;

public interface AnnouncementService {
    Announcement create(AnnouncementRequest request);

    Announcement update(AnnouncementRequest request);

    boolean delete(String uuid);

    Announcement detail(String uuid);

    List<Announcement> getAll();

}
