package com.Mercurius.Bridge.service;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.Mercurius.Bridge.dto.ResponseDto;
import com.Mercurius.Bridge.entity.AccountRepresentation;
import com.Mercurius.Bridge.entity.BundledProductRepresentation;
import com.Mercurius.Bridge.entity.EligibilityStatusRepresentation;
import com.Mercurius.Bridge.entity.OfferRepresentation;
import com.Mercurius.Bridge.entity.Order;
import com.Mercurius.Bridge.entity.OrderItem;
import com.Mercurius.Bridge.entity.ProductRepresentation;

import jakarta.validation.Valid;

public interface IBridgeService {
	// Base Product Management
	ResponseEntity<ResponseDto> createBaseProduct(ProductRepresentation baseProduct);

	ResponseEntity<ResponseDto> updateBaseProduct(String productId, ProductRepresentation baseProduct);

	ResponseEntity<ResponseDto> deleteBaseProduct(String productId);

	ProductRepresentation getBaseProductById(String productId);

	List<ProductRepresentation> listBaseProducts();

	// Bundled Product Management
	String createBundledProduct(BundledProductRepresentation bundledProduct);

	String updateBundledProduct(String productId, BundledProductRepresentation bundledProduct);

	String deleteBundledProduct(String productId);

	BundledProductRepresentation getBundledProductById(String productId);

	List<BundledProductRepresentation> listBundledProducts();

	// User Profile Management
	ResponseEntity<ResponseDto> createUserProfile(AccountRepresentation userProfile);

	ResponseEntity<ResponseDto> updateUserProfile(AccountRepresentation userProfile);

	ResponseEntity<ResponseDto> deleteUserProfile(String userId);

	AccountRepresentation getUserProfileById(String userId);

	List<AccountRepresentation> listUserProfiles();

	// Eligibility Checks
	EligibilityStatusRepresentation evaluateEligibility(String userId, String productId);

	// Combined Business Logic
	ResponseEntity<ResponseDto> createOfferForUser(String userId, OfferRepresentation offer);

	Set<OfferRepresentation> getUserOffers(String userId);

	List<ProductRepresentation> getEligibleProductsForUser(String userId);

	// Order Related Methods

	ResponseEntity<ResponseDto> createOrder( Order order);

	ResponseEntity<ResponseDto> createOrderItem(OrderItem orderItem);

	ResponseEntity<List<Order>> getAllOrders();

	ResponseEntity<List<OrderItem>> getCart( String accString);

}
