package com.Mercurius.accountservice;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.Mercurius.accountservice.entity.AccountsConfiguration;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties(value = AccountsConfiguration.class)
@EnableDiscoveryClient
public class AccountServiceApplication {

	@Autowired
	private DataSource dataSource;

	public AccountServiceApplication(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	public AccountServiceApplication() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
	}

	@Bean
	public RestTemplate getTemplate() {
		return new RestTemplate();
	}

	@PostConstruct
	public void testConnection() throws SQLException {
		System.out.println("Connecting to MySQL...");
		Connection connection = dataSource.getConnection();
		System.out.println("Connection successful: " + connection.getCatalog());
		connection.close();
	}
}
