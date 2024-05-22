package com.Mercurious.eligibilityservice.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Mercurious.eligibilityservice.constants.EligibilityConstants;
import com.Mercurious.eligibilityservice.entity.AccountRepresentation;
import com.Mercurious.eligibilityservice.entity.EligibilityStatusRepresentation;
import com.Mercurious.eligibilityservice.entity.OfferRepresentation;
import com.Mercurious.eligibilityservice.entity.ProductRepresentation;
import com.Mercurious.eligibilityservice.exception.ResourceNotFoundException;
import com.Mercurious.eligibilityservice.repository.AccountsRepository;
import com.Mercurious.eligibilityservice.repository.ProductRepository;
import com.Mercurious.eligibilityservice.service.IEligibilityService;

@Service
public class EligibilityServiceImpl implements IEligibilityService {
	@Autowired
	private AccountsRepository accountsRepository;

	@Autowired
	private ProductRepository productRepository;

	@Override
	public EligibilityStatusRepresentation evaluateEligibility(String accountId, String productId) {
		try {
			AccountRepresentation user = accountsRepository.findByAccountId(accountId)
					.orElseThrow(() -> new ResourceNotFoundException("User not found", accountId, ""));
			ProductRepresentation product = productRepository.findByProductId(productId)
					.orElseThrow(() -> new ResourceNotFoundException("User not found", productId, ""));

			if (user.getAge() < product.getMinAge()) {
				return new EligibilityStatusRepresentation(false, "User does not meet the minimum age criteria");
			}

			if (user.getIncome() < product.getPrice()) {
				return new EligibilityStatusRepresentation(false, "User does not meet the income criteria");
			}

			if (product.isMembershipRequired() && !user.isMembershipLevel()) {
				return new EligibilityStatusRepresentation(false, "User does not have the required membership level");
			}

			if (!product.isAvailable() || product.getStock() <= 0) {
				return new EligibilityStatusRepresentation(false, "Product is not available in stock");
			}
			switch (product.getProductType().toLowerCase()) {
			case "premium":
				if (!user.isActive()) {
					return new EligibilityStatusRepresentation(false,
							"User account is not active for premium products");
				}
				break;
			case "financial":
				if (!user.getEmploymentStatus().equalsIgnoreCase("employed")) {
					return new EligibilityStatusRepresentation(false,
							"User must be employed to purchase financial products");
				}
				break;

			default:
				break;
			}

			return new EligibilityStatusRepresentation(true, "User is eligible for the product");
		} catch (Exception e) {
			throw new RuntimeException("Error occured while evaluating Eligibility" + e.getMessage());
		}
	}

	@Override
	public List<ProductRepresentation> getEligibleProductsForUser(String accountId) {
		try {
			AccountRepresentation user = accountsRepository.findByAccountId(accountId)
					.orElseThrow(() -> new ResourceNotFoundException("User not found", accountId, ""));
			List<ProductRepresentation> products = productRepository.findAll();
			return products.stream().filter(product -> product.getMinAge() <= user.getAge())
					.filter(product -> !product.isMembershipRequired() || user.isMembershipLevel())
					.filter(ProductRepresentation::isAvailable).collect(Collectors.toList());
		} catch (Exception e) {
			throw new RuntimeException(
					"Error occured while accessing Eligibility for user with id:" + accountId + e.getMessage());
		}
	}

	@Override
	public boolean createOfferForUser(String accountId, OfferRepresentation offer) {
		boolean isOfferCreated=false;
		try {
			AccountRepresentation user = accountsRepository.findByAccountId(accountId)
					.orElseThrow(() -> new ResourceNotFoundException("User not found", accountId, ""));
			List<OfferRepresentation> offers = user.getOffers();
			offers.add(offer);
			user.setOffers(offers);
			accountsRepository.save(user);
			isOfferCreated=true;
			return isOfferCreated;
		} catch (Exception e) {
			throw new RuntimeException(
					"Error occured while creating offer for user with id:" + accountId + e.getMessage());

		}
	}

	@Override
	public List<OfferRepresentation> getUserOffers(String accountId) {
		try {
			AccountRepresentation user = accountsRepository.findByAccountId(accountId)
					.orElseThrow(() -> new ResourceNotFoundException("User not found", accountId, ""));
			List<OfferRepresentation> offers = user.getOffers();
			return offers;
		} catch (Exception e) {
			throw new RuntimeException("Error occured while accessing for user with id:" + accountId + e.getMessage());

		}
	}

}
