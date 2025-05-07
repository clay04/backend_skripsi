package com.sms.uk.skripsi.base.responses;

import com.sms.uk.skripsi.base.constant.BaseEnumMessageKey;
import com.sms.uk.skripsi.config.response_messages.BaseResponseMessage;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ListResp extends BaseResponseMessage {

    private OutputSchema outputSchema;

    @Builder(builderMethodName = "responseBuilder")
    public ListResp(
        BaseEnumMessageKey messageKey,
        List<?> records,
        Object ...args
    ) {
        super.buildCustomResponse(messageKey, args);
        outputSchema = new OutputSchema(records);
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OutputSchema {

        private List<?> records;

    }

}
