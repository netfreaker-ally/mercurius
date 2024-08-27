package com.Mercurius.ApiGateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j

public class KeycloakLogoutHandler implements ServerLogoutHandler {
	@Autowired
	private WebClient webClient;

	private static final String keycloakLogoutUrl = "http://localhost:8080/realms/master/protocol/openid-connect/logout";

	@Override
	public Mono<Void> logout(WebFilterExchange exchange, Authentication authentication) {
		// Extract ID token from the authentication
		if (authentication != null && authentication.getPrincipal() instanceof OidcUser) {
			log.info("authentication is instance of oidc" + authentication.getPrincipal());
			OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
			String idToken = oidcUser.getIdToken().getTokenValue();

			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(keycloakLogoutUrl)
					.queryParam("id_token_hint", idToken);

			// Perform the logout request to Keycloak
			return webClient.get().uri(builder.toUriString()).retrieve().toBodilessEntity().then();
		}
		log.info("authentication is not a  instance of oidc ot may be null");
		return Mono.empty();

	}

}
