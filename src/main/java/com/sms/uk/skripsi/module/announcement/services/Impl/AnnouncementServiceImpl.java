package com.sms.uk.skripsi.module.announcement.services.Impl;

import com.sms.uk.skripsi.base.exception.BaseException;
import com.sms.uk.skripsi.config.response_messages.localization_messages.EnumMessagesKey;
import com.sms.uk.skripsi.module.announcement.dtos.AnnouncementRequest;
import com.sms.uk.skripsi.module.announcement.entities.Announcement;
import com.sms.uk.skripsi.module.announcement.mapper.AnnouncementMapper;
import com.sms.uk.skripsi.module.announcement.repositories.AnnouncementRepository;
import com.sms.uk.skripsi.module.announcement.services.AnnouncementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository repository;
    private final AnnouncementMapper mapper;

    @Override
    public Announcement create(AnnouncementRequest request) {

        log.info("announcement create request: {}", request);

        this.isExistsByTitle(request);
        Announcement announcement = mapper.convertRequestToEntity(request);

        return repository.save(announcement);
    }

    @Override
    public Announcement update(AnnouncementRequest request) {

        log.info("announcement update request: {}", request);
        this.isExistsByTitle(request);

        Announcement announcement = this.getDetail(request.getUuid());

        Announcement announcementAboutToEdit = mapper.convertRequestToUpdateEntity(request, announcement);

        return repository.save(announcementAboutToEdit);
    }

    @Override
    public boolean delete(String uuid) {

        Announcement announcement = this.getDetail(uuid);

        try {

            announcement.setDeleted(true);
            announcement.setDeletedAt(LocalDateTime.now());
            repository.save(announcement);

            return true;
        }catch (Exception e){
            throw new BaseException(EnumMessagesKey.ERROR_DEFAULT);
        }
    }

    @Override
    public Announcement detail(String uuid) {
        return this.getDetail(uuid);
    }

    private Announcement getDetail(String uuid) {

        return repository.findById(uuid)
                .orElseThrow(() -> new BaseException(EnumMessagesKey.ERROR_NOT_FOUND));
    }

    private void isExistsByTitle(AnnouncementRequest request) {
        boolean isExist = request.getUuid() == null? repository.existsByTitle(request.getTitle())
                : repository.existsByTitleAndUuidNot(request.getDescription(), request.getUuid());

        if (isExist){
            throw new BaseException(EnumMessagesKey.ERROR_DUPLICATED_ANNOUNCEMENT_TITLE);
        }
    }

    @Override
    public List<Announcement> getAll() {
        return repository.findAll();
    }


}
