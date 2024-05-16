package com.mercurius.keyAuth.service.serviceImpl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mercurius.keyAuth.exception.ResourceNotFoundException;
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

		String urlEncodedBody = "";
		try {
			urlEncodedBody = "grant_type=password" + "&client_id="
					+ URLEncoder.encode(keyCloakConfiguration.clientId(), "UTF-8") + "&username="
					+ URLEncoder.encode(keyCloakConfiguration.adminUser(), "UTF-8") + "&password="
					+ URLEncoder.encode(keyCloakConfiguration.adminPassword(), "UTF-8");
		} catch (UnsupportedEncodingException e) {

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
		client.setClientId("id3");

		client.setDescription("desc of clientid2");
		HttpEntity<ClientRepresentation> entity = new HttpEntity<>(client, headers);
		String clientEndpoint = "http://localhost:8080/admin/realms/master/clients?realm=" + client.getClientId();
		return restTemplate.postForEntity(clientEndpoint, entity, String.class);

	}

	public Map<String, Object> getConfig(String clientId) {
		ClientRepresentation client = getClient(clientId);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setBearerAuth(getAdminAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		String clientEndpoint = "http://localhost:8080/admin/realms/master/clients/" + client.getId();
		HttpEntity<ClientRepresentation> entity = new HttpEntity<>(client, httpHeaders);

		Map<String, Object> response = new HashMap<>();
		response.put("entity", entity);
		response.put("httpHeaders", httpHeaders);
		response.put("clientEndpoint", clientEndpoint);

		return response;
	}

	public boolean deleteClient(String clientId) {
		Map<String, Object> responseMap = getConfig(clientId);
		HttpEntity<ClientRepresentation> entity = (HttpEntity<ClientRepresentation>) responseMap.get("entity");
		HttpHeaders httpHeaders = (HttpHeaders) responseMap.get("httpHeaders");
		String clientEndpoint = (String) responseMap.get("clientEndpoint");
		
		boolean isDeleted = false;
		ResponseEntity<String> response = restTemplate.exchange(clientEndpoint, HttpMethod.DELETE, entity,
				String.class);
		if (response.getStatusCode().is2xxSuccessful()) {
			isDeleted = true;
		}
		return isDeleted;
	}

	@Override
	public boolean updateClient(String clientId) {
		try {
			ClientRepresentation client = getClient(clientId);
			Map<String, Object> responseMap = getConfig(clientId);
			HttpHeaders httpHeaders = (HttpHeaders) responseMap.get("httpHeaders");
			String clientEndpoint = (String) responseMap.get("clientEndpoint");
			client.setDescription("hi................s");
			HttpEntity<ClientRepresentation> entity = new HttpEntity<>(client, httpHeaders);

			ResponseEntity<String> response = restTemplate.exchange(clientEndpoint, HttpMethod.PUT, entity,
					String.class);
			return response.getStatusCode().is2xxSuccessful();
		} catch (ResourceNotFoundException ex) {
			throw ex;

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
		throw new ResourceNotFoundException("clientId", clientId, "");
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

	@Override
	public ResponseEntity<String> registerUser() {
		UserRepresentation user =new UserRepresentation();
	    user.setUsername("john.doe");
	    user.setFirstName("John");
	    user.setLastName("Doe");
	    user.setEmail("john.doe@example.com");
	    user.setEnabled(true);
	    user.setEmailVerified(true);
	 
	    HttpHeaders headers = new HttpHeaders();
	    String accessToken=getAdminAccessToken();
		headers.setBearerAuth(accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
	
		HttpEntity<UserRepresentation> entity = new HttpEntity<>(user, headers);
		String clientEndpoint = "http://localhost:8080/admin/realms/master/users";
		return restTemplate.postForEntity(clientEndpoint, entity, String.class);
		
	}

}
