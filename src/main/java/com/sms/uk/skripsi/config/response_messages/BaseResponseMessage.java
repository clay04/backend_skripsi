package com.sms.uk.skripsi.config.response_messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sms.uk.skripsi.base.constant.BaseEnumMessageKey;
import com.sms.uk.skripsi.config.response_messages.localization_messages.EnumMessagesKey;
import com.sms.uk.skripsi.config.response_messages.localization_messages.LocalizedMessages;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.text.MessageFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BaseResponseMessage {

    private ErrorSchema errorSchema = new ErrorSchema();

    @Builder(builderMethodName = "baseResponseBuilder")
    public BaseResponseMessage(String errorCode, ErrorMessage errorMessage) {
        this.errorSchema.errorCode = errorCode;
        this.errorSchema.errorMessage = errorMessage;
    }

    public void buildSuccessResponse() {
        errorSchema = ErrorSchema.builder()
            .errorCode(String.valueOf(HttpStatus.OK.value()))
            .errorMessage(new ErrorMessage(EnumMessagesKey.SUCCESS.getMessageKey()))
            .build();
    }

    public void buildUnprocessableEntityResponse() {
        errorSchema = ErrorSchema.builder()
                .errorCode(String.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .errorMessage(new ErrorMessage(EnumMessagesKey.ERROR_UNPROCESSABLE_ENTITY.getMessageKey()))
                .build();
    }

    public void buildCustomResponse(BaseEnumMessageKey messageKey, Object ...args) {
        errorSchema = ErrorSchema.builder()
            .errorCode(
                messageKey != null ? messageKey.getCode() : String.valueOf(HttpStatus.OK.value())
            )
            .errorMessage(
                new ErrorMessage(
                    messageKey != null ? messageKey.getMessageKey() : EnumMessagesKey.SUCCESS.getMessageKey(),
                    args
            ))
            .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class ErrorSchema {
        @JsonProperty("error_code")
        private String errorCode;

        @JsonProperty("error_message")
        private ErrorMessage errorMessage;
    }

    @Getter
    @NoArgsConstructor
    @ToString
    public static class ErrorMessage {
        private String indonesian;
        private String english;

        public ErrorMessage(String messageKey, Object ...values) {
            this.indonesian = MessageFormat.format(LocalizedMessages.getLocalizedMessageIndonesian(messageKey), values);
            this.english = MessageFormat.format(LocalizedMessages.getLocalizedMessageEnglish(messageKey), values);
        }
    }
}