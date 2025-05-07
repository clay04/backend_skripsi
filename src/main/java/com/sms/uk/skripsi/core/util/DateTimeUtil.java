package com.sms.uk.skripsi.core.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateTimeUtil {

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static LocalDateTime getLocalDateOfInstant(Date expiration) {
        return LocalDateTime.ofInstant(expiration.toInstant(),
                ZoneId.systemDefault());
    }


    public static String convertToDetailDateTime(LocalDateTime createdDate) {

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

        return createdDate.format(df);
    }

    public static LocalDateTime getCurrentDateTimePlusOneHour() {
        return LocalDateTime.now().plusHours(1);
    }

    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }
}
