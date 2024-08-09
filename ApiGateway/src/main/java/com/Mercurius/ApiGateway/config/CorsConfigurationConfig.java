package com.Mercurius.ApiGateway.config;


import java.util.Collections;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.server.ServerWebExchange;

public class CorsConfigurationConfig implements CorsConfigurationSource {

	@Override
	public CorsConfiguration getCorsConfiguration(ServerWebExchange exchange) {
		// TODO Auto-generated method stub
		CorsConfiguration config = new CorsConfiguration();
		
		config.setAllowedOrigins(Collections.singletonList("http://mercurius/"));
		config.setAllowedMethods(Collections.singletonList("*"));
		config.setAllowCredentials(true);
		config.setAllowedHeaders(Collections.singletonList("*"));
//		config.setExposedHeaders(Arrays.asList("Authorization"));
		config.setMaxAge(3600L);
		return config;
	}

}
