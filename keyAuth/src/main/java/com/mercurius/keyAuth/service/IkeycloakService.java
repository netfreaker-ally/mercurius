package com.mercurius.keyAuth.service;

import java.util.List;

import org.keycloak.representations.idm.ClientRepresentation;

public interface IkeycloakService {
	 String createClient(String accessToken,String clientId);
	 String deleteClient(String clientId);
	 String updateClient(String clientId,ClientRepresentation clientRepresentaion);
	 ClientRepresentation getClient(String clientId);
	 List<ClientRepresentation> getAllClients();
	 String getAdminAccessToken();

}
