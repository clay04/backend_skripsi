package com.sms.uk.skripsi.config.error_handling;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sms.uk.skripsi.config.response_messages.BaseResponseMessage;
import com.sms.uk.skripsi.config.response_messages.localization_messages.EnumMessagesKey;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.io.IOException;

@Slf4j
@ControllerAdvice
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    
    // Handler for case 401

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         org.springframework.security.core.AuthenticationException authException) throws IOException {
        // 401
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        try {
            if (!request.getRequestURL().toString().contains("favicon"))
                log.error(authException.getMessage(), authException);
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String responseMessageKey = EnumMessagesKey.ERROR_UNAUTHORIZED.getMessageKey();
        BaseResponseMessage responseMessage = BaseResponseMessage.baseResponseBuilder()
                .errorCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()))
                .errorMessage(new BaseResponseMessage.ErrorMessage(responseMessageKey))
                .build();
        String responseData = objectMapper.writeValueAsString(responseMessage);
        response.getOutputStream().println(responseData);
    }
}
