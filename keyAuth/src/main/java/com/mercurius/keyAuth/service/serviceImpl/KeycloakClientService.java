package com.mercurius.keyAuth.service.serviceImpl;

import java.util.List;

import org.keycloak.representations.idm.ClientRepresentation;

import com.mercurius.keyAuth.service.IkeycloakService;

public class KeycloakClientService  implements IkeycloakService{

	@Override
	public String createClient(String accessToken, String clientId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteClient(String clientId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateClient(String clientId, ClientRepresentation clientRepresentaion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientRepresentation getClient(String clientId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClientRepresentation> getAllClients() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAdminAccessToken() {
		// TODO Auto-generated method stub
		return null;
	}

}
