package com.mercurius.keyAuth.controller;

import org.keycloak.representations.idm.ClientRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercurius.keyAuth.service.serviceImpl.KeycloakClientService;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@RestController
@RequestMapping(path="/admin")
public class KeycloakController {
		
	KeycloakClientService keycloakClientService;
	@Autowired
	public KeycloakController( KeycloakClientService keycloakClientService) {
		super();
		
		this.keycloakClientService = keycloakClientService;
	}

	@PostMapping("/create-client")
	public ResponseEntity<String> createClient(@RequestBody ClientRepresentation  clientRepresentaion) {
		String accessToken="";
		try {
			accessToken=keycloakClientService.getAdminAccessToken();
			ResponseEntity<String> message=keycloakClientService.createClient(accessToken, clientRepresentaion);
			System.out.println("out: "+message);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("ExceptionOccured: "+e.getMessage().toString());
		}
		return ResponseEntity.status(200).body("CreatedClient");
		
	}
}
