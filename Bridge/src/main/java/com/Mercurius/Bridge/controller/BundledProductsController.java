package com.Mercurius.Bridge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Mercurius.Bridge.entity.BundledProductRepresentation;
import com.Mercurius.Bridge.service.IBridgeService;

@RestController
@RequestMapping("/bridge/api/bundled-products")
public class BundledProductsController {

    @Autowired
    private IBridgeService bridgeService;
    

	public BundledProductsController(IBridgeService bridgeService) {
		super();
		this.bridgeService = bridgeService;
	}

	@PostMapping
    public ResponseEntity<String> createBundledProduct(@RequestBody BundledProductRepresentation bundledProduct) {
        return ResponseEntity.ok(bridgeService.createBundledProduct(bundledProduct));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<String> updateBundledProduct(@PathVariable String productId, @RequestBody BundledProductRepresentation bundledProduct) {
        return ResponseEntity.ok(bridgeService.updateBundledProduct(productId, bundledProduct));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteBundledProduct(@PathVariable String productId) {
        return ResponseEntity.ok(bridgeService.deleteBundledProduct(productId));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<BundledProductRepresentation> getBundledProductById(@PathVariable String productId) {
        return ResponseEntity.ok(bridgeService.getBundledProductById(productId));
    }

    @GetMapping
    public ResponseEntity<List<BundledProductRepresentation>> listBundledProducts() {
        return ResponseEntity.ok(bridgeService.listBundledProducts());
    }
}
