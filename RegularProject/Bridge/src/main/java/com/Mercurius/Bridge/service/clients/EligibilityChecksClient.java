package com.Mercurius.Bridge.service.clients;

import java.util.List;
import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.Mercurius.Bridge.dto.ResponseDto;
import com.Mercurius.Bridge.entity.EligibilityStatusRepresentation;
import com.Mercurius.Bridge.entity.OfferRepresentation;
import com.Mercurius.Bridge.entity.ProductRepresentation;

@FeignClient(name = "ELIGIBILITY-SERVICE")
public interface EligibilityChecksClient {
	@PostMapping("/api/evaluate")
	ResponseEntity<EligibilityStatusRepresentation> evaluateEligibility(@RequestParam("userId") String userId,
			@RequestParam("productId") String productId);

	@GetMapping("/api/{userId}/products")
	ResponseEntity<List<ProductRepresentation>> getEligibleProductsForUser(@PathVariable("userId") String userId);

	
	@PostMapping("/api/offers/{userId}")
	ResponseEntity<ResponseDto> createOfferForUser(@PathVariable("userId") String userId,
			@RequestBody OfferRepresentation offer);

	@GetMapping("/api/offers/{userId}")
	ResponseEntity<Set<OfferRepresentation>> getUserOffers(@PathVariable("userId") String userId);

	
}
	