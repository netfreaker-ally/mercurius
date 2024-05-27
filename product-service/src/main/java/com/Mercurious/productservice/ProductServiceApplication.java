package com.Mercurious.productservice;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.Mercurious.productservice.entity.ProductsConfiguration;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties(value = ProductsConfiguration.class)
@EnableDiscoveryClient
public class ProductServiceApplication {
	@Autowired
	private DataSource dataSource;
	
	public ProductServiceApplication(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}
	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}
	@PostConstruct
	public void testConnection() throws SQLException {
		System.out.println("Connecting to MySQL...");
		Connection connection = dataSource.getConnection();
		System.out.println("Connection successful: " + connection.getCatalog());
		connection.close();
	}
}
