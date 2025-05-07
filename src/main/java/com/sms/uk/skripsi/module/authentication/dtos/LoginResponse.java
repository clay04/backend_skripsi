package com.sms.uk.skripsi.module.authentication.dtos;

import com.sms.uk.skripsi.module.user.dtos.MasterUserResponse;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class LoginResponse {

    private MasterUserResponse user;

    private String token;
}
