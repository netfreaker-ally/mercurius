package com.Mercurious.productservice.functions;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.Mercurious.productservice.entity.ProductRepresentation;
import com.Mercurious.productservice.service.IProductManagementService;

@Configuration
public class ProductsFunction {
	@Bean
	public Consumer<ProductRepresentation> updateCommunication(IProductManagementService productManagement) {
		return productRepresentaion -> {

			productManagement.updateCommunicationStatus(productRepresentaion);
		};
	}

}
