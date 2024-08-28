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

import com.Mercurius.ApiGateway.filter.CustomRedirectFilter;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	private CustomRedirectFilter customRedirectFilter;

	public ApiGatewayApplication(CustomRedirectFilter customRedirectFilter) {
		this.customRedirectFilter = customRedirectFilter;
	}

	@Bean
	RouteLocator eazyBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes().route("accounts", p -> p.path("/mercurius/accounts/**").filters(f -> f
				.rewritePath("/mercurius/accounts/(?<segment>.*)", "/${segment}")

				.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
				.addResponseHeader("Responsible", "NetFreaker").circuitBreaker(
						config -> config.setName("accountsserviceCircuitBreaker").setFallbackUri("forward:/accounts"))

		).uri("lb://ACCOUNT-SERVICE"))

				.route("products",
						p -> p.path("/mercurius/products/**")
								.filters(f -> f.rewritePath("/mercurius/products/(?<segment>.*)", "/${segment}")
										.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
										.addResponseHeader("Responsible", "Hanuma")
										.circuitBreaker(config -> config.setName("products-serviceCircuitBreaker")
												.setFallbackUri("forward:/product"))
										.retry(retryConfig -> retryConfig.setRetries(3).setMethods(HttpMethod.GET)
												
												.setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true)))
								.uri("lb://PRODUCT-SERVICE"))

				.route("eligibility-service", p -> p.path("/mercurius/eligibility-service/**")
						.filters(f -> f.rewritePath("/mercurius/eligibility-service/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								.addResponseHeader("Responsible", "Hanuma Ramavath")
								.circuitBreaker(config -> config.setName("eligibility-serviceCircuitBreaker")
										.setFallbackUri("forward:/eligibility-service")))
						.uri("lb://ELIGIBILITY-SERVICE")

				)
				.route("bridge",
						p -> p.path("/mercurius/bridge/**")
								.filters(f -> f.rewritePath("/mercurius/bridge/(?<segment>.*)", "/${segment}")
										.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
										.addResponseHeader("Responsible", "Hanuma Ramavath")
										.circuitBreaker(config -> config.setName("bridgeCircuitBreaker")
												.setFallbackUri("forward:/bridge")))
								// .uri("http://localhost:8082/")
								.uri("lb://BRIDGE")

				).route("order", p -> {
					return p.path("/mercurius/orders/**").and().method("GET")
							.filters(f -> f.rewritePath("/mercurius/orders/(?<segment>.*)", "/${segment}")
									.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
									.addResponseHeader("Responsible", "Hanuma Ramavath").circuitBreaker(config -> config
											.setName("orderCircuitBreaker").setFallbackUri("forward:/order")))
							.uri("lb://ORDER");
				}

				).build();

	}
}
