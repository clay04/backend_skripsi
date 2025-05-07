package com.sms.uk.skripsi.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "config.app")
public record ConfigAppProperties(
        String[] allowUrl,
        String baseUrlFe,
        Long expToken,
        String mailFrom,
        String imageFileDir,
        String documentFileDir,
        String imageFileGlobalDir


) {
    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "";
    }
}
