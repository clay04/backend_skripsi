package com.sms.uk.skripsi.base.constant;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

public interface BaseEnumMessageKey extends Serializable {

    String getMessageKey();

    String getCode();

    HttpStatus getHttpStatus();
}
