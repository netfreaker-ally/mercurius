package com.Mercurius.ApiGateway.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.LogoutSpec;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.oauth2.server.resource.web.access.server.BearerTokenServerAccessDeniedHandler;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.HttpBasicServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.logout.DelegatingServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.HttpStatusReturningServerLogoutSuccessHandler;
import org.springframework.security.web.server.authentication.logout.SecurityContextServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.WebSessionServerLogoutHandler;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.security.web.server.csrf.CsrfServerLogoutHandler;
import org.springframework.security.web.server.csrf.ServerCsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import com.Mercurius.ApiGateway.filter.CsrfCheckFilter;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@Slf4j
public class MercuriusSecurityConfig {

	@Bean
	SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
		CookieServerCsrfTokenRepository csrfTokenRepository = CookieServerCsrfTokenRepository.withHttpOnlyFalse();
		Customizer<LogoutSpec> logCustomizer = new Customizer<ServerHttpSecurity.LogoutSpec>() {

			@Override
			public void customize(LogoutSpec t) {
				// TODO Auto-generated method stub
				DelegatingServerLogoutHandler delegatingServerLogoutHandler = new DelegatingServerLogoutHandler(
						new SecurityContextServerLogoutHandler(), new CsrfServerLogoutHandler(csrfTokenRepository),
						new WebSessionServerLogoutHandler());
				t.logoutUrl("/logout").logoutSuccessHandler(new HttpStatusReturningServerLogoutSuccessHandler())

						.logoutHandler(delegatingServerLogoutHandler);

				// .logoutHandler(new WebSessionServerLogoutHandler());// default handler but as
				// i used
				// statefull just to remember i kept
				// this

			}
		};
		serverHttpSecurity

				.securityContextRepository(new WebSessionServerSecurityContextRepository())// default used by security
																							// for statefull for
																							// stateless i need to use
																							// NoOpServerSecurityContextRepository
				.authorizeExchange(exchange -> exchange.pathMatchers(HttpMethod.GET).authenticated()
						.pathMatchers("/mercurius/accounts/**").hasAnyRole("USER", "ADMIN")
						.pathMatchers("/mercurius/products/**").hasAnyRole("USER", "ADMIN")
						.pathMatchers("/mercurius/eligibility/**").hasAnyRole("ADMIN")
						.pathMatchers("/mercurius/bridge/**").hasAnyRole("USER", "ADMIN")
						.pathMatchers("/mercurius/order/**").hasAnyRole("USER", "ADMIN"))
				.oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec
						.jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(grantedAuthoritiesExtractor())))
				.exceptionHandling(exceptionHandling -> exceptionHandling

						.authenticationEntryPoint(new HttpBasicServerAuthenticationEntryPoint())
						.accessDeniedHandler(new BearerTokenServerAccessDeniedHandler()))
				.cors(corsCustomizer -> corsCustomizer.configurationSource(corsConfigurationSource()))
				.csrf(csrf -> csrf.csrfTokenRequestHandler(new ServerCsrfTokenRequestAttributeHandler())
						.csrfTokenRepository(csrfTokenRepository))
				.addFilterAfter(new CsrfCheckFilter(), SecurityWebFiltersOrder.CSRF).logout(logCustomizer);

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
//		config.addExposedHeader("Set-Cookie");
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

	@Bean
	PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
