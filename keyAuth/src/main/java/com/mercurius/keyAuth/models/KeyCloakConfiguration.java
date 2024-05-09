package com.mercurius.keyAuth.models;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "keycloak")
public record KeyCloakConfiguration(String authServerUrl,
	    String realm,
	    String adminUser,
	    String adminPassword,
	    String clientId,
	    String tokenEndpoint ) {
}
