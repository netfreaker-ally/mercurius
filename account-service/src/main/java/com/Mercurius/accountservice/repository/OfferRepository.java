package com.Mercurius.accountservice.repository;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.Mercurius.accountservice.entity.OfferRepresentation;

import reactor.core.publisher.Mono;

public interface OfferRepository extends ReactiveMongoRepository<OfferRepresentation, Long> {
	Mono<OfferRepresentation> findByOfferId(String offerId);
}