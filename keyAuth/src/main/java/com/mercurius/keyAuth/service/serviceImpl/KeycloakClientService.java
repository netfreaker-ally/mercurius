package com.mercurius.keyAuth.service.serviceImpl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.keycloak.representations.idm.ClientRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mercurius.keyAuth.models.KeyCloakConfiguration;
import com.mercurius.keyAuth.service.IkeycloakService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class KeycloakClientService implements IkeycloakService {
	@Autowired
	private KeyCloakConfiguration keyCloakConfiguration;
	@Autowired
	private final RestTemplate restTemplate;

	public KeycloakClientService(KeyCloakConfiguration keyCloakConfiguration, RestTemplate restTemplate) {
		super();
		this.keyCloakConfiguration = keyCloakConfiguration;
		this.restTemplate = restTemplate;
	}

	@Override
	public String getAdminAccessToken() {
		// TODO Auto-generated method stub
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		System.out.println("keycloakConfig" + keyCloakConfiguration);
		String urlEncodedBody = "";
		try {
			urlEncodedBody = "grant_type=password" + "&client_id="
					+ URLEncoder.encode(keyCloakConfiguration.clientId(), "UTF-8") + "&username="
					+ URLEncoder.encode(keyCloakConfiguration.adminUser(), "UTF-8") + "&password="
					+ URLEncoder.encode(keyCloakConfiguration.adminPassword(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HttpEntity<String> request = new HttpEntity<>(urlEncodedBody, headers);

		ResponseEntity<Map> response = restTemplate.postForEntity(keyCloakConfiguration.tokenEndpoint(), request,
				Map.class);

		if (response.getStatusCode().is2xxSuccessful()) {
			Map<String, Object> body = response.getBody();
			System.out.println("AccessToken is Generated----------------------------");
			return (String) body.get("access_token");
		} else {
			throw new RuntimeException("Failed to obtain access token");
		}
	}

	@Override
	public ResponseEntity<String> createClient(String accessToken, ClientRepresentation clientRepresentation) {
		// TODO Auto-generated method stub
		String clientEndpoint = String.format("%s/admin/realms/%s/clients", keyCloakConfiguration.authServerUrl(),
				keyCloakConfiguration.realm());
		ClientRepresentation client = new ClientRepresentation();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		client.setClientId("private_client123");
		client.setServiceAccountsEnabled(true);
		HttpEntity<ClientRepresentation> entity = new HttpEntity<>(client, headers);

		return restTemplate.postForEntity(clientEndpoint, entity, String.class);
		
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

}
