package com.Mercurius.Bridge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Mercurius.Bridge.constants.BridgeConstants;
import com.Mercurius.Bridge.dto.ResponseDto;
import com.Mercurius.Bridge.entity.ProductRepresentation;
import com.Mercurius.Bridge.service.IBridgeService;

@RestController
@RequestMapping("/api/products")
public class ProductOfferingsController {

	@Autowired
	private IBridgeService bridgeService;

	public ProductOfferingsController(IBridgeService bridgeService) {
		super();
		this.bridgeService = bridgeService;
	}

	@PostMapping
	public ResponseEntity<ResponseDto> createBaseProduct(@RequestBody ProductRepresentation baseProduct){
		bridgeService.createBaseProduct(baseProduct);
		return  ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(BridgeConstants.STATUS_201, BridgeConstants.MESSAGE_201));
	}

	@PutMapping("/{productId}")
	public ResponseEntity<ResponseDto> updateBaseProduct(@PathVariable String productId,
			@RequestBody ProductRepresentation baseProduct)  {
		bridgeService.updateBaseProduct(productId, baseProduct);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseDto(BridgeConstants.STATUS_200, BridgeConstants.MESSAGE_200));
	}

	@DeleteMapping("/{productId}")
	public ResponseEntity<ResponseDto> deleteBaseProduct(@PathVariable String productId) {
		bridgeService.deleteBaseProduct(productId);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseDto(BridgeConstants.STATUS_200, BridgeConstants.MESSAGE_200));
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
