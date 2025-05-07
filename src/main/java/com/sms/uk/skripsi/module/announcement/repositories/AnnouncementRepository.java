package com.sms.uk.skripsi.module.announcement.repositories;

import com.sms.uk.skripsi.module.announcement.entities.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, String>, JpaSpecificationExecutor<Announcement> {

    @Query("select (count(a) > 0) from Announcement a where a.title = ?1")
    boolean existsByTitle(String title);

    @Query("select (count(a) > 0) from Announcement a where a.title = ?1 and a.uuid <> ?2")
    boolean existsByTitleAndUuidNot(String title, String uuid);

}