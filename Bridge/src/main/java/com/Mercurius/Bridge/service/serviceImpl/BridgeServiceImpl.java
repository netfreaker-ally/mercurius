package com.Mercurius.Bridge.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.Mercurius.Bridge.service.clients.BundledProductsClient;
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
	@Autowired
	private BundledProductsClient bundledProductsClient;

	public BridgeServiceImpl(AccountManagementClient accountManagementClient,
			EligibilityChecksClient eligibilityChecksClient, ProductOfferingsClient productOfferingsClient,
			BundledProductsClient bundledProductsClient) {
		super();
		this.accountManagementClient = accountManagementClient;
		this.eligibilityChecksClient = eligibilityChecksClient;
		this.productOfferingsClient = productOfferingsClient;
		this.bundledProductsClient = bundledProductsClient;
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
	public List<ProductRepresentation> listBaseProducts() {

		try {
			ResponseEntity<List<ProductRepresentation>> message = productOfferingsClient.getAllProducts();
			if (message.getStatusCode().equals(HttpStatus.OK)) {
				return message.getBody();
			} else {
				throw new Exception(BridgeConstants.MESSAGE_500);
			}

		} catch (Exception ex) {
			throw new RuntimeException("Failed to Get Products:\n", ex);
		}
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
	public String createUserProfile(AccountRepresentation userProfile) throws Exception {
		// TODO Auto-generated method stub
		try {
			ResponseEntity<AccountRepresentation> message = accountManagementClient.createAccount(userProfile);
			if (message.getStatusCode().equals(BridgeConstants.STATUS_200)) {
				return BridgeConstants.MESSAGE_200;
			} else {

				throw new Exception(BridgeConstants.MESSAGE_500);
			}
		} catch (FeignException ex) {

			throw new RuntimeException("Failed to create Account", ex);
		}
	}

	@Override
	public String updateUserProfile(String userId, AccountRepresentation userProfile) {
		// TODO Auto-generated method stub
		try {
			ResponseEntity<AccountRepresentation> message = accountManagementClient.updateAccount(userId, userProfile);
			if (message.getStatusCode().equals(BridgeConstants.STATUS_200)) {
				return BridgeConstants.MESSAGE_200;
			} else {
				throw new Exception(BridgeConstants.MESSAGE_500);
			}
		} catch (FeignException.NotFound ex) {
			throw new ResourceNotFoundException("User ", userId, " not found");
		} catch (Exception ex) {
			throw new RuntimeException("Failed to update Account", ex);
		}
	}

	@Override
	public String deleteUserProfile(String userId) {

		try {
			ResponseEntity<String> message = accountManagementClient.deleteAccount(userId);
			if (message.getStatusCode().equals(BridgeConstants.STATUS_200)) {
				return BridgeConstants.MESSAGE_200;
			} else {
				throw new Exception(BridgeConstants.MESSAGE_500);
			}
		} catch (FeignException.NotFound ex) {
			throw new ResourceNotFoundException("User ", userId, " not found");
		} catch (Exception ex) {
			throw new RuntimeException("Failed to Delete Account", ex);
		}
	}

	@Override
	public AccountRepresentation getUserProfileById(String userId) {
		// TODO Auto-generated method stub
		try {
			ResponseEntity<AccountRepresentation> message = accountManagementClient.getAccountById(userId);
			if (message.getStatusCode().equals(BridgeConstants.STATUS_200)) {
				return message.getBody();
			} else {
				throw new Exception(BridgeConstants.MESSAGE_500);
			}
		} catch (FeignException.NotFound ex) {
			throw new ResourceNotFoundException("User ", userId, " not found");
		} catch (Exception ex) {
			throw new RuntimeException("Failed to Get User:\n", ex);
		}
	}

	@Override
	public List<AccountRepresentation> listUserProfiles() {
		try {
			ResponseEntity<List<AccountRepresentation>> message = accountManagementClient.getAllAccounts();
			if (message.getStatusCode().equals(HttpStatus.OK)) {
				return message.getBody();
			} else {
				throw new Exception(BridgeConstants.MESSAGE_500);
			}

		} catch (Exception ex) {
			throw new RuntimeException("Failed to Get Users:\n", ex);
		}

	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public EligibilityStatusRepresentation evaluateEligibility(String userId, String productId) {
		try {
			ResponseEntity<EligibilityStatusRepresentation> message = eligibilityChecksClient
					.evaluateEligibility(userId, productId);
			if (message.getStatusCode().equals(BridgeConstants.STATUS_200)) {
				return message.getBody();
			} else {
				throw new Exception(BridgeConstants.MESSAGE_500);
			}
		} catch (FeignException.NotFound ex) {
			throw new ResourceNotFoundException("User ", userId, " not found");
		} catch (Exception ex) {
			throw new RuntimeException("Failed to Get User or Product:\n", ex);
		}
	}

	@Override
	public String updateUserEligibility(String userId, EligibilityStatusRepresentation eligibilityUpdate) {
		try {
			ResponseEntity<EligibilityStatusRepresentation> message = eligibilityChecksClient.updateEligibility(userId,
					eligibilityUpdate);
			if (message.getStatusCode().equals(BridgeConstants.STATUS_200)) {
				return BridgeConstants.MESSAGE_200;
			} else {
				throw new Exception(BridgeConstants.MESSAGE_500);
			}
		} catch (FeignException.NotFound ex) {
			throw new ResourceNotFoundException("User ", userId, " not found");
		} catch (Exception ex) {
			throw new RuntimeException("Failed to Get User ", ex);
		}
	}

	@Override
	public String createOfferForUser(String userId, OfferRepresentation offer) {
		ResponseEntity<String> response = eligibilityChecksClient.createOfferForUser(userId, offer);
		if (response.getStatusCode().equals(HttpStatus.OK)) {
			return response.getBody();
		} else {
			throw new RuntimeException("Failed to create offer for user");
		}
	}

	@Override
	public List<OfferRepresentation> getUserOffers(String userId) {
		ResponseEntity<List<OfferRepresentation>> response = eligibilityChecksClient.getUserOffers(userId);
		if (response.getStatusCode().equals(HttpStatus.OK)) {
			return response.getBody();
		} else {
			throw new RuntimeException("Failed to get offers for user");
		}
	}

	@Override
	public EligibilityStatusRepresentation checkAndApplyEligibility(String userId, String productId) {
		try {
			ResponseEntity<EligibilityStatusRepresentation> response = eligibilityChecksClient
					.checkAndApplyEligibility(userId, productId);
			if (response.getStatusCode().equals(HttpStatus.OK)) {
				return response.getBody();
			} else {
				throw new RuntimeException("Failed to check and apply eligibility");
			}
		} catch (FeignException.NotFound ex) {
			throw new ResourceNotFoundException("User", userId, " not found");
		} catch (Exception ex) {
			throw new RuntimeException("Failed to get eligibility status", ex);
		}
	}

}
