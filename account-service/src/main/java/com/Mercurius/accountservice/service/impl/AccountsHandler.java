package com.Mercurius.accountservice.service.impl;

import java.util.Date;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.Mercurius.accountservice.entity.AccountRepresentation;
import com.Mercurius.accountservice.exception.AccountDataException;
import com.Mercurius.accountservice.exception.CustomerAlreadyExistsException;
import com.Mercurius.accountservice.exception.ResourceNotFoundException;
import com.Mercurius.accountservice.repository.AccountsRepository;
import com.Mercurius.accountservice.service.IAccountManagementService;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class AccountsHandler implements IAccountManagementService {

	@Autowired
	private AccountsRepository accountRepository;
	@Autowired
	private Validator validator;
	Date utilDate = new Date();

	@Override
	public Mono<ServerResponse> createAccount(ServerRequest serverRequest) {
	    return serverRequest.bodyToMono(AccountRepresentation.class)
	            .flatMap(this::validateAndSaveAccount)
	            .flatMap(savedAccount -> ServerResponse.status(HttpStatus.CREATED).bodyValue(savedAccount));
	}

	private Mono<AccountRepresentation> validateAndSaveAccount(AccountRepresentation newAccount) {
	    return validateConstraints(newAccount)
	            .then(accountRepository.findByAccountId(newAccount.getAccountId()))
	            .flatMap(existingAccount -> Mono.error(new CustomerAlreadyExistsException(
	                    "Customer already registered with given accountId " + newAccount.getAccountId())))
	            .switchIfEmpty(Mono.defer(() -> accountRepository.save(newAccount)))
	            .cast(AccountRepresentation.class); 
	}





	private Mono<AccountRepresentation> validateConstraints(AccountRepresentation accountDetails) {
		var constraintViolations = validator.validate(accountDetails);
		if (!constraintViolations.isEmpty()) {
			var errorMessage = constraintViolations.stream().map(ConstraintViolation::getMessage).sorted()
					.collect(Collectors.joining(","));
			return Mono.error(new AccountDataException(errorMessage));
		}
		return Mono.just(accountDetails);
	}

	@Override
	public Mono<ServerResponse> getAccounts(ServerRequest serverRequest) {

		var accountId = serverRequest.queryParam("accountId");
		if (accountId.isPresent()) {
			var accounts = accountRepository.findByAccountId(String.valueOf(accountId.get()));
			return buildServerResponse(accounts);
		} else {
			var allAccounts = accountRepository.findAll();
			return buildServerResponse(allAccounts);
		}
	}

	private Mono<ServerResponse> buildServerResponse(Mono<AccountRepresentation> account) {
		return account.flatMap(acc -> ServerResponse.status(HttpStatus.OK).bodyValue(acc))
				.switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).build());
	}

	private Mono<ServerResponse> buildServerResponse(Flux<AccountRepresentation> accounts) {
		return accounts

				.collectList().log().flatMap(accList -> {
					if (accList.isEmpty()) {
						return ServerResponse.status(HttpStatus.NOT_FOUND).build();
					}
					return ServerResponse.status(HttpStatus.OK).bodyValue(accList);
				});
	}

	@Override
	public Mono<ServerResponse> updateAccount(ServerRequest serverRequest) {
		return serverRequest.bodyToMono(AccountRepresentation.class).doOnNext(this::validateConstraints)

				.flatMap(acc -> {
					return accountRepository.findByAccountId(acc.getAccountId()).flatMap(account -> {
						account.setAccountName(acc.getAccountName());
						account.setAccountType(acc.getAccountType());
						account.setBalance(acc.getBalance());
						account.setUpdatedDate(new java.sql.Date(utilDate.getTime()));
						account.setActive(acc.isActive());
						return accountRepository.save(account);
					}).switchIfEmpty(
							Mono.error(new ResourceNotFoundException("Account", "accountId", acc.getAccountId())))
							.flatMap(savedAccount -> buildServerResponse(Mono.just(savedAccount)));
				});

	}

	@Override
	public Mono<ServerResponse> deleteAccount(ServerRequest serverRequest) {
		var accountId = serverRequest.pathVariable("accountId");
		return accountRepository.findByAccountId(accountId).flatMap(account -> accountRepository.delete(account))
				.then(ServerResponse.noContent().build());
	}

}
