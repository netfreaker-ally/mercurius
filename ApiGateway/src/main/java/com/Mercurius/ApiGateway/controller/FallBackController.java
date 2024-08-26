package com.Mercurius.ApiGateway.controller;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.security.oauth2.client.OAuth2RestTemplate
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class FallBackController {
	@RequestMapping("/Support")
	public String fallbackmethod() {
		return "An Error occured in gateway server";
	}

	@GetMapping("/home")
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
