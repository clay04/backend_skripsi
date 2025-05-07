package com.sms.uk.skripsi.config.error_handling;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sms.uk.skripsi.config.response_messages.BaseResponseMessage;
import com.sms.uk.skripsi.config.response_messages.localization_messages.EnumMessagesKey;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class MyAccessDeniedHandler implements AccessDeniedHandler {

    // Handler for case 403
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessException) throws IOException {

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        ObjectMapper objectMapper = new ObjectMapper();
        String responseMessageKey = EnumMessagesKey.ERROR_FORBIDDEN.getMessageKey();
        BaseResponseMessage responseMessage = BaseResponseMessage.baseResponseBuilder()
                .errorCode(String.valueOf(HttpStatus.FORBIDDEN.value()))
                .errorMessage(new BaseResponseMessage.ErrorMessage(responseMessageKey))
                .build();
        String responseData = objectMapper.writeValueAsString(responseMessage);
        response.getOutputStream().println(responseData);
    }
}
