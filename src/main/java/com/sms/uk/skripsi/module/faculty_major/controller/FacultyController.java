package com.sms.uk.skripsi.module.faculty_major.controller;

import com.sms.uk.skripsi.base.responses.PagingSortingResp;
import com.sms.uk.skripsi.base.responses.SingleRecordResp;
import com.sms.uk.skripsi.config.response_messages.localization_messages.EnumMessagesKey;
import com.sms.uk.skripsi.module.faculty_major.dtos.FacultyRequest;
import com.sms.uk.skripsi.module.faculty_major.mapper.FacultyMapper;
import com.sms.uk.skripsi.module.faculty_major.services.impl.FacultyServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/faculty")
@RequiredArgsConstructor
@Tag(name = "Faculty")
public class FacultyController {

    private final FacultyServiceImpl service;
    private final FacultyMapper mapper;

    @PostMapping("/create")
    @Operation(summary = "Create faculty")
    public ResponseEntity<Object> create(@Valid @RequestBody FacultyRequest request){

        var result = service.create(request);

        return ResponseEntity.ok(SingleRecordResp.responseBuilder()
                .result(mapper.convertEntityToResponse(result))
                .messageKey(EnumMessagesKey.SUCCESS_INSERT)
                .build());
    }

    @PutMapping("/update")
    @Operation(summary = "Update faculty")
    public ResponseEntity<Object> update(@Valid @RequestBody FacultyRequest request){

        var result = service.update(request);

        return ResponseEntity.ok(SingleRecordResp.responseBuilder()
                .result(mapper.convertEntityToResponse(result))
                .messageKey(EnumMessagesKey.SUCCESS_INSERT)
                .build());
    }

    @GetMapping("/get")
    @Operation(summary = "Paging Faculty")
    public ResponseEntity<Object> paging(@RequestParam(required = false) Integer page,
                                         @RequestParam(required = false) Integer size,
                                         @RequestParam(required = false) String sortBy,
                                         @RequestParam(required = false) String orderBy,
                                         @RequestParam(required = false) String searchByCode,
                                         @RequestParam(required = false) String searchByName){

        var results = service.paging(page, size, sortBy, orderBy, searchByCode, searchByName);

        return ResponseEntity.ok(PagingSortingResp.responseBuilder()
                .messageKey(EnumMessagesKey.SUCCESS)
                .currentPage(results.getNumber())
                .totalPage(results.getTotalPages())
                .currentSize(results.getSize())
                .records(results.getContent().stream().map(mapper::convertEntityToResponse).toList())
                .totalSize(results.getTotalElements())
                .build());
    }

    @GetMapping("/detail")
    @Operation(summary = "Detail faculty")
    public ResponseEntity<Object> detail(@RequestParam String uuid){

        var result = service.detail(uuid);

        return ResponseEntity.ok(SingleRecordResp.responseBuilder()
                .result(mapper.convertEntityToResponse(result))
                .messageKey(EnumMessagesKey.SUCCESS_INSERT)
                .build());
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete faculty")
    public ResponseEntity<Object> create(@RequestParam String uuid){

        return ResponseEntity.ok(SingleRecordResp.responseBuilder()
                .result(service.delete(uuid))
                .messageKey(EnumMessagesKey.SUCCESS_DELETE)
                .build());
    }
}
