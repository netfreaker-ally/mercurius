package com.Mercurius.Bridge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Mercurius.Bridge.entity.EligibilityStatusRepresentation;
import com.Mercurius.Bridge.entity.OfferRepresentation;
import com.Mercurius.Bridge.service.IBridgeService;

@RestController
@RequestMapping("/bridge/api/eligibility")
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

	@PutMapping("/update/{userId}")
	public ResponseEntity<String> updateUserEligibility(@PathVariable String userId,
			@RequestBody EligibilityStatusRepresentation eligibilityUpdate) {
		return ResponseEntity.ok(bridgeService.updateUserEligibility(userId, eligibilityUpdate));
	}

	@PostMapping("/offer/{userId}")
	public ResponseEntity<String> createOfferForUser(@PathVariable String userId,
			@RequestBody OfferRepresentation offer) {
		return ResponseEntity.ok(bridgeService.createOfferForUser(userId, offer));
	}

	@GetMapping("/offers/{userId}")
	public ResponseEntity<List<OfferRepresentation>> getUserOffers(@PathVariable String userId) {
		return ResponseEntity.ok(bridgeService.getUserOffers(userId));
	}

	@GetMapping("/check-apply")
	public ResponseEntity<EligibilityStatusRepresentation> checkAndApplyEligibility(@RequestParam String userId,
			@RequestParam String productId) {
		return ResponseEntity.ok(bridgeService.checkAndApplyEligibility(userId, productId));
	}
}
