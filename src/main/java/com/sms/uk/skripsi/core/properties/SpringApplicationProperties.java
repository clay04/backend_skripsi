package com.sms.uk.skripsi.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.application")
public record SpringApplicationProperties(
        String id,
        String name,
        String version) {
}
