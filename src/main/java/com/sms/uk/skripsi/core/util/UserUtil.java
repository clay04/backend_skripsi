package com.sms.uk.skripsi.core.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserUtil {

    public static Principal principal() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
