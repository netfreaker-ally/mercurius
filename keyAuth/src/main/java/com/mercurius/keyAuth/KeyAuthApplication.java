package com.mercurius.keyAuth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.mercurius.keyAuth.models.KeyCloakConfiguration;

@SpringBootApplication
@EnableConfigurationProperties(KeyCloakConfiguration.class)
public class KeyAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(KeyAuthApplication.class, args);
	}
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
