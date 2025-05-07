package com.sms.uk.skripsi.module.authentication.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ForgotPasswordRequest {

    @NotBlank(message = "error.email.cannot.blank")
    private String email;
}
