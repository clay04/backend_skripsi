package com.sms.uk.skripsi.module.document.dtos;

import lombok.Data;

@Data
public class DocumentCompletionStatusRequest {
    private String uuid;
    private boolean isComplete;
}
