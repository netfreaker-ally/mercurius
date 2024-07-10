package com.Mercurius.Bridge.service.serviceImpl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Mercurius.Bridge.constants.BridgeConstants;
import com.Mercurius.Bridge.dto.ResponseDto;
import com.Mercurius.Bridge.entity.AccountRepresentation;
import com.Mercurius.Bridge.entity.BundledProductRepresentation;
import com.Mercurius.Bridge.entity.EligibilityStatusRepresentation;
import com.Mercurius.Bridge.entity.OfferRepresentation;
import com.Mercurius.Bridge.entity.Order;
import com.Mercurius.Bridge.entity.OrderItem;
import com.Mercurius.Bridge.entity.OrderItem.PaymentType;
import com.Mercurius.Bridge.entity.OrderItem.PriceComponent;
import com.Mercurius.Bridge.entity.OrderItem.Status;
import com.Mercurius.Bridge.entity.ProductRepresentation;
import com.Mercurius.Bridge.exception.ResourceNotFoundException;
import com.Mercurius.Bridge.service.IBridgeService;
import com.Mercurius.Bridge.service.clients.AccountManagementClient;
import com.Mercurius.Bridge.service.clients.BundledProductsClient;
import com.Mercurius.Bridge.service.clients.EligibilityChecksClient;
import com.Mercurius.Bridge.service.clients.OrderManagementClient;
import com.Mercurius.Bridge.service.clients.ProductOfferingsClient;

@Service
public class BridgeServiceImpl implements IBridgeService {
	@Autowired
	AccountManagementClient accountManagementClient;
	@Autowired
	EligibilityChecksClient eligibilityChecksClient;
	@Autowired
	ProductOfferingsClient productOfferingsClient;
	@Autowired
	private BundledProductsClient bundledProductsClient;
	@Autowired
	private OrderManagementClient orderManagementClient;

	public BridgeServiceImpl(AccountManagementClient accountManagementClient,
			EligibilityChecksClient eligibilityChecksClient, ProductOfferingsClient productOfferingsClient,
			BundledProductsClient bundledProductsClient, OrderManagementClient orderManagementClient) {
		super();
		this.accountManagementClient = accountManagementClient;
		this.eligibilityChecksClient = eligibilityChecksClient;
		this.productOfferingsClient = productOfferingsClient;
		this.bundledProductsClient = bundledProductsClient;
		this.orderManagementClient = orderManagementClient;
	}

	@Override
	public ResponseEntity<ResponseDto> createBaseProduct(ProductRepresentation baseProduct) {
		
		
		
		ResponseEntity<ResponseDto> response = productOfferingsClient.createProduct(baseProduct);
		return response;
	}

	@Override
	public ResponseEntity<ResponseDto> updateBaseProduct(String productId, ProductRepresentation baseProduct) {
		ResponseEntity<ResponseDto> response = productOfferingsClient.updateProduct(baseProduct);
		return response;
	}

	@Override
	public ResponseEntity<ResponseDto> deleteBaseProduct(String productId) {
		ResponseEntity<ResponseDto> response = productOfferingsClient.deleteProduct(productId);
		return response;

	}

	@Override
	public ProductRepresentation getBaseProductById(String productId) {

		ResponseEntity<ProductRepresentation> message = productOfferingsClient.getProductById(productId);
		return message.getBody();

	}

	@Override
	public List<ProductRepresentation> listBaseProducts() {

		ResponseEntity<List<ProductRepresentation>> message = productOfferingsClient.getAllProducts();
		return message.getBody();
	}

	@Override
	public String createBundledProduct(BundledProductRepresentation bundledProduct) {
		ResponseEntity<String> response = bundledProductsClient.createBundledProduct(bundledProduct);
		if (response.getStatusCode().equals(HttpStatus.OK)) {
			return response.getBody();
		} else {
			throw new RuntimeException("Failed to create bundled product");
		}
	}

	@Override
	public String updateBundledProduct(String productId, BundledProductRepresentation bundledProduct) {
		ResponseEntity<String> response = bundledProductsClient.updateBundledProduct(productId, bundledProduct);
		if (response.getStatusCode().equals(HttpStatus.OK)) {
			return response.getBody();
		} else {
			throw new RuntimeException("Failed to update bundled product");
		}
	}

	@Override
	public String deleteBundledProduct(String productId) {
		ResponseEntity<Void> response = bundledProductsClient.deleteBundledProduct(productId);
		if (response.getStatusCode().equals(HttpStatus.OK)) {
			return BridgeConstants.MESSAGE_417_DELETE;
		} else {
			throw new RuntimeException("Failed to Delete bundled product");
		}
	}

	@Override
	public BundledProductRepresentation getBundledProductById(String productId) {
		ResponseEntity<BundledProductRepresentation> response = bundledProductsClient.getBundledProductById(productId);
	
		if (response.getStatusCode().equals(HttpStatus.OK)) {
			return response.getBody();
		} else {
			throw new RuntimeException("Failed to retrieve bundled product by ID");
		}
	}

	@Override
	public List<BundledProductRepresentation> listBundledProducts() {
		ResponseEntity<List<BundledProductRepresentation>> response = bundledProductsClient.listBundledProducts();
		if (response.getStatusCode().equals(HttpStatus.OK)) {
			return response.getBody();
		} else {
			throw new RuntimeException("Failed to list bundled products");
		}
	}

	@Override
	public ResponseEntity<ResponseDto> createUserProfile(AccountRepresentation userProfile) {
		// TODO Auto-generated method stub
		ResponseEntity<ResponseDto> response = accountManagementClient.createAccount(userProfile);
		return response;
	}

	@Override
	public ResponseEntity<ResponseDto> updateUserProfile(AccountRepresentation userProfile) {
		// TODO Auto-generated method stub
		ResponseEntity<ResponseDto> response = accountManagementClient.updateAccount(userProfile);
		return response;
	}

	@Override
	public ResponseEntity<ResponseDto> deleteUserProfile(String userId) {

		ResponseEntity<ResponseDto> response = accountManagementClient.deleteAccount(userId);
		return response;

	}

	@Override
	public AccountRepresentation getUserProfileById(String userId) {
		// TODO Auto-generated method stub
		ResponseEntity<AccountRepresentation> response = accountManagementClient.getAccountById(userId);
		return response.getBody();

	}

	@Override
	public List<AccountRepresentation> listUserProfiles() {

		ResponseEntity<List<AccountRepresentation>> message = accountManagementClient.getAllAccounts();
		return message.getBody();

	}

	@Override
	public EligibilityStatusRepresentation evaluateEligibility(String userId, String productId) {

		ResponseEntity<EligibilityStatusRepresentation> message = eligibilityChecksClient.evaluateEligibility(userId,
				productId);
		return message.getBody();
	}

	@Override
	public ResponseEntity<ResponseDto> createOfferForUser(String userId, OfferRepresentation offer) {
		ResponseEntity<ResponseDto> response = eligibilityChecksClient.createOfferForUser(userId, offer);
		return response;
	}

	@Override
	public Set<OfferRepresentation> getUserOffers(String userId) {
		ResponseEntity<Set<OfferRepresentation>> response = eligibilityChecksClient.getUserOffers(userId);
		return response.getBody();
	}

	@Override
	public List<ProductRepresentation> getEligibleProductsForUser(String userId) {
		ResponseEntity<List<ProductRepresentation>> response = eligibilityChecksClient
				.getEligibleProductsForUser(userId);
		return response.getBody();
	}

	@Override
	public ResponseEntity<ResponseDto> createOrder(Order order) {

		return orderManagementClient.createOrder(order);
	}

	@Override
	public ResponseEntity<ResponseDto> createOrderItem(OrderItem orderItem) {
		return orderManagementClient.createOrderItem(orderItem);
	}

	@Override
	public ResponseEntity<List<Order>> getAllOrders() {

		return orderManagementClient.getAllOrders();
	}

	@Override
	public ResponseEntity<List<OrderItem>> getCart(String accString) {

		return orderManagementClient.getCart(accString);
	}
	public OrderItem generateOrderItem(OrderItem orderItem) throws ResourceNotFoundException {
	    String accountId = orderItem.getAccountId();
	    System.out.println("accountID:\t"+accountId);
//	    Set<OfferRepresentation> offers = getUserOffers(accountId);  
AccountRepresentation accountRepresentation=accountManagementClient.getAccountById(accountId).getBody();
Set<OfferRepresentation> offers=accountRepresentation.getOffers();
	  
	    double maxDiscountPercentage = offers.stream()
	            .mapToDouble(OfferRepresentation::getDiscountPercentage)
	            .max()
	            .orElse(0.0);

	    ProductRepresentation productRepresentation = getBaseProductById(orderItem.getProductId());
	  
	   

	    if (productRepresentation.getStock() < orderItem.getQuantity()) {
	        throw new ResourceNotFoundException("Product with ID " , orderItem.getProductId() , " has insufficient stock. Available: " + productRepresentation.getStock() + ", Requested: " + orderItem.getQuantity());
	    }

	   
	    productRepresentation.setAvailable(orderItem.getQuantity() == productRepresentation.getStock());
	    productRepresentation.setStock(productRepresentation.getStock() - orderItem.getQuantity());
 
	    productOfferingsClient.updateProduct(productRepresentation);

	    PriceComponent priceComponent = new PriceComponent();
	    priceComponent.setSellingPrice(productRepresentation.getPrice());
	    priceComponent.setShippingCharge(50.0);
	    priceComponent.setmercuiusDiscount(maxDiscountPercentage);


	    double totalPrice = priceComponent.getSellingPrice() * (1 - maxDiscountPercentage / 100);
	    totalPrice = Math.round(totalPrice * 100) / 100.0;
	    totalPrice+=priceComponent.getShippingCharge();

	    priceComponent.setTotalPrice(totalPrice);
	    priceComponent.setCustomerPrice(totalPrice);

	    orderItem.setPriceComponents(priceComponent);

	    orderItem.setOrderDate(Date.valueOf(LocalDate.now()));
	    orderItem.setCancellationDate(null);
	    orderItem.setCancellationReason(null);
	    orderItem.setCancellationSubReason(null);
	    orderItem.setStatus(Status.APPROVED);
	    orderItem.setPaymentType(PaymentType.COD);
	    orderItem.setCourierReturn(false);
	    orderItem.setListingId(UUID.randomUUID().toString());
	    orderItem.setPackageIds(new ArrayList<String>());
	    orderItem.setReplacement(true);
	 
	    
	    return orderItem;
	}

}
