package com.Mercurius.accountservice.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.Mercurius.accountservice.entity.AccountRepresentation;

import reactor.core.publisher.Mono;

public interface AccountsRepository extends ReactiveCrudRepository<AccountRepresentation, Long> {
	Mono<AccountRepresentation> findByAccountId(String accountId);

	void deleteByAccountId(String accountId);
}
