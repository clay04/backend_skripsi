package com.sms.uk.skripsi.module.authentication.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "error.username.cannot.blank")
    private String username;

    @NotBlank(message = "error.password.cannot.blank")
    private String password;
}
