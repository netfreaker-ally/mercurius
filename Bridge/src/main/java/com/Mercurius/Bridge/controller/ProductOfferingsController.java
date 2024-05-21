package com.Mercurius.Bridge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Mercurius.Bridge.entity.ProductRepresentation;
import com.Mercurius.Bridge.service.IBridgeService;

@RestController
@RequestMapping("/bridge/api/products")
public class ProductOfferingsController {

	@Autowired
	private IBridgeService bridgeService;

	public ProductOfferingsController(IBridgeService bridgeService) {
		super();
		this.bridgeService = bridgeService;
	}

	@PostMapping
	public ResponseEntity<String> createBaseProduct(@RequestBody ProductRepresentation baseProduct) throws Exception {
		return ResponseEntity.ok(bridgeService.createBaseProduct(baseProduct));
	}

	@PutMapping("/{productId}")
	public ResponseEntity<String> updateBaseProduct(@PathVariable String productId,
			@RequestBody ProductRepresentation baseProduct) throws Exception {
		return ResponseEntity.ok(bridgeService.updateBaseProduct(productId, baseProduct));
	}

	@DeleteMapping("/{productId}")
	public ResponseEntity<String> deleteBaseProduct(@PathVariable String productId) {
		return ResponseEntity.ok(bridgeService.deleteBaseProduct(productId));
	}

	@GetMapping("/{productId}")
	public ResponseEntity<ProductRepresentation> getBaseProductById(@PathVariable String productId) {
		return ResponseEntity.ok(bridgeService.getBaseProductById(productId));
	}

	@GetMapping
	public ResponseEntity<List<ProductRepresentation>> listBaseProducts() {
		return ResponseEntity.ok(bridgeService.listBaseProducts());
	}
}
