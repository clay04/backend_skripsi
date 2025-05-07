package com.sms.uk.skripsi.module.authentication.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreatePasswordRequest {

    @JsonProperty("new_password")
    @NotBlank(message = "error.password.cannot.blank")
    private String newPassword;

    @JsonProperty("confirm_new_password")
    @NotBlank(message = "error.confirm.password.cannot.blank")
    private String confirmNewPassword;
}
