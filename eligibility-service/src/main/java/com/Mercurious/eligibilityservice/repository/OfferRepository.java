package com.Mercurious.eligibilityservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Mercurious.eligibilityservice.entity.OfferRepresentation;
@Repository
public interface OfferRepository extends JpaRepository<OfferRepresentation, Long>{
	Optional<OfferRepresentation> findByOfferId(String offerId);
}
