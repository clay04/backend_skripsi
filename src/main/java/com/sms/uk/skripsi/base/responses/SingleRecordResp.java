package com.sms.uk.skripsi.base.responses;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sms.uk.skripsi.base.constant.BaseEnumMessageKey;
import com.sms.uk.skripsi.config.response_messages.BaseResponseMessage;
import com.sms.uk.skripsi.config.response_messages.serializer.ResultSerializer;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SingleRecordResp extends BaseResponseMessage {

    private OutputSchema outputSchema;

    @Builder(builderMethodName = "responseBuilder")
    public SingleRecordResp(
            BaseEnumMessageKey messageKey,
            Object result,
            Object ...args
    ) {
        super.buildCustomResponse(messageKey, args);
        outputSchema = new OutputSchema(result);
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class OutputSchema {

        @JsonSerialize(using = ResultSerializer.class)
        private Object result;
    }
}

