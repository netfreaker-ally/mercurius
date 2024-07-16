package com.Mercurius.Bridge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Mercurius.Bridge.entity.AccountRepresentation;
import com.Mercurius.Bridge.service.serviceImpl.AccountsWebclientServices;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/bridge/account-webclient")
@Slf4j

public class AccountsWebClientController {
	@Autowired
	private AccountsWebclientServices accountsWebclientServices;

	@GetMapping
	public Mono<ResponseEntity<List<AccountRepresentation>>> getAccount() {
		return accountsWebclientServices.getAccounts().collectList().log().flatMap(accounts -> {
			if (accounts.isEmpty()) {
				return Mono.just(ResponseEntity.notFound().build());
			} else {
				log.info("accounts are: " + accounts.toString());
				return Mono.just(ResponseEntity.ok(accounts));
			}
		});
	}

}
