package com.Mercurious.eligibilityservice.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.Mercurious.eligibilityservice.dto.ResponseDto;
import com.Mercurious.eligibilityservice.entity.ProductRepresentation;

import jakarta.validation.Valid;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductsClient {
	@PostMapping("/api/products/create")
	public ResponseEntity<ResponseDto> createProduct(@Valid@RequestBody ProductRepresentation product) ;

}
