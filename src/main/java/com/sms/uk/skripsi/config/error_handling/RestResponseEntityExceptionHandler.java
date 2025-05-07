package com.sms.uk.skripsi.config.error_handling;

import com.sms.uk.skripsi.base.exception.BaseException;
import com.sms.uk.skripsi.config.response_messages.BaseResponseMessage;
import com.sms.uk.skripsi.config.response_messages.localization_messages.EnumMessagesKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    // https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
    /*
    Exception handler untuk case error di spring, lebih cocok digunakan karena semuanya terpusat, jadi ga perlu
    handling satu per satu tiap case, dan bisa d set per masing masing exception akan return seperti apa
    */

    @ExceptionHandler(Exception.class)
    private ResponseEntity<Object> exceptionHandler(Exception e) {
        log.error(e.getMessage(), e);

        String responseMessageKey = EnumMessagesKey.ERROR_DEFAULT.getMessageKey();
        BaseResponseMessage responseMessage = BaseResponseMessage.baseResponseBuilder()
                .errorCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .errorMessage(new BaseResponseMessage.ErrorMessage(responseMessageKey))
                .build();
        return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    private ResponseEntity<Object> httpRequestMethodNotSupportedHandler(HttpRequestMethodNotSupportedException e) {
    	log.error(e.getMessage(), e);

        String responseMessageKey = EnumMessagesKey.ERROR_NOT_FOUND.getMessageKey();
        BaseResponseMessage responseMessage = BaseResponseMessage.baseResponseBuilder()
                .errorCode(String.valueOf(HttpStatus.NOT_FOUND.value()))
                .errorMessage(new BaseResponseMessage.ErrorMessage(responseMessageKey))
                .build();
        return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<Object> methodArgumentNotValidHandler(MethodArgumentNotValidException e) {
    	log.error(e.getMessage(), e);

        var errors = e.getBindingResult().getFieldErrors()
                .stream()
                .map(x -> Pair.of(x.getField(), x.getDefaultMessage()))
                .sorted(Comparable::compareTo)
                .toList();

        BaseResponseMessage.ErrorMessage message;
        try {
            var responseMessageKey = EnumMessagesKey.ERROR_BAD_REQUEST.getMessageKey();
            if (!errors.isEmpty()) responseMessageKey = errors.get(0).getValue();
            message = new BaseResponseMessage.ErrorMessage(responseMessageKey, errors.get(0).getKey());
        } catch (Exception ex) {
            message = new BaseResponseMessage.ErrorMessage(EnumMessagesKey.ERROR_BAD_REQUEST.getMessageKey());
        }

        BaseResponseMessage responseMessage = BaseResponseMessage.baseResponseBuilder()
                .errorCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .errorMessage(message)
                .build();
        return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    private ResponseEntity<Object> httpMediaTypeNotSupportedHandler(HttpMediaTypeNotSupportedException e) {
        log.error(e.getMessage(), e);

        String responseMessageKey = EnumMessagesKey.ERROR_UNSUPPORTED_MEDIA_TYPE.getMessageKey();
        BaseResponseMessage responseMessage = BaseResponseMessage.baseResponseBuilder()
                .errorCode(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
                .errorMessage(new BaseResponseMessage.ErrorMessage(responseMessageKey))
                .build();
        return new ResponseEntity<>(responseMessage, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    private ResponseEntity<Object> httpMessageNotReadableHandler(HttpMessageNotReadableException e) {
        log.error(e.getMessage(), e);

        String responseMessageKey = EnumMessagesKey.ERROR_BAD_REQUEST.getMessageKey();
        BaseResponseMessage responseMessage = BaseResponseMessage.baseResponseBuilder()
                .errorCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .errorMessage(new BaseResponseMessage.ErrorMessage(responseMessageKey))
                .build();
        return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BaseException.class)
    private ResponseEntity<Object> handleBusinessException(BaseException e) {
        log.error(e.getMessage(), e);

        String responseMessageKey = e.getErrorMessage().getMessageKey();

        BaseResponseMessage responseMessage =
                BaseResponseMessage.baseResponseBuilder()
                        .errorCode(e.getErrorMessage().getCode())
                        .errorMessage(new BaseResponseMessage.ErrorMessage(responseMessageKey, e.getArguments()))
                        .build();
        return new ResponseEntity<>(responseMessage, e.getErrorMessage().getHttpStatus());
    }
}