package com.mercurius.order.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mercurius.order.dto.ResponseDto;
import com.mercurius.order.entity.AccountRepresentation;

import jakarta.validation.Valid;

@FeignClient(name = "ACCOUNT-SERVICE")
public interface AccountManagementClient {
	/*
	 * @PostMapping("/api/create") public ResponseEntity<ResponseDto>
	 * createAccount(@Valid@RequestBody AccountRepresentation account);
	 * 
	 * @GetMapping("/api/{id}") public ResponseEntity<AccountRepresentation>
	 * getAccountById(@PathVariable("id") String accountId);
	 * 
	 * @PutMapping("/api") public ResponseEntity<ResponseDto>
	 * updateAccount(@Valid @RequestBody AccountRepresentation account);
	 */

	@DeleteMapping("/api/{id}")
	public ResponseEntity<ResponseDto> deleteAccount(@PathVariable("id") String accountId);

	@GetMapping("/api/all-users")
	public ResponseEntity<List<AccountRepresentation>> getAllAccounts();

}
