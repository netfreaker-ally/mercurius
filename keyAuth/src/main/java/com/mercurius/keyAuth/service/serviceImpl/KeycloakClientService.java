package com.mercurius.keyAuth.service.serviceImpl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.keycloak.representations.idm.ClientRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
			return (String) body.get("access_token");
		} else {
			throw new RuntimeException("Failed to obtain access token");
		}
	}

	@Override
	public ResponseEntity<String> createClient(String accessToken) {

		ClientRepresentation client = new ClientRepresentation();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		client.setServiceAccountsEnabled(true);
		client.setId("id2");
		client.setDescription("desc of id2");
		HttpEntity<ClientRepresentation> entity = new HttpEntity<>(client, headers);
		String clientEndpoint = "http://localhost:8080/admin/realms/master/clients?realm=" + client.getClientId();
		return restTemplate.postForEntity(clientEndpoint, entity, String.class);
	}

	@Override
	public String deleteClient(String clientId) {
		return null;
	}

	@Override
	public ClientRepresentation updateClient(String clientId) {
		ClientRepresentation client = getClient(clientId);

		client.setClientId("new_client_id_1");
		System.out.println("client: " + client);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setBearerAuth(getAdminAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		String clientEndpoint = "http://localhost:8080/admin/realms/master/clients/" + client.getId();

		HttpEntity<ClientRepresentation> entity = new HttpEntity<>(client, httpHeaders);

		try {
			ResponseEntity<ClientRepresentation> response = restTemplate.exchange(clientEndpoint, HttpMethod.PUT,
					entity, ClientRepresentation.class);
			return response.getBody();
		} catch (Exception e) {
			System.out.println("Excep In update method:\t" + e.getMessage());
			return null;
		}
	}

	@Override
	public ClientRepresentation getClient(String clientId) {
	    List<ClientRepresentation> clients = getAllClients();
	    for (ClientRepresentation client : clients) {
	        if (client.getClientId().equals(clientId)) {
	            return client; 
	        }
	    }
	  
	    throw new NullPointerException("Client with clientId '" + clientId + "' not found");
	}



	@Override
	public List<ClientRepresentation> getAllClients() {
		String accessToken = getAdminAccessToken();
		String clientEndpoint = "http://localhost:8080/admin/realms/master/clients?realm=master";

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setBearerAuth(accessToken);
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);

		ResponseEntity<List<ClientRepresentation>> responseEntity = restTemplate.exchange(clientEndpoint,
				HttpMethod.GET, entity, new ParameterizedTypeReference<List<ClientRepresentation>>() {
				});

		if (responseEntity.getStatusCode().is2xxSuccessful()) {
			return responseEntity.getBody();
		} else {

			return Collections.emptyList();
		}
	}

}
