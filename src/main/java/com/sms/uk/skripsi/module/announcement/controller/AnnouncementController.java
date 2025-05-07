package com.sms.uk.skripsi.module.announcement.controller;

import com.sms.uk.skripsi.base.responses.SingleRecordResp;
import com.sms.uk.skripsi.config.response_messages.localization_messages.EnumMessagesKey;
import com.sms.uk.skripsi.module.announcement.dtos.AnnouncementRequest;
import com.sms.uk.skripsi.module.announcement.mapper.AnnouncementMapper;
import com.sms.uk.skripsi.module.announcement.services.AnnouncementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/annoucement")
@RequiredArgsConstructor
@Tag(name = "Announcement")
public class AnnouncementController {

    private final AnnouncementService announcementService;
    private final AnnouncementMapper announcementMapper;

    @PostMapping("/create")
    @Operation(summary = "Create announcement")
    public ResponseEntity<Object> create(@Valid @RequestBody AnnouncementRequest request){

        var result = announcementService.create(request);

        return ResponseEntity.ok(SingleRecordResp.responseBuilder()
                .result(announcementMapper.convertEntityToResponse(result))
                .messageKey(EnumMessagesKey.SUCCESS_INSERT)
                .build());
    }

    @PutMapping("/update")
    @Operation(summary = "Update announcement")
    public ResponseEntity<Object> update(@Valid @RequestBody AnnouncementRequest request){

        var result = announcementService.update(request);

        return ResponseEntity.ok(SingleRecordResp.responseBuilder()
                .result(announcementMapper.convertEntityToResponse(result))
                .messageKey(EnumMessagesKey.SUCCESS_INSERT)
                .build());
    }

    @GetMapping("/detail")
    @Operation(summary = "Detail announcement")
    public ResponseEntity<Object> detail(@RequestParam String uuid){

        var result = announcementService.detail(uuid);

        return ResponseEntity.ok(SingleRecordResp.responseBuilder()
                .result(announcementMapper.convertEntityToResponse(result))
                .messageKey(EnumMessagesKey.SUCCESS_INSERT)
                .build());
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete announcement")
    public ResponseEntity<Object> create(@RequestParam String uuid){

        return ResponseEntity.ok(SingleRecordResp.responseBuilder()
                .result(announcementService.delete(uuid))
                .messageKey(EnumMessagesKey.SUCCESS_DELETE)
                .build());
    }

    @GetMapping("/list")
    @Operation(summary = "List all announcements")
    public ResponseEntity<Object> list() {
        var result = announcementService.getAll()
                .stream()
                .map(announcementMapper::convertEntityToResponse)
                .toList();

        return ResponseEntity.ok(result); // langsung kembalikan list
    }
}
