package com.mercurius.keyAuth.controller;

import java.net.http.HttpClient.Redirect;
import java.util.List;

import org.keycloak.representations.idm.ClientRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.mercurius.keyAuth.dto.ResponseDto;
import com.mercurius.keyAuth.service.serviceImpl.KeycloakClientService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/admin")
public class KeycloakController {

	KeycloakClientService keycloakClientService;
	Redirect redirrect;
	@Autowired
	public KeycloakController(KeycloakClientService keycloakClientService) {
		super();

		this.keycloakClientService = keycloakClientService;
	}
	@GetMapping("/login")
	public RedirectView login() {
	    return new RedirectView("http://localhost:8080/admin/master/console/");
	}
	@GetMapping("/welcome")
	public String welcome() {
	    return new String("Welcome.........../");
	}
	@PostMapping("/register")
	public ResponseEntity<ResponseDto> registerUser() {
		try {
		
			ResponseEntity<String> message = keycloakClientService.registerUser();
			return ResponseEntity.status(200).body(new ResponseDto("200", "User Created SuccessFully"));
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDto("417", "Create operation failed. Please try again or contact Dev team"));
		}

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
	public   ResponseEntity<List<ClientRepresentation>> getAllClients() {
					
			return ResponseEntity.status(200).body(keycloakClientService.getAllClients());
		
	}
	@GetMapping("/client")
	public   ResponseEntity<ClientRepresentation> getClient(@RequestParam String clientId) throws Exception{
			
			return ResponseEntity.status(200).body(keycloakClientService.getClient(clientId));
		
	}
	@PutMapping("/update-client")
	public   ResponseEntity<ResponseDto> updateClient(@RequestParam String clientId) {
		boolean isUpdated = keycloakClientService.updateClient(clientId);
		if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto("200", "Request processed successfully"));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto("417","Update operation failed. Please try again or contact Dev team"));
        }
		
		
	}
	@DeleteMapping("/delete-client")
	public   ResponseEntity<ResponseDto> deleteClient(@RequestParam String clientId) {
		 boolean isDeleted = keycloakClientService.deleteClient(clientId);
	        if(isDeleted) {
	            return ResponseEntity
	                    .status(HttpStatus.OK)
	                    .body(new ResponseDto("200", "Request processed successfully"));
	        }else{
	            return ResponseEntity
	                    .status(HttpStatus.EXPECTATION_FAILED)
	                    .body(new ResponseDto("417","Delete operation failed. Please try again or contact Dev team"));
	        }
	    }
	}
	

