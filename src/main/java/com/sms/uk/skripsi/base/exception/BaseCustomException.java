package com.sms.uk.skripsi.base.exception;

import lombok.Getter;

@Getter
public class BaseCustomException extends RuntimeException {

    private final String field;

    private final Integer status;

    public BaseCustomException(String field, String message, Integer status) {
        super(message);
        this.field = field;
        this.status = status;
    }

}
