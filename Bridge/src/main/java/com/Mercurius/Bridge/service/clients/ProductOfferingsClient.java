package com.Mercurius.Bridge.service.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.Mercurius.Bridge.entity.ProductRepresentation;

@FeignClient(name = "product-service")
public interface ProductOfferingsClient {

    @GetMapping("/products")
    ResponseEntity<List<ProductRepresentation>>  getAllProducts();

    @GetMapping("/products/{id}")
    ResponseEntity<ProductRepresentation>  getProductById(@PathVariable("id") String productId);

    @PostMapping("/products")
    ResponseEntity<String> createProduct(@RequestBody ProductRepresentation product) ;

    @PutMapping("/products/{id}")
    ResponseEntity<ProductRepresentation> updateProduct(@PathVariable("id") String productId, @RequestBody ProductRepresentation product);

    @DeleteMapping("/products/{id}")
    ResponseEntity<String> deleteProduct(@PathVariable("id") String productId);
}
