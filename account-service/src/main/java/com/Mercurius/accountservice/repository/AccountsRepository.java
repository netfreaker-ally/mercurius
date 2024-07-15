package com.Mercurius.accountservice.repository;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.Mercurius.accountservice.entity.AccountRepresentation;

import reactor.core.publisher.Mono;

public interface AccountsRepository extends ReactiveMongoRepository<AccountRepresentation, String> {
	Mono<AccountRepresentation> findByAccountId(String accountId);

	Mono<Void> deleteByAccountId(String accountId);
}
