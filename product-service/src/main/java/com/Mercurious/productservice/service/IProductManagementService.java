package com.Mercurious.productservice.service;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.Mercurious.productservice.entity.ProductRepresentation;

public interface IProductManagementService {

	ProductRepresentation getProductById(@PathVariable("id") String productId);

	ProductRepresentation createProduct(@RequestBody ProductRepresentation product);

	boolean updateProduct(@RequestBody ProductRepresentation product);

	boolean deleteProduct(@PathVariable("id") String productId);

	List<ProductRepresentation> getAllProducts();

}
