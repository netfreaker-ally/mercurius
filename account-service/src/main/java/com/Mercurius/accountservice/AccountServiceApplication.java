package com.Mercurius.accountservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.Mercurius.accountservice.entity.AccountsConfiguration;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties(value = AccountsConfiguration.class)
@EnableDiscoveryClient
public class AccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
	}
	@Bean
	public RestTemplate getTemplate() {
		return new RestTemplate();
	}

}
