package com.sms.uk.skripsi.core.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PasswordUtil {

    public static String generatePassword(){

        return encryptPassword(UUID.randomUUID().toString().replace("-", "").substring(0, 8));
    }

    public static String encryptPassword(String attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return null;
        }
        return BCrypt.hashpw(attribute, BCrypt.gensalt());
    }

    public static boolean verifyPassword(String rawPassword, String storedHash) {
        if (rawPassword == null || storedHash == null) {
            return false;
        }
        return BCrypt.checkpw(rawPassword, storedHash);
    }

}
