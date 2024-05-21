package com.Mercurius.Bridge.service;

import java.util.List;

import com.Mercurius.Bridge.entity.AccountRepresentation;
import com.Mercurius.Bridge.entity.BundledProductRepresentation;
import com.Mercurius.Bridge.entity.EligibilityStatusRepresentation;
import com.Mercurius.Bridge.entity.OfferRepresentation;
import com.Mercurius.Bridge.entity.ProductRepresentation;

public interface IBridgeService {
		//Base Product Management
	 	String createBaseProduct(ProductRepresentation baseProduct) throws Exception;
	    String updateBaseProduct(String productId, ProductRepresentation baseProduct);
	    String deleteBaseProduct(String productId);
	    ProductRepresentation getBaseProductById(String productId);
	    List<ProductRepresentation> listBaseProducts();

	    // Bundled Product Management
	    String createBundledProduct(BundledProductRepresentation bundledProduct);
	    String updateBundledProduct(String productId, BundledProductRepresentation bundledProduct);
	    String deleteBundledProduct(String productId);
	    BundledProductRepresentation getBundledProductById(String productId);
	    List<BundledProductRepresentation> listBundledProducts();

	    // User Profile Management
	    String createUserProfile(AccountRepresentation userProfile) throws Exception;
	    String updateUserProfile(String userId, AccountRepresentation userProfile);
	    String deleteUserProfile(String userId);
	    AccountRepresentation getUserProfileById(String userId);
	    List<AccountRepresentation> listUserProfiles();

	    // Eligibility Checks
	    EligibilityStatusRepresentation evaluateEligibility(String userId, String productId);
	   
	    String updateUserEligibility(String userId, EligibilityStatusRepresentation eligibilityUpdate);

	    // Combined Business Logic
	    String createOfferForUser(String userId, OfferRepresentation offer);
	    List<OfferRepresentation> getUserOffers(String userId);
	    EligibilityStatusRepresentation checkAndApplyEligibility(String userId, String productId);

}
