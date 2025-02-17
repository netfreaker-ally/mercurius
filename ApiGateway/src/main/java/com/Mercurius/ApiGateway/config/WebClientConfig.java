package com.Mercurius.ApiGateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

	@Bean
	WebClient webClient() {
		return WebClient.builder().build();
	}
	@Bean
	WebSessionServerSecurityContextRepository getsecuritybean() {
		return new WebSessionServerSecurityContextRepository();
	}
}
