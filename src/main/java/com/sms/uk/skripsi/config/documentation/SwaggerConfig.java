package com.sms.uk.skripsi.config.documentation;

import com.sms.uk.skripsi.core.properties.SpringApplicationProperties;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
@SecurityScheme(
        name = "Authorization",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SwaggerConfig
        extends WebMvcConfigurationSupport {

    private final SpringApplicationProperties springApplicationProperties;

    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI()
                .addServersItem(new Server().url("/sms-mgmt"))
                .info(new Info().title(springApplicationProperties.name())
                        .description("APIs Documentation")
                        .version(springApplicationProperties.version()));
    }

    @Bean
    public GroupedOpenApi auth() {
        return GroupedOpenApi.builder()
                .group("Authentication")
                .packagesToScan("com.sms.uk.skripsi.module.authentication.controller")
                .build();
    }

    @Bean
    public GroupedOpenApi faculty() {
        return GroupedOpenApi.builder()
                .group("Faculty")
                .packagesToScan("com.sms.uk.skripsi.module.faculty_major.controller")
                .build();
    }

    @Bean
    public GroupedOpenApi major() {
        return GroupedOpenApi.builder()
                .group("Major")
                .packagesToScan("com.sms.uk.skripsi.module.faculty_major.controller")
                .build();
    }

    @Bean
    public GroupedOpenApi userManagement() {
        return GroupedOpenApi.builder()
                .group("User")
                .packagesToScan("com.sms.uk.skripsi.module.user.controller")
                .build();
    }

    @Bean
    public GroupedOpenApi scholarManagement() {
        return GroupedOpenApi.builder()
                .group("Scholar")
                .packagesToScan("com.sms.uk.skripsi.module.scholarship.controller")
                .build();
    }
}
