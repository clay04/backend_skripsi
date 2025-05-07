package com.sms.uk.skripsi.module.scholarship.controller;

import com.sms.uk.skripsi.base.responses.PagingSortingResp;
import com.sms.uk.skripsi.base.responses.SingleRecordResp;
import com.sms.uk.skripsi.config.response_messages.localization_messages.EnumMessagesKey;
import com.sms.uk.skripsi.module.scholarship.dtos.ScholarshipFilterRequest;
import com.sms.uk.skripsi.module.scholarship.dtos.ScholarshipRequest;
import com.sms.uk.skripsi.module.scholarship.mapper.ScholarshipMapper;
import com.sms.uk.skripsi.module.scholarship.services.impl.ScholarshipServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scholarship")
@RequiredArgsConstructor
@Tag(name = "Scholarship")
public class ScholarshipController {

    private final ScholarshipServiceImpl scholarshipService;
    private final ScholarshipMapper scholarshipMapper;

    @PostMapping("/create")
    @Operation(summary = "Create scholarship")
    public ResponseEntity<Object> create(@Valid @RequestBody ScholarshipRequest request){

        var result = scholarshipService.create(request);

        return ResponseEntity.ok(SingleRecordResp.responseBuilder()
                .result(scholarshipMapper.convertEntityToResponse(result))
                .messageKey(EnumMessagesKey.SUCCESS_INSERT)
                .build());
    }

    @PutMapping("/update")
    @Operation(summary = "Update scholarship")
    public ResponseEntity<Object> update(@Valid @RequestBody ScholarshipRequest request){

        var result = scholarshipService.update(request);

        return ResponseEntity.ok(SingleRecordResp.responseBuilder()
                .result(scholarshipMapper.convertEntityToResponse(result))
                .messageKey(EnumMessagesKey.SUCCESS_UPDATE)
                .build());
    }

    @GetMapping("/get")
    @Operation(summary = "Paging Scholar")
    public ResponseEntity<Object> paging(ScholarshipFilterRequest filterRequest){

        var results = scholarshipService.paging(filterRequest);

        return ResponseEntity.ok(PagingSortingResp.responseBuilder()
                .messageKey(EnumMessagesKey.SUCCESS)
                .currentPage(results.getNumber())
                .totalPage(results.getTotalPages())
                .currentSize(results.getSize())
                .records(results.getContent().stream().map(scholarshipMapper::convertEntityToResponse).toList())
                .totalSize(results.getTotalElements())
                .build());
    }

    @GetMapping("/detail")
    @Operation(summary = "Detail scholarship")
    public ResponseEntity<Object> detail(@RequestParam String uuid){

        var result = scholarshipService.detail(uuid);

        return ResponseEntity.ok(SingleRecordResp.responseBuilder()
                .result(scholarshipMapper.convertEntityToResponse(result))
                .messageKey(EnumMessagesKey.SUCCESS)
                .build());
    }



    @DeleteMapping("/delete")
    @Operation(summary = "Delete scholarship")
    public ResponseEntity<Object> create(@RequestParam String uuid){

        return ResponseEntity.ok(SingleRecordResp.responseBuilder()
                .result(scholarshipService.delete(uuid))
                .messageKey(EnumMessagesKey.SUCCESS_DELETE)
                .build());
    }

    @PutMapping("/approval")
    @Operation(summary = "Approval scholarship")
    public ResponseEntity<Object> approval(@RequestParam String uuid,
                                           @RequestParam boolean isApproved) throws MessagingException {

        var result = scholarshipService.approveReject(uuid, isApproved);

        return ResponseEntity.ok(SingleRecordResp.responseBuilder()
                .result(scholarshipMapper.convertEntityToResponse(result))
                .messageKey(EnumMessagesKey.SUCCESS_UPDATE)
                .build());
    }


}
