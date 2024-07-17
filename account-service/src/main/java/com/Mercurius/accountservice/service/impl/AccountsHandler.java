package com.Mercurius.accountservice.service.impl;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.Mercurius.accountservice.entity.AccountRepresentation;
import com.Mercurius.accountservice.entity.OfferRepresentation;
import com.Mercurius.accountservice.exception.AccountDataException;
import com.Mercurius.accountservice.exception.CustomerAlreadyExistsException;
import com.Mercurius.accountservice.exception.OfferAlreadyExistsException;
import com.Mercurius.accountservice.exception.OfferDataException;
import com.Mercurius.accountservice.exception.ResourceNotFoundException;
import com.Mercurius.accountservice.repository.AccountsRepository;
import com.Mercurius.accountservice.repository.OfferRepository;
import com.Mercurius.accountservice.service.IAccountManagementService;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AccountsHandler implements IAccountManagementService {

	@Autowired
	private AccountsRepository accountRepository;
	@Autowired
	private Validator validator;
	@Autowired
	private OfferRepository offerRepository;
	Date utilDate = new Date();
	@Autowired
	private OrderService orderService;

	public Mono<ServerResponse> createAccount(ServerRequest serverRequest) {
	    return serverRequest.bodyToMono(AccountRepresentation.class)
	            .flatMap(this::validateAndSaveAccount)
	            .doOnNext(savedAccount -> {
	                orderService.getAccountInfoSink().tryEmitNext(savedAccount);
	                log.info("Emitted AccountRepresentation: {}", savedAccount);
	            })
	            .flatMap(savedAccount -> ServerResponse.status(HttpStatus.CREATED).bodyValue(savedAccount));
	}

	private Mono<AccountRepresentation> validateAndSaveAccount(AccountRepresentation newAccount) {
		return validateConstraints(newAccount).then(accountRepository.findByAccountId(newAccount.getAccountId()))
				.flatMap(existingAccount -> Mono.<AccountRepresentation>error(new CustomerAlreadyExistsException(
						"Customer already registered with given accountId " + newAccount.getAccountId())))
				.switchIfEmpty(Mono.defer(() -> {
					newAccount.setCreatedDate(new java.sql.Date(new java.util.Date().getTime()));
					return accountRepository.save(newAccount);
				})).cast(AccountRepresentation.class);
	}

	@Override
	public Mono<ServerResponse> createOffer(ServerRequest serverRequest) {

		return serverRequest.bodyToMono(OfferRepresentation.class).flatMap(this::validateAndSaveOffer)
				.flatMap(savedOffer -> ServerResponse.status(HttpStatus.CREATED).bodyValue(savedOffer));
	}

	private Mono<OfferRepresentation> validateAndSaveOffer(OfferRepresentation newOffer) {
		return validateOfferConstraints(newOffer).then(offerRepository.findByOfferId(newOffer.getOfferId()))
				.flatMap(existingOffer -> Mono.<OfferRepresentation>error(new OfferAlreadyExistsException(
						"Offer already registered with given offerId " + newOffer.getOfferId())))
				.switchIfEmpty(Mono.defer(() -> offerRepository.save(newOffer))).cast(OfferRepresentation.class);
	}

	private Mono<OfferRepresentation> validateOfferConstraints(OfferRepresentation offerDetails) {
		var constraintViolations = validator.validate(offerDetails);
		if (!constraintViolations.isEmpty()) {
			log.error("Error got in valid constraints");
			var errorMessage = constraintViolations.stream().map(ConstraintViolation::getMessage).sorted()
					.collect(Collectors.joining(","));
			return Mono.error(new OfferDataException(errorMessage));
		}
		return Mono.just(offerDetails);
	}

	private Mono<AccountRepresentation> validateConstraints(AccountRepresentation accountDetails) {
		var constraintViolations = validator.validate(accountDetails);
		if (!constraintViolations.isEmpty()) {
			log.error("Error got in valid constraints");
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
			String accountIdValue = accountId.get();
			log.info("Received query param accountId: " + accountIdValue);

			var accountMono = accountRepository.findByAccountId(accountIdValue);

			return accountMono.flatMap(acc -> {
				log.info("Found account with name: " + acc.getAccountName());
				return ServerResponse.ok().bodyValue(acc);
			}).switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue("Account not found"));
		} else {
			log.info("No query param accountId provided, fetching all accounts");
			var allAccounts = accountRepository.findAll();
			return ServerResponse.ok().body(allAccounts, AccountRepresentation.class);
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
		var accountId = serverRequest.pathVariable("accountId");

		return serverRequest.bodyToMono(AccountRepresentation.class).doOnNext(this::validateConstraints)

				.flatMap(acc -> {
					return accountRepository.findByAccountId(accountId).flatMap(account -> {
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

	@Override
	public Mono<ServerResponse> addOffersToAccount(ServerRequest serverRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<ServerResponse> publishOrderEvent(ServerRequest serverRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	public Mono<ServerResponse> subscribeToOrderEvents(ServerRequest serverRequest) {
	    return ServerResponse.ok()
	            .contentType(MediaType.APPLICATION_NDJSON)  
	            .body(orderService.getAccountInfoSink().asFlux().log(), AccountRepresentation.class);
	}



}
