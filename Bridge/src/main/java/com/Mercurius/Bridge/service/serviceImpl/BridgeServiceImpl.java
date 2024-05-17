package com.Mercurius.Bridge.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.Mercurius.Bridge.constants.BridgeConstants;
import com.Mercurius.Bridge.entity.AccountRepresentation;
import com.Mercurius.Bridge.entity.BundledProductRepresentation;
import com.Mercurius.Bridge.entity.EligibilityStatusRepresentation;
import com.Mercurius.Bridge.entity.OfferRepresentation;
import com.Mercurius.Bridge.entity.ProductRepresentation;
import com.Mercurius.Bridge.exception.ResourceNotFoundException;
import com.Mercurius.Bridge.service.IBridgeService;
import com.Mercurius.Bridge.service.clients.AccountManagementClient;
import com.Mercurius.Bridge.service.clients.EligibilityChecksClient;
import com.Mercurius.Bridge.service.clients.ProductOfferingsClient;

import feign.FeignException;

public class BridgeServiceImpl implements IBridgeService {
	@Autowired
	AccountManagementClient accountManagementClient;
	@Autowired
	EligibilityChecksClient eligibilityChecksClient;
	@Autowired
	ProductOfferingsClient productOfferingsClient;

	public BridgeServiceImpl(AccountManagementClient accountManagementClient,
			EligibilityChecksClient eligibilityChecksClient, ProductOfferingsClient productOfferingsClient) {
		super();
		this.accountManagementClient = accountManagementClient;
		this.eligibilityChecksClient = eligibilityChecksClient;
		this.productOfferingsClient = productOfferingsClient;
	}

	@Override
	public String createBaseProduct(ProductRepresentation baseProduct) throws Exception {
		try {
			ResponseEntity<String> message = productOfferingsClient.createProduct(baseProduct);
			if (message.getStatusCode().equals(BridgeConstants.STATUS_200)) {
				return message.getBody();
			} else {

				throw new Exception(BridgeConstants.MESSAGE_500);
			}
		} catch (FeignException ex) {

			throw new RuntimeException("Failed to create base product", ex);
		}
	}

	@Override
	public String updateBaseProduct(String productId, ProductRepresentation baseProduct) {
		try {
			ResponseEntity<ProductRepresentation> message = productOfferingsClient.updateProduct(productId,
					baseProduct);
			if (message.getStatusCode().equals(BridgeConstants.STATUS_200)) {
				return BridgeConstants.MESSAGE_200;
			} else {
				throw new Exception(BridgeConstants.MESSAGE_500);
			}
		} catch (FeignException.NotFound ex) {
			throw new ResourceNotFoundException("Product ", productId, " not found");
		} catch (Exception ex) {
			throw new RuntimeException("Failed to update base product", ex);
		}
	}

	@Override
	public String deleteBaseProduct(String productId) {
		try {
			ResponseEntity<String> message = productOfferingsClient.deleteProduct(productId);
			if (message.getStatusCode().equals(BridgeConstants.STATUS_200)) {
				return BridgeConstants.MESSAGE_200;
			} else {
				throw new Exception(BridgeConstants.MESSAGE_500);
			}
		} catch (FeignException.NotFound ex) {
			throw new ResourceNotFoundException("Product ", productId, " not found");
		} catch (Exception ex) {
			throw new RuntimeException("Failed to Delete base product", ex);
		}

	}

	@Override
	public ProductRepresentation getBaseProductById(String productId) {
		try {
			ResponseEntity<ProductRepresentation> message = productOfferingsClient.getProductById(productId);
			if (message.getStatusCode().equals(BridgeConstants.STATUS_200)) {
				return message.getBody();
			} else {
				throw new Exception(BridgeConstants.MESSAGE_500);
			}
		} catch (FeignException.NotFound ex) {
			throw new ResourceNotFoundException("Product ", productId, " not found");
		} catch (Exception ex) {
			throw new RuntimeException("Failed to Delete base product", ex);
		}

	}

	@Override
	public List<ProductRepresentation> listBaseProducts(int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createBundledProduct(BundledProductRepresentation bundledProduct) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateBundledProduct(String productId, BundledProductRepresentation bundledProduct) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteBundledProduct(String productId) {
		// TODO Auto-generated method stub

	}

	@Override
	public BundledProductRepresentation getBundledProductById(String productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BundledProductRepresentation> listBundledProducts(int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createUserProfile(AccountRepresentation userProfile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateUserProfile(String userId, AccountRepresentation userProfile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUserProfile(String userId) {
		// TODO Auto-generated method stub

	}

	@Override
	public AccountRepresentation getUserProfileById(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AccountRepresentation> listUserProfiles(int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EligibilityStatusRepresentation evaluateEligibility(String userId, String productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EligibilityStatusRepresentation getEligibilityStatus(String userId, String productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateUserEligibility(String userId, EligibilityStatusRepresentation eligibilityUpdate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createOfferForUser(String userId, OfferRepresentation offer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OfferRepresentation> getUserOffers(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EligibilityStatusRepresentation checkAndApplyEligibility(String userId, String productId) {
		// TODO Auto-generated method stub
		return null;
	}

}
