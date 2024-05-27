package com.Mercurious.productservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Mercurious.productservice.constants.ProductConstants;
import com.Mercurious.productservice.dto.ResponseDto;
import com.Mercurious.productservice.entity.ProductRepresentation;
import com.Mercurious.productservice.service.IProductManagementService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	@Autowired
	private IProductManagementService iProductManagementService;

	public ProductController(IProductManagementService iProductManagementService) {
		super();
		this.iProductManagementService = iProductManagementService;
	}
	
	public ProductController() {
		super();
		// TODO Auto-generated constructor stub
	}

	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createProduct(@Valid@RequestBody ProductRepresentation product) {
		iProductManagementService.createProduct(product);
		return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(ProductConstants.STATUS_201, ProductConstants.MESSAGE_201));
	
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductRepresentation> getProductById(@PathVariable("id") String productId) {
		return ResponseEntity.status(HttpStatus.OK).body(iProductManagementService.getProductById(productId));
	
	}

	@PutMapping
	public ResponseEntity<ResponseDto> updateProduct(
			@Valid@RequestBody ProductRepresentation product) {
		boolean isUpdated= iProductManagementService.updateProduct(product);
		  if(isUpdated) {
	            return ResponseEntity
	                    .status(HttpStatus.OK)
	                    .body(new ResponseDto(ProductConstants.STATUS_200, ProductConstants.MESSAGE_200));
	        }else{
	            return ResponseEntity
	                    .status(HttpStatus.EXPECTATION_FAILED)
	                    .body(new ResponseDto(ProductConstants.STATUS_417, ProductConstants.MESSAGE_417_UPDATE));
	        }
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseDto> deleteProduct(@PathVariable("id") String productId) {
		boolean isDeleted=iProductManagementService.deleteProduct(productId);
		 if(isDeleted) {
	            return ResponseEntity
	                    .status(HttpStatus.OK)
	                    .body(new ResponseDto(ProductConstants.STATUS_200, ProductConstants.MESSAGE_200));
	        }else{
	            return ResponseEntity
	                    .status(HttpStatus.EXPECTATION_FAILED)
	                    .body(new ResponseDto(ProductConstants.STATUS_417, ProductConstants.MESSAGE_417_DELETE));
	        }
	}

	@GetMapping("/all")
	public ResponseEntity<List<ProductRepresentation>> getAllProducts() {
		return ResponseEntity.ok(iProductManagementService.getAllProducts());
	}

	

}
