package com.Mercurious.eligibilityservice.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Mercurious.eligibilityservice.dto.ResponseDto;
import com.Mercurious.eligibilityservice.entity.AccountRepresentation;
import com.Mercurious.eligibilityservice.entity.EligibilityStatusRepresentation;
import com.Mercurious.eligibilityservice.entity.OfferRepresentation;
import com.Mercurious.eligibilityservice.entity.ProductRepresentation;
import com.Mercurious.eligibilityservice.exception.OffereAlreadyExistsException;
import com.Mercurious.eligibilityservice.exception.ResourceNotFoundException;
import com.Mercurious.eligibilityservice.repository.AccountsRepository;
import com.Mercurious.eligibilityservice.repository.OfferRepository;
import com.Mercurious.eligibilityservice.repository.ProductRepository;
import com.Mercurious.eligibilityservice.service.IEligibilityService;
import com.Mercurious.eligibilityservice.service.client.AccountsClient;
import com.Mercurious.eligibilityservice.service.client.ProductsClient;

import jakarta.transaction.Transactional;

@Service
public class EligibilityServiceImpl implements IEligibilityService {
	@Autowired
	private AccountsRepository accountsRepository;

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private AccountsClient accountsClient;
	@Autowired
	private ProductsClient productsClient;
	@Autowired
	private OfferRepository offerRepository;

	
	public EligibilityServiceImpl(AccountsRepository accountsRepository, ProductRepository productRepository,
			AccountsClient accountsClient, ProductsClient productsClient, OfferRepository offerRepository,
			Date utilDate) {
		super();
		this.accountsRepository = accountsRepository;
		this.productRepository = productRepository;
		this.accountsClient = accountsClient;
		this.productsClient = productsClient;
		this.offerRepository = offerRepository;
		this.utilDate = utilDate;
	}
	Date utilDate=new Date();
	@Transactional
	@Override
	public ResponseEntity<ResponseDto>  createProduct(ProductRepresentation product) {
//		Optional<ProductRepresentation> optionalProduct = productRepository.findByProductId(product.getProductId());
//		if (optionalProduct.isPresent()) {
//			throw new OffereAlreadyExistsException(
//					"Product already registered with given product " + product.getProductId());
//		}
//
//		return productRepository.save(product);
		ResponseEntity<ResponseDto>  response=productsClient.createProduct(product);
		return response;
	}

	@Override
	public ResponseEntity<ResponseDto> createAccount(AccountRepresentation account) {
//		Optional<AccountRepresentation> optionalAccount = accountsRepository.findByAccountId(account.getAccountId());
//		if (optionalAccount.isPresent()) {
//			throw new OffereAlreadyExistsException(
//					"Customer already registered with given accountId " + account.getAccountId());
//		}
//		account.setCreatedDate(new java.sql.Date(utilDate.getTime()));;
//		return accountsRepository.save(account);
		ResponseEntity<ResponseDto>  response=accountsClient.createAccount(account);
		return response;
	}

	@Override
	public EligibilityStatusRepresentation evaluateEligibility(String accountId, String productId) {
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
		} catch (RuntimeException e) {
			throw new RuntimeException(
					"Error occured while accessing Eligibility for user with id:" + accountId + e.getMessage());
		}
	}
	public boolean isOfferAttachedToAccount(String accountId, String offerId) {
	    Optional<AccountRepresentation> optionalAccount = accountsRepository.findByAccountId(accountId);
	    Optional<OfferRepresentation> optionalOffer = offerRepository.findByOfferId(offerId);

	    if (optionalAccount.isPresent() && optionalOffer.isPresent()) {
	        AccountRepresentation account = optionalAccount.get();
	        OfferRepresentation offer = optionalOffer.get();
	        return account.getOffers().contains(offer);
	    }

	    return false;
	}


	@Override
	public boolean createOfferForUser(String accountId, OfferRepresentation newOffer) {
		boolean isOfferCreated = false;
		 if (!isOfferAttachedToAccount(accountId, newOffer.getOfferId())) {
		        AccountRepresentation user = accountsRepository.findByAccountId(accountId)
		                .orElseThrow(() -> new ResourceNotFoundException("User not found", accountId, ""));
		        Set<OfferRepresentation> offers = user.getOffers();
		        offers.add(newOffer);
		        user.setOffers(offers);
		        accountsRepository.save(user);
		        isOfferCreated = true;
		    } else {
		        throw new OffereAlreadyExistsException("Provided Offer Already Exists for this account:" + accountId);
		    }

		    return isOfferCreated;

	}

	@Override
	public Set<OfferRepresentation> getUserOffers(String accountId) {
		try {
			AccountRepresentation user = accountsRepository.findByAccountId(accountId)
					.orElseThrow(() -> new ResourceNotFoundException("User not found", accountId, ""));
			Set<OfferRepresentation> offers = user.getOffers();
			System.out.println(offers);
			return offers;
		} catch (Exception e) {
			throw new RuntimeException("Error occured while accessing for user with id:" + accountId + e.getMessage());
		}
	}

}
