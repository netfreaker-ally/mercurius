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
@EnableDiscoveryClient
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator eazyBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes().route(p -> p.path("/mercurius/accounts/**")
				.filters(f -> f.rewritePath("/mercurius/accounts/(?<segment>.*)", "/${segment}")
						.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
						.addResponseHeader("Responsible", "NetFreaker").circuitBreaker(
								config -> config.setName("accountsCircuitBreaker").setFallbackUri("forward:/Support")))
				.uri("lb://ACCOUNT-SERVICE"))
				.route(p -> p.path("/mercurius/products/**")
						.filters(f -> f.rewritePath("/mercurius/products/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								.addResponseHeader("Responsible", "Hanuma")
								.retry(retryConfig -> retryConfig.setRetries(3).setMethods(HttpMethod.GET)
										.setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true)))
						.uri("lb://PRODUCT-SERVICE"))
				.route(p -> p.path("/mercurius/eligibility/**")
						.filters(f -> f.rewritePath("/mercurius/eligibility/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								.addResponseHeader("Responsible", "Hanuma Ramavath").circuitBreaker(config -> config
										.setName("loansCircuitBreaker").setFallbackUri("forward:/Support")))
						.uri("lb://ELIGIBILITY-SERVICE"))
				.build();

	}
}