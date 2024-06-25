package com.Mercurius.ApiGateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration

public class MercuriusSecurityConfig {
	@Bean
	SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
	    serverHttpSecurity.authorizeExchange(exchange -> exchange
	            .pathMatchers(HttpMethod.GET).permitAll()
	            .pathMatchers("/mercurius/accounts/**").hasAnyRole("USER", "ADMIN")
	            .pathMatchers("/mercurius/products/**").hasAnyRole("USER", "ADMIN")
	            .pathMatchers("/mercurius/elibility/**").hasAnyRole("ADMIN")
	            .pathMatchers("/mercurius/bridge/**").hasAnyRole("USER", "ADMIN")
	            .pathMatchers("/mercurius/order/**").hasAnyRole("USER", "ADMIN"))
	            .oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec
	                    .jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(grantedAuthoritiesExtractor())));
	    serverHttpSecurity.csrf(csrfSpec -> csrfSpec.disable());
	    return serverHttpSecurity.build();
	}

	private Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
	    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
	    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
	    return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
	}
	


	@Bean
	public ReactiveJwtAuthenticationConverterAdapter jwtConverter() {
		JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
		converter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
		return new ReactiveJwtAuthenticationConverterAdapter(converter);
	}
}
