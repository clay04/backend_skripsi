package com.sms.uk.skripsi.config.cors;

import com.sms.uk.skripsi.core.properties.ConfigSecurityProperties;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.io.IOException;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class CustomCorsFilter implements Filter {

    private final ConfigSecurityProperties configSecurityProperties;

    @Override
    public void init(FilterConfig filterConfig) {
        //this method is intended left out blank
        //custom if you know what to do
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        setCorsResponseHeader(response);

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        //this method is intended left out blank
        //custom if you know what to do
    }

    private void setCorsResponseHeader(HttpServletResponse response) {
        if (!configSecurityProperties.allowOriginList().equalsIgnoreCase("")) {
            response.setHeader("Access-Control-Allow-Origin", configSecurityProperties.allowOriginList());
        }
        if (!configSecurityProperties.requestMethodList().equalsIgnoreCase("")) {
            response.setHeader("Access-Control-Allow-Methods", configSecurityProperties.requestMethodList());
        }
        if (!configSecurityProperties.allowHeaderList().equalsIgnoreCase("")) {
            response.setHeader("Access-Control-Allow-Headers", configSecurityProperties.allowHeaderList());
        }
        if (!configSecurityProperties.exposedHeaders().equalsIgnoreCase("")) {
            response.setHeader("Access-Control-Expose-Headers", configSecurityProperties.exposedHeaders());
        }
    }
}
