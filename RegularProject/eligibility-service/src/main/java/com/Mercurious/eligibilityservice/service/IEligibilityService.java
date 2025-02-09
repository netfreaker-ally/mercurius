package com.Mercurious.eligibilityservice.service;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.Mercurious.eligibilityservice.dto.ResponseDto;
import com.Mercurious.eligibilityservice.entity.AccountRepresentation;
import com.Mercurious.eligibilityservice.entity.EligibilityStatusRepresentation;
import com.Mercurious.eligibilityservice.entity.OfferRepresentation;
import com.Mercurious.eligibilityservice.entity.ProductRepresentation;

public interface IEligibilityService {
	EligibilityStatusRepresentation evaluateEligibility(String accountId, String productId);

	List<ProductRepresentation> getEligibleProductsForUser(String accountId);

	

	boolean createOfferForUser(String accountId, OfferRepresentation offer);

	Set<OfferRepresentation> getUserOffers(String accountId);

	ResponseEntity<ResponseDto>  createAccount(AccountRepresentation account);
	ResponseEntity<ResponseDto> createProduct(@RequestBody ProductRepresentation product);
}
