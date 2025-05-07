package com.sms.uk.skripsi.module.authentication.controller;

import com.sms.uk.skripsi.base.responses.SingleRecordResp;
import com.sms.uk.skripsi.config.response_messages.localization_messages.EnumMessagesKey;
import com.sms.uk.skripsi.module.authentication.dtos.CreatePasswordRequest;
import com.sms.uk.skripsi.module.authentication.dtos.ForgotPasswordRequest;
import com.sms.uk.skripsi.module.authentication.dtos.LoginRequest;
import com.sms.uk.skripsi.module.authentication.mapper.AuthenticationMapper;
import com.sms.uk.skripsi.module.authentication.services.AuthenticationService;
import com.sms.uk.skripsi.module.user.dtos.MasterUserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Authentication")
public class AuthenticationController {

    private final AuthenticationService service;
    private final AuthenticationMapper mapper;


    @PostMapping("/sign-up")
    @Operation(summary = "Signup")
    ResponseEntity<Object> signup(@Valid @RequestBody MasterUserRequest request) throws MessagingException{

        var result = service.signup(request);

        return ResponseEntity.ok(SingleRecordResp.responseBuilder()
                .messageKey(EnumMessagesKey.SUCCESS)
                .result(mapper.convertEntityToResponse(result))
                .build());
    }
    @PostMapping("/login")
    @Operation(summary = "Login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest request) {

        var result = service.login(request);

        return ResponseEntity.ok(SingleRecordResp.responseBuilder()
                .result(mapper.convertEntityToResponse(result))
                .messageKey(EnumMessagesKey.SUCCESS)
                .build());
    }

    @GetMapping("/logout")
    @Operation(summary = "Logout")
    public ResponseEntity<Object> logout(@RequestParam String uuid){

        return ResponseEntity.ok(SingleRecordResp.responseBuilder()
                .result(service.logout(uuid))
                .messageKey(EnumMessagesKey.SUCCESS)
                .build());
    }

    @PutMapping("/forgot-password")
    @Operation(summary = "Forgot Password")
    public ResponseEntity<Object> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) throws MessagingException {

        return ResponseEntity.ok(SingleRecordResp.responseBuilder()
                .messageKey(EnumMessagesKey.SUCCESS)
                .result(service.forgotPassword(request))
                .build());
    }

    @PutMapping("/create-new-password/{token}")
    @Operation(summary = "Create New Password")
    public ResponseEntity<Object> createNewPassword(@PathVariable("token") String token,
                                               @Valid @RequestBody CreatePasswordRequest request){
        return ResponseEntity.ok(SingleRecordResp.responseBuilder()
                .messageKey(EnumMessagesKey.SUCCESS)
                .result(service.createPassword(token, request))
                .build());
    }
}
