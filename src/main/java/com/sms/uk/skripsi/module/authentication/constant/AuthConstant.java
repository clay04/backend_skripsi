package com.sms.uk.skripsi.module.authentication.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthConstant {
    public static final String BASE_URL_CREATE_PASSWORD_FE = "/create-password/";

    public static final String REGEX_EMAIL_FORMAT_UK = "^[a-zA-Z0-9._%+-]+@student\\.unklab\\.ac\\.id$";
}
