package com.sms.uk.skripsi.module.user.controller;

import com.sms.uk.skripsi.base.responses.PagingSortingResp;
import com.sms.uk.skripsi.base.responses.SingleRecordResp;
import com.sms.uk.skripsi.config.response_messages.localization_messages.EnumMessagesKey;
import com.sms.uk.skripsi.module.user.dtos.MasterUserRequest;
import com.sms.uk.skripsi.module.user.mapper.UserManagementMapper;
import com.sms.uk.skripsi.module.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/master-user")
@RequiredArgsConstructor
@Tag(name = "User Management")
public class UserController {

    private final UserService service;
    private final UserManagementMapper mapper;

    @PostMapping("/create")
    @Operation(summary = "Create user")
    public ResponseEntity<Object> create(@Valid @RequestBody MasterUserRequest request) throws MessagingException {

        var result = service.create(request);

        return ResponseEntity.ok(SingleRecordResp.responseBuilder()
                .result(mapper.convertEntityToResponse(result))
                .messageKey(EnumMessagesKey.SUCCESS_INSERT)
                .build());
    }

    @PutMapping("/update")
    @Operation(summary = "Update user")
    public ResponseEntity<Object> update(@Valid @RequestBody MasterUserRequest request){

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
                                         @RequestParam(required = false) String searchByRoleId,
                                         @RequestParam(required = false) String searchByFirstName,
                                         @RequestParam(required = false) String searchByLastName){

        var results = service.paging(page, size, sortBy, orderBy, searchByRoleId, searchByFirstName, searchByLastName);

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
    @Operation(summary = "Detail user")
    public ResponseEntity<Object> detail(@RequestParam String uuid){

        var result = service.detail(uuid);

        return ResponseEntity.ok(SingleRecordResp.responseBuilder()
                .result(mapper.convertEntityToResponse(result))
                .messageKey(EnumMessagesKey.SUCCESS_INSERT)
                .build());
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete user")
    public ResponseEntity<Object> create(@RequestParam String uuid){

        return ResponseEntity.ok(SingleRecordResp.responseBuilder()
                .result(service.delete(uuid))
                .messageKey(EnumMessagesKey.SUCCESS_DELETE)
                .build());
    }
}
