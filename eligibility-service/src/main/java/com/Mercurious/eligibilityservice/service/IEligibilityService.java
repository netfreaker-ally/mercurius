package com.Mercurious.eligibilityservice.service;

import java.util.List;

import com.Mercurious.eligibilityservice.entity.EligibilityStatusRepresentation;
import com.Mercurious.eligibilityservice.entity.OfferRepresentation;
import com.Mercurious.eligibilityservice.entity.ProductRepresentation;

public interface IEligibilityService {
	EligibilityStatusRepresentation evaluateEligibility(String accountId, String productId);

	List<ProductRepresentation> getEligibleProductsForUser(String accountId);

	

	boolean createOfferForUser(String accountId, OfferRepresentation offer);

	List<OfferRepresentation> getUserOffers(String accountId);



}
