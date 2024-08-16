package com.Mercurius.ApiGateway.controller;



import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class FallBackController {
	@RequestMapping("/Support")
	public String fallbackmethod() {
		return "An Error occured in gateway server";
	}
	@RequestMapping("/home")
	public String home() {
		return "Welcome To Home";
	}
//	@GetMapping("/csrf-test")
//	public Mono<String> testCsrf(ServerWebExchange exchange) {
//	    ServerHttpResponse response = exchange.getResponse();
//	    CsrfToken csrfToken = (CsrfToken) exchange.getAttribute(CsrfToken.class.getName());
//	    if (csrfToken != null) {
//	        response.getHeaders().set("X-CSRF-TOKEN", csrfToken.getToken());
//	        return Mono.just("CSRF token generated: " + csrfToken.getToken());
//	    } else {
//	        return Mono.just("CSRF token not available");
//	    }
//	}



}
