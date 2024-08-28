package com.Mercurius.ApiGateway.config;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
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
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.server.WebSessionServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.oauth2.server.resource.web.access.server.BearerTokenServerAccessDeniedHandler;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.DelegatingServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;
import org.springframework.security.web.server.authentication.logout.SecurityContextServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.WebSessionServerLogoutHandler;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.security.web.server.csrf.CsrfServerLogoutHandler;
import org.springframework.security.web.server.csrf.ServerCsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.client.WebClient;

import com.Mercurius.ApiGateway.filter.CsrfCheckFilter;
import com.Mercurius.ApiGateway.filter.CustomRedirectFilter;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@Slf4j

public class MercuriusSecurityConfig {
	// InMemoryReactiveClientRegistrationRepository

	public MercuriusSecurityConfig() {
		super();
		// TODO Auto-generated constructor stub
	}

	private KeycloakLogoutHandler keycloakLogoutHandler;
//	private ReactiveClientRegistrationRepository clientRegistrationRepository;

//	public MercuriusSecurityConfig(ReactiveClientRegistrationRepository clientRegistrationRepository) {
//		this.clientRegistrationRepository = clientRegistrationRepository;
//	}

	public MercuriusSecurityConfig(KeycloakLogoutHandler keycloakLogoutHandler) {
		this.keycloakLogoutHandler = keycloakLogoutHandler;
	}

//	public ServerLogoutSuccessHandler createLogoutSuccessHandler() {
//		OidcClientInitiatedServerLogoutSuccessHandler logoutSuccessHandler = new OidcClientInitiatedServerLogoutSuccessHandler(
//				clientRegistrationRepository);
//		// You can configure additional properties if needed
//		return logoutSuccessHandler;
//	}

	@Bean
	ReactiveJwtDecoder jwtDecoder() {
		String jwkSetUri = "http://localhost:8080/realms/master/protocol/openid-connect/certs";
		return NimbusReactiveJwtDecoder.withJwkSetUri(jwkSetUri).build();
	}

	@Bean
	public ServerOAuth2AuthorizedClientRepository authorizedClientRepository() {
		return new WebSessionServerOAuth2AuthorizedClientRepository();
	}

//	    @Bean
//	    public ServerOAuth2AuthorizedClientManager authorizedClientManager(ServerOAuth2AuthorizedClientRepository authorizedClientRepository) {
//	        return new ServerOAuth2AuthorizedClientManager(authorizedClientRepository);
//	    }
	@Bean
	SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity, WebClient webClient) {

		CookieServerCsrfTokenRepository csrfTokenRepository = CookieServerCsrfTokenRepository.withHttpOnlyFalse();
		Customizer<LogoutSpec> logCustomizer = new Customizer<ServerHttpSecurity.LogoutSpec>() {

			@Override
			public void customize(LogoutSpec t) {
				// TODO Auto-generated method stub
				RedirectServerLogoutSuccessHandler redirectserverlogoutHandler = new RedirectServerLogoutSuccessHandler();
				URI logoutSuccessUrl = URI.create("http://localhost:1312/home");
				redirectserverlogoutHandler.setLogoutSuccessUrl(logoutSuccessUrl);
				DelegatingServerLogoutHandler delegatingServerLogoutHandler = new DelegatingServerLogoutHandler(
						new SecurityContextServerLogoutHandler(), new CsrfServerLogoutHandler(csrfTokenRepository),
						new WebSessionServerLogoutHandler(), new KeycloakLogoutHandler());
				t.logoutUrl("/logout").logoutSuccessHandler(redirectserverlogoutHandler)
						// .logoutSuccessHandler(createLogoutSuccessHandler()
						// )

						.logoutHandler(delegatingServerLogoutHandler);

				// .logoutHandler(new WebSessionServerLogoutHandler());// default handler but as
				// i used
				// statefull just to remember i kept
				// this

			}
		};

		serverHttpSecurity

				// Statefull session management for browser-based OAuth2 login
				.securityContextRepository(new WebSessionServerSecurityContextRepository())
				// for stateless
				// .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())

				.authorizeExchange(exchange -> exchange.pathMatchers("/mercurius/accounts/**")
						.hasAuthority("SCOPE_admin").pathMatchers("/mercurius/products/**").authenticated()
						.pathMatchers("/mercurius/eligibility/**").hasAuthority("SCOPE_admin")
						.pathMatchers("/mercurius/bridge/**").hasAuthority("OIDC_USER")
						.pathMatchers("/mercurius/order/**").hasAnyAuthority("SCOPE_admin", "OIDC_USER").anyExchange()
						.permitAll())

				// OAuth2 Resource Server JWT handling
				.oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec
						.jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(grantedAuthoritiesExtractor())))

				// OAuth2 Login for browser-based login
				.oauth2Login(Customizer.withDefaults()) // This handles the redirect to Keycloak

				// Exception handling
				.exceptionHandling(exceptionHandling -> exceptionHandling
						// .authenticationEntryPoint(new
						// RedirectServerAuthenticationEntryPoint("/login"))
						.accessDeniedHandler(new BearerTokenServerAccessDeniedHandler()))

				.cors(corsCustomizer -> corsCustomizer.configurationSource(corsConfigurationSource()))
				.csrf(csrf -> csrf.csrfTokenRequestHandler(new ServerCsrfTokenRequestAttributeHandler())
						.csrfTokenRepository(csrfTokenRepository))

				.addFilterAfter(new CsrfCheckFilter(), SecurityWebFiltersOrder.CSRF)

				.logout(logCustomizer);

		return serverHttpSecurity.build();

	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		List<String> allowedorigins = List.of("http://mercurius/*", "http://localhost/*", "null");
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(allowedorigins);
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
