package com.Mercurious.productservice.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Mercurious.productservice.entity.ProductRepresentation;
import com.Mercurious.productservice.exception.ProductAlreadyExistsException;
import com.Mercurious.productservice.exception.ResourceNotFoundException;
import com.Mercurious.productservice.repository.AccountsRepository;
import com.Mercurious.productservice.repository.ProductRepository;
import com.Mercurious.productservice.service.IProductManagementService;

import jakarta.transaction.Transactional;

@Service
public class ProductServiceImpl implements IProductManagementService {
	@Autowired
	ProductRepository productRepository;
	@Autowired
	private AccountsRepository accountRepository;

	public ProductServiceImpl(ProductRepository productRepository, AccountsRepository accountRepository) {
		super();
		this.productRepository = productRepository;
		this.accountRepository = accountRepository;
	}

	@Override
	public ProductRepresentation getProductById(String productId) {
		ProductRepresentation product = productRepository.findByProductId(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", productId, ""));
		return product;
	}

	@Transactional
	@Override
	public ProductRepresentation createProduct(ProductRepresentation product) {
		Optional<ProductRepresentation> optionalProduct = productRepository.findByProductId(product.getProductId());
		if (optionalProduct.isPresent()) {
			throw new ProductAlreadyExistsException(
					"Product already registered with given product " + product.getProductId());
		}
		
		return productRepository.save(product);
	}

	@Override
	public boolean updateProduct(ProductRepresentation product) {
		boolean isUpdated = false;
		try {
			ProductRepresentation productDetails = productRepository.findByProductId(product.getProductId())
					.orElseThrow(() -> new ResourceNotFoundException("Account not found with id ",
							product.getProductId(), ""));

		
			productDetails.setDescription(product.getDescription());
			productDetails.setPrice(product.getPrice());
			productDetails.setProductId(product.getProductId());
			productDetails.setProductName(product.getProductName());

			productRepository.save(productDetails);
			isUpdated = true;
			return isUpdated;
		} catch (Exception e) {
			throw new RuntimeException("Error Occurred while Updating");
		}
	}

	@Override
	public boolean deleteProduct(String productId) {
		boolean isDelated = false;
		try {
			ProductRepresentation product = productRepository.findByProductId(productId)
					.orElseThrow(() -> new ResourceNotFoundException("Product not found with id ", productId, ""));
			productRepository.delete(product);
			isDelated = true;
			return isDelated;

		} catch (Exception e) {
			throw new RuntimeException("AccountId:" + productId + "\nerror Message:\n" + e.getMessage());
		}

	}

	@Override
	public List<ProductRepresentation> getAllProducts() {
		List<ProductRepresentation> allProducts = productRepository.findAll();
		return allProducts;
	}

}
