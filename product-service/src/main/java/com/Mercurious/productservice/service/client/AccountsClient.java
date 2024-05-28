package com.Mercurious.productservice.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.Mercurious.productservice.dto.ResponseDto;
import com.Mercurious.productservice.entity.AccountRepresentation;

import jakarta.validation.Valid;

@FeignClient(name = "ACCOUNT-SERVICE")
public interface AccountsClient {
	@PostMapping("/api/create")
	 ResponseEntity<ResponseDto> createAccount(@Valid@RequestBody AccountRepresentation account) ;
	
	

}
