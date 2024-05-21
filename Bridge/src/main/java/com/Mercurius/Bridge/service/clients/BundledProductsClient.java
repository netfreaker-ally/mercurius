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


import com.Mercurius.Bridge.entity.BundledProductRepresentation;

@FeignClient(name = "bundled-product-service")
public interface BundledProductsClient {

    @PostMapping("/bundled-products")
    ResponseEntity<String> createBundledProduct(@RequestBody BundledProductRepresentation bundledProduct);

    @PutMapping("/bundled-products/{productId}")
    ResponseEntity<String> updateBundledProduct(@PathVariable("productId") String productId, @RequestBody BundledProductRepresentation bundledProduct);

    @DeleteMapping("/bundled-products/{productId}")
    ResponseEntity<Void> deleteBundledProduct(@PathVariable("productId") String productId);

    @GetMapping("/bundled-products/{productId}")
    ResponseEntity<BundledProductRepresentation> getBundledProductById(@PathVariable("productId") String productId);

    @GetMapping("/bundled-products")
    ResponseEntity<List<BundledProductRepresentation>> listBundledProducts();
}