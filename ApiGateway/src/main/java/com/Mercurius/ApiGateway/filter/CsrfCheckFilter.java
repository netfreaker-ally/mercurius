package com.Mercurius.ApiGateway.filter;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor

public class CsrfCheckFilter implements WebFilter {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		// TODO Auto-generated method stub
		log.info("Entered in Filter");

		CsrfToken csrfToken = exchange.getAttribute(CsrfToken.class.getName());

		// Log the CSRF token if it is available
		if (csrfToken != null) {
			log.info("CSRF token: {}", csrfToken.getToken());
		} else {
			log.warn("CSRF token is not available");
		}

		return chain.filter(exchange);
	}
}