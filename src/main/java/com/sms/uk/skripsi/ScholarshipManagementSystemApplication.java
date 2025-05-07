package com.sms.uk.skripsi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ConfigurationPropertiesScan("com.sms.uk.skripsi.core.properties")
@EnableAsync
public class ScholarshipManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScholarshipManagementSystemApplication.class, args);
	}

}
