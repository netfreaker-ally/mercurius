package com.Mercurius.ApiGateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component

@Slf4j
@AllArgsConstructor
public class CustomRedirectFilter implements GatewayFilter {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		// TODO Auto-generated method stub
		log.info("CustomRedirectFilter", "Entered in this----------");
		String requestPath = exchange.getRequest().getURI().getPath();

		if (requestPath.equals("/")) {
			return chain.filter(exchange.mutate()
					.request(exchange.getRequest().mutate()
							.uri(exchange.getRequest().getURI().resolve("http://localhost:1312/services")).build())
					.build());
		}

		return chain.filter(exchange);
	}
}
