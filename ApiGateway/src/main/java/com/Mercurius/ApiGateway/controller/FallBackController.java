package com.Mercurius.ApiGateway.controller;

import java.net.URI;
import java.net.http.HttpClient.Redirect;

import org.apache.http.impl.client.RedirectLocations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
//import org.springframework.security.oauth2.client.OAuth2RestTemplate
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class FallBackController {
	@Autowired
	public WebSessionServerSecurityContextRepository webSessionServerSecurityContextRepository;

	@GetMapping("/")
	public String test() {
		log.error("----------Logged In SuccessFully--------------");
		URI redirectUri=URI.create("redirect:http://localhost:1312/services");
	
		return "redirect:http://localhost:1312/services";
	}

	@GetMapping("/Support")
	public String fallbackmethod() {
		return "An Error occured in gateway server";
	}

	@GetMapping("/accounts")
	public String accountsFallbackMethod() {
		return "An Error occured in accounts -ApiGateway";
	}

	@GetMapping("/eligibility-service")
	public String eligibilityserviceFallbackMethod() {
		return "An Error occured in eligibility-service -ApiGateway";
	}

	@GetMapping("/order")
	public String ordertsFallbackMethod() {
		return "An Error occured in order -ApiGateway";
	}

	@GetMapping("/product")
	public String productFallbackMethod() {
		return "An Error occured in product -ApiGateway";
	}

	@GetMapping("/allServices")
	private String allservices() {
		// TODO Auto-generated method stub
		return "SuccessFully Logged In /n You can access our all Services now";
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
