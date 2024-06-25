package com.Mercurius.ApiGateway;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	RouteLocator eazyBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes()
				.route(p -> p.path("/mercurius/accounts/**")
						.filters(f -> f.rewritePath("/mercurius/accounts/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								.addResponseHeader("Responsible", "NetFreaker")
								.circuitBreaker(config -> config.setName("accountsserviceCircuitBreaker")
										.setFallbackUri("forward:/Support")))
						.uri("http://localhost:8083/"))
				.route(p -> p.path("/mercurius/products/**")
						.filters(f -> f.rewritePath("/mercurius/products/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								.addResponseHeader("Responsible", "Hanuma")
								.retry(retryConfig -> retryConfig.setRetries(3).setMethods(HttpMethod.GET)
										.setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true)))
						.uri("http://localhost:8084/"))
				.route(p -> p.path("/mercurius/eligibility-service/**").filters(f -> f
						.rewritePath("/mercurius/eligibility-service/(?<segment>.*)", "/${segment}")
						.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
						.addResponseHeader("Responsible", "Hanuma Ramavath").circuitBreaker(config -> config
								.setName("eligibility-serviceCircuitBreaker").setFallbackUri("forward:/Support")))
						.uri("http://localhost:8085/")

				)
				.route(p -> p.path("/mercurius/bridge/**")
						.filters(f -> f.rewritePath("/mercurius/bridge/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								.addResponseHeader("Responsible", "Hanuma Ramavath").circuitBreaker(config -> config
										.setName("bridgeCircuitBreaker").setFallbackUri("forward:/Support")))
						.uri("http://localhost:8082/")

				)
				.route(p -> p.path("/mercurius/order/**")
						.filters(f -> f.rewritePath("/mercurius/order/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								.addResponseHeader("Responsible", "Hanuma Ramavath").circuitBreaker(config -> config
										.setName("orderCircuitBreaker").setFallbackUri("forward:/Support")))
						.uri("http://localhost:9092/")

				).build();

	}
}
