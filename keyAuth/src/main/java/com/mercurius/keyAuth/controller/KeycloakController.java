package com.mercurius.keyAuth.controller;

import java.util.List;

import org.keycloak.representations.idm.ClientRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mercurius.keyAuth.dto.ErrorResponseDto;
import com.mercurius.keyAuth.dto.ResponseDto;
import com.mercurius.keyAuth.exception.NetworkException;
import com.mercurius.keyAuth.service.serviceImpl.KeycloakClientService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/admin")
public class KeycloakController {

	KeycloakClientService keycloakClientService;

	@Autowired
	public KeycloakController(KeycloakClientService keycloakClientService) {
		super();

		this.keycloakClientService = keycloakClientService;
	}

	@PostMapping("/create-client")
	public ResponseEntity<ResponseDto> createClient() {
		try {
			String accessToken = keycloakClientService.getAdminAccessToken();
			ResponseEntity<String> message = keycloakClientService.createClient(accessToken);
			return ResponseEntity.status(200).body(new ResponseDto("200", "Client Created SuccessFully"));
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDto("417", "Create operation failed. Please try again or contact Dev team"));
		}

	}
	@GetMapping("/clients")
	public   ResponseEntity<List<ClientRepresentation>> getAllClients() throws Exception{
		try {			
			return ResponseEntity.status(200).body(keycloakClientService.getAllClients());
		} catch (Exception e) {
				throw new NetworkException(e.getMessage());	
		}
	}
	@GetMapping("/client")
	public   ResponseEntity<ClientRepresentation> getClient(@RequestParam String clientId) throws Exception{
			if(keycloakClientService.getClient(clientId).equals(null)) {
				throw new NullPointerException("Client with clientId " + clientId + " not found");
			}
			return ResponseEntity.status(200).body(keycloakClientService.getClient(clientId));
		
	}
	@PutMapping("/update-client")
	public   ResponseEntity<ClientRepresentation> updateClient(@RequestParam String clientId) throws Exception{
		try {			
			return ResponseEntity.status(200).body(keycloakClientService.updateClient(clientId));
		} catch (Exception e) {
				System.out.println("Ex:"+e.getMessage());
				return null;
		}
	}
	
}
