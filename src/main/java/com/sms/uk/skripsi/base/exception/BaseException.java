package com.sms.uk.skripsi.base.exception;

import com.sms.uk.skripsi.base.constant.BaseEnumMessageKey;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final transient BaseEnumMessageKey errorMessage;

    private final transient Object[] arguments;

    public BaseException(BaseEnumMessageKey errorMessage, Object ...args) {
        super(String.format("[%s] %s", errorMessage.getCode(), errorMessage.getMessageKey()));
        this.errorMessage = errorMessage;
        this.arguments = args;
    }

}
