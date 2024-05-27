package com.Mercurious.eligibilityservice.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Mercurious.eligibilityservice.constants.EligibilityConstants;
import com.Mercurious.eligibilityservice.dto.ResponseDto;
import com.Mercurious.eligibilityservice.entity.AccountRepresentation;
import com.Mercurious.eligibilityservice.entity.EligibilityStatusRepresentation;
import com.Mercurious.eligibilityservice.entity.OfferRepresentation;
import com.Mercurious.eligibilityservice.entity.ProductRepresentation;
import com.Mercurious.eligibilityservice.service.IEligibilityService;

import jakarta.validation.Valid;

@RequestMapping("/api/eligibility")
@RestController
public class EligibilityServiceController {
	@Autowired
	private IEligibilityService eligibilityService;

	public EligibilityServiceController(IEligibilityService eligibilityService) {
		super();
		this.eligibilityService = eligibilityService;
	}

	@PostMapping("/create-account")
	public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody AccountRepresentation account) {
		eligibilityService.createAccount(account);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(EligibilityConstants.STATUS_201, EligibilityConstants.MESSAGE_201));

	}

	@PostMapping("/create-product")
	public ResponseEntity<ResponseDto> createProduct(@Valid @RequestBody ProductRepresentation product) {
		eligibilityService.createProduct(product);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(EligibilityConstants.STATUS_201, EligibilityConstants.MESSAGE_201));

	}

	@PostMapping("/evaluate")
	ResponseEntity<EligibilityStatusRepresentation> evaluateEligibility(@RequestParam("userId") String userId,
			@RequestParam("productId") String productId) {
		EligibilityStatusRepresentation eligibilityStatusRepresentation = eligibilityService.evaluateEligibility(userId,
				productId);
		return ResponseEntity.ok(eligibilityStatusRepresentation);

	}

	@GetMapping("/{userId}/products")
	ResponseEntity<List<ProductRepresentation>> getEligibleProductsForUser(@PathVariable("userId") String userId) {
		List<ProductRepresentation> productRepresentations = eligibilityService.getEligibleProductsForUser(userId);
		return ResponseEntity.ok(productRepresentations);

	}

	@PostMapping("/offers/{userId}")
	ResponseEntity<ResponseDto> createOfferForUser(@PathVariable("userId") String userId,
			@RequestBody OfferRepresentation offer) {
		boolean isOfferCreated = eligibilityService.createOfferForUser(userId, offer);
		if (isOfferCreated) {
			return ResponseEntity.ok(new ResponseDto(EligibilityConstants.MESSAGE_200, userId));
		} else {
			return ResponseEntity.ok(new ResponseDto(EligibilityConstants.MESSAGE_417_UPDATE, userId));

		}

	}

	@GetMapping("/offers/{userId}")
	ResponseEntity<Set<OfferRepresentation>> getUserOffers(@PathVariable("userId") String userId) {
		Set<OfferRepresentation> offers = eligibilityService.getUserOffers(userId);
		return ResponseEntity.ok(offers);

	}

}
