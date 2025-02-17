package com.mercurius.keyAuth.service;

import java.util.List;

import org.keycloak.representations.idm.ClientRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public interface IkeycloakService {
	ResponseEntity<String> createClient(String accessToken);

	boolean deleteClient(String clientId);

	boolean updateClient(String clientId);

	ClientRepresentation getClient(String clientId);

	List<ClientRepresentation> getAllClients();

	String getAdminAccessToken();
	ResponseEntity<String> registerUser();

}
