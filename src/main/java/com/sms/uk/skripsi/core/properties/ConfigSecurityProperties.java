package com.sms.uk.skripsi.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "config.security")
public record ConfigSecurityProperties(String allowOriginList,
                                       String requestMethodList,
                                       String allowHeaderList,
                                       String exposedHeaders) {
}
