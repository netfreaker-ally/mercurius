package com.Mercurius.ApiGateway.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.security.web.server.csrf.ServerCsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
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
				.jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(grantedAuthoritiesExtractor())))
			.exceptionHandling(exceptionHandlingSpec -> exceptionHandlingSpec
				.accessDeniedHandler((exchange, denied) -> {
					@SuppressWarnings("unused")
					Mono<String> email = getUserDetails();
					// logic to send email as i have a notification i need to create a queue for it

					exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
					return exchange.getResponse().setComplete();
				}))
			.cors(corsCustomizer -> corsCustomizer.configurationSource(corsConfigurationSource()))
			.csrf(csrf -> csrf.csrfTokenRequestHandler(new ServerCsrfTokenRequestAttributeHandler())
				.csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse()))
			.logout(customizer -> customizer.logoutUrl("/home"));

		return serverHttpSecurity.build();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Collections.singletonList("http://mercurius"));
		config.setAllowedMethods(Collections.singletonList("*"));
		config.setAllowCredentials(true);
		config.setAllowedHeaders(Collections.singletonList("*"));
		config.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		return source;
	}

	private Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
		return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
	}

	public Mono<String> getUserDetails() {
		return ReactiveSecurityContextHolder.getContext().map(securityContext -> {
			Authentication authentication = securityContext.getAuthentication();
			if (authentication != null) {

				Jwt jwt = (Jwt) authentication.getPrincipal();
				String email = jwt.getClaim("email");

				return email;
			}
			return "UserUnknown";
		});
	}

	


//	@Bean
//	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//		CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
//		requestHandler.setCsrfRequestAttributeName("_csrf");
//		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//				.cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
//
//					@Override
//					public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
//						CorsConfiguration config = new CorsConfiguration();
//						config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
//						config.setAllowedMethods(Collections.singletonList("*"));
//						config.setAllowCredentials(true);
//						config.setAllowedHeaders(Collections.singletonList("*"));
//						config.setExposedHeaders(Arrays.asList("Authorization"));
//						config.setMaxAge(3600L);
//						return config;
//					}
//				}))
//				.csrf((csrf) -> csrf.csrfTokenRequestHandler(new ServerCsrfTokenRequestAttributeHandler())
//						
//						.ignoringRequestMatchers("/contact", "/register")
//						.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
////				.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
////				.addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
////				.addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
////				.addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
////				.addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
////				.addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
//				.authorizeHttpRequests((requests) -> requests.requestMatchers("/myAccount").hasRole("USER")
//						.requestMatchers("/myBalance").hasAnyRole("USER", "ADMIN").requestMatchers("/myLoans")
//						.authenticated().requestMatchers("/myCards").hasRole("USER").requestMatchers("/user")
//						.authenticated().requestMatchers("/notices", "/contact", "/register").permitAll())
////				.formLogin(Customizer.withDefaults()).httpBasic(Customizer.withDefaults());
//		return http.build();
//	}
//
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}

}
