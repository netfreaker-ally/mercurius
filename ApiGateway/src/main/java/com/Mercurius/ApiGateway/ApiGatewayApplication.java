package com.Mercurius.ApiGateway;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

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
	RouteLocator eazyBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes()
				.route("accounts", p -> p.path("/mercurius/accounts/**").and().cookie("cookie1", "cook123")
						.filters(f -> f.rewritePath("/mercurius/accounts/(?<segment>.*)", "/${segment}")
								.removeRequestHeader("Cookie")
								.addRequestHeader("Authorization", "Bearer {access_token}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								.addResponseHeader("Responsible", "NetFreaker").circuitBreaker(config -> config
										.setName("accountsserviceCircuitBreaker").setFallbackUri("forward:/Support"))

						).uri("lb://ACCOUNT-SERVICE"))
//				.route("products",
//						p -> p.path("/mercurius/products/**").and().cookie("cookie1", "cook123")
//								.filters(f -> f.rewritePath("/mercurius/products/(?<segment>.*)", "/${segment}")
//										.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
//										.addResponseHeader("Responsible", "Hanuma")
//										.retry(retryConfig -> retryConfig.setRetries(3).setMethods(HttpMethod.GET)
//												.setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true)
//
//										))
//
//								.uri("lb://PRODUCT-SERVICE"))
				.route("products", p -> p
					    .path("/mercurius/products/**")
					    .filters(f -> f.rewritePath("/mercurius/products/(?<segment>.*)", "/${segment}")
					        .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
					        .addResponseHeader("Responsible", "Hanuma")
					        .retry(retryConfig -> retryConfig.setRetries(3)
					            .setMethods(HttpMethod.GET)
					            .setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true)))
					    .uri("lb://PRODUCT-SERVICE"))


				.route("eligibility-service", p -> p.path("/mercurius/eligibility-service/**").filters(f -> f
						.rewritePath("/mercurius/eligibility-service/(?<segment>.*)", "/${segment}")
						.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
						.addResponseHeader("Responsible", "Hanuma Ramavath").circuitBreaker(config -> config
								.setName("eligibility-serviceCircuitBreaker").setFallbackUri("forward:/Support")))
						.uri("lb://ELIGIBILITY-SERVICE")

				)
				.route("bridge",
						p -> p.path("/mercurius/bridge/**")
								.filters(f -> f.rewritePath("/mercurius/bridge/(?<segment>.*)", "/${segment}")
										.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
										.addResponseHeader("Responsible", "Hanuma Ramavath")
										.circuitBreaker(config -> config.setName("bridgeCircuitBreaker")
												.setFallbackUri("forward:/Support")))
								// .uri("http://localhost:8082/")
								.uri("lb://BRIDGE")

				).route("order", p -> {

					return p.path("/mercurius/order/**").and().method("GET")
							.filters(f -> f.rewritePath("/mercurius/order/(?<segment>.*)", "/${segment}")

									.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
									.addResponseHeader("Responsible", "Hanuma Ramavath")
									.circuitBreaker(config -> config.setName("orderCircuitBreaker")
											.setFallbackUri("forward:/Support")))

							.uri("lb://ORDER");
				}

				).route("example_route", r -> r.path("/api/**")

						.and().method("GET").and().header("X-Custom-Header", "Value").and().host("example.com").and()
						.remoteAddr("192.168.1.100").and().query("param1", "value1").and()
						.before(ZonedDateTime.parse("2024-12-31T23:59:59Z")).and()
						.after(ZonedDateTime.parse("2024-01-01T00:00:00Z")).and().cookie("sessionId", "123456").and()

						.uri("http://example.com"))
				.build();

	}
}
