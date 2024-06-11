package com.mercurius.order.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mercurius.order.dto.ResponseDto;
import com.mercurius.order.entity.ProductRepresentation;

import jakarta.validation.Valid;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductOfferingsClient {

	@GetMapping("/api/all-products")
	public ResponseEntity<List<ProductRepresentation>> getAllProducts();

	@GetMapping("/api/{id}")
	public ResponseEntity<ProductRepresentation> getProductById(@PathVariable("id") String productId);
	/*
	 * @PostMapping("/api/create") public ResponseEntity<ResponseDto>
	 * createProduct(@Valid@RequestBody ProductRepresentation product);
	 * 
	 * @PutMapping("/api/products") public ResponseEntity<ResponseDto>
	 * updateProduct(
	 * 
	 * @Valid@RequestBody ProductRepresentation product);
	 * 
	 * @DeleteMapping("/api/{id}") public ResponseEntity<ResponseDto>
	 * deleteProduct(@PathVariable("id") String productId);
	 */
}
