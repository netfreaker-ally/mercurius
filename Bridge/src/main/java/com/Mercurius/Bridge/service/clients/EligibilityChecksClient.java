package com.Mercurius.Bridge.service.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Mercurius.Bridge.entity.EligibilityStatusRepresentation;
import com.Mercurius.Bridge.entity.ProductRepresentation;

@FeignClient(name = "eligibility-service")
public interface EligibilityChecksClient {
	@PostMapping("/eligibility/evaluate")
	ResponseEntity<EligibilityStatusRepresentation>  evaluateEligibility(@RequestParam("userId") String userId, @RequestParam("productId") String productId);

    @GetMapping("/eligibility/{userId}/products")
    ResponseEntity< List<ProductRepresentation>> getEligibleProductsForUser(@PathVariable("userId") String userId);

}
