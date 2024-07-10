package com.Mercurius.Bridge.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Mercurius.Bridge.dto.ResponseDto;
import com.Mercurius.Bridge.entity.EligibilityStatusRepresentation;
import com.Mercurius.Bridge.entity.OfferRepresentation;
import com.Mercurius.Bridge.entity.ProductRepresentation;
import com.Mercurius.Bridge.service.IBridgeService;

@RestController
@RequestMapping("/api/eligibility")
public class EligibilityChecksController {

	@Autowired
	private IBridgeService bridgeService;

	public EligibilityChecksController(IBridgeService bridgeService) {
		super();
		this.bridgeService = bridgeService;
	}

	@GetMapping("/evaluate")
	public ResponseEntity<EligibilityStatusRepresentation> evaluateEligibility(@RequestParam String userId,
			@RequestParam String productId) {
		return ResponseEntity.ok(bridgeService.evaluateEligibility(userId, productId));
	}
	@GetMapping("/{userId}/products")
	ResponseEntity<List<ProductRepresentation>> getEligibleProductsForUser(@PathVariable("userId") String userId) {
		List<ProductRepresentation> productRepresentations =bridgeService.getEligibleProductsForUser(userId);
		return ResponseEntity.ok(productRepresentations);

	}
	

	@PostMapping("/offer/{userId}")
	public ResponseEntity<ResponseDto> createOfferForUser(@PathVariable String userId,
			@RequestBody OfferRepresentation offer) {
		return ResponseEntity.ok(bridgeService.createOfferForUser(userId, offer).getBody());
	}

	@GetMapping("/offers/{userId}")
	public ResponseEntity<Set<OfferRepresentation>> getUserOffers(@PathVariable String userId) {
		return ResponseEntity.ok(bridgeService.getUserOffers(userId));
	}

	
}
