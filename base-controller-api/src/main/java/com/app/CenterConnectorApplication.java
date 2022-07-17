package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableConfigurationProperties
@EnableSwagger2
@SpringBootApplication(exclude = { ErrorMvcAutoConfiguration.class })
@EnableScheduling
public class CenterConnectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CenterConnectorApplication.class, args);
	}
}
