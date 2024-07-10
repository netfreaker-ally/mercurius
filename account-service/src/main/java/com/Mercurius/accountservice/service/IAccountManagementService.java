package com.Mercurius.accountservice.service;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;


import reactor.core.publisher.Mono;

public interface IAccountManagementService {
	Mono<ServerResponse> createAccount(ServerRequest serverRequest);

	Mono<ServerResponse> getAccounts(ServerRequest serverRequest);

	Mono<ServerResponse> updateAccount(ServerRequest serverRequest);

	Mono<ServerResponse> deleteAccount(ServerRequest serverRequest);



}
