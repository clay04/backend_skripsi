package com.sms.uk.skripsi.config.error_handling.tomcat;

import com.google.gson.Gson;
import com.sms.uk.skripsi.config.response_messages.BaseResponseMessage;
import com.sms.uk.skripsi.config.response_messages.localization_messages.EnumMessagesKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ErrorReportValve;

import java.util.Enumeration;

@Slf4j
public class CustomTomcatErrorReportValve extends ErrorReportValve {
    
    //https://github.com/spring-projects/spring-boot/issues/21257
    @Override
    protected void report(Request request, Response response, Throwable throwable) {
        if (!response.setErrorReported())
            return;
        log.warn("{} Fatal error before getting to Spring. {} ", response.getStatus(), throwable);

        try {
            if (response.getStatus() == 400) {
                printRequestHeader(request);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        try {
            response.setContentType("application/json");
            var gson = new Gson();

            String responseMessageKey = EnumMessagesKey.ERROR_DEFAULT.getMessageKey();
            if ("404".equalsIgnoreCase(String.valueOf(response.getStatus()))) {
                responseMessageKey = EnumMessagesKey.ERROR_NOT_FOUND.getMessageKey();
            }
            BaseResponseMessage responseMessage = BaseResponseMessage.baseResponseBuilder()
                    .errorCode(String.valueOf(response.getStatus()))
                    .errorMessage(new BaseResponseMessage.ErrorMessage(responseMessageKey))
                    .build();
            String responseData = gson.toJson(responseMessage);
            response.getOutputStream().println(responseData);
            response.finishResponse();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void printRequestHeader(Request request) {
        log.error("Request header was");
        for (Enumeration<String> headerNames = request.getHeaderNames(); headerNames.hasMoreElements();) {
            try{
                String headerName = headerNames.nextElement();
                log.error("Header name {}", headerName);
                log.error("Header value {}", request.getHeader(headerName));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
