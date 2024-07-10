package com.Mercurius.accountservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductRepresentation {
	@Id
	
	private Long id;

	@NotBlank(message = "Product ID is required")
	private String productId;

	@NotBlank(message = "Product Name is required")
	private String productName;

	@NotNull(message = "Price is required")
	private Double price;

	private String description;

	@NotNull(message = "Minimum Age is required")
	private Integer minAge;
	private boolean isMembershipRequired;
	@NotNull(message = "Stock is required")
	private Long Stock;
	private boolean isAvailable;

	@NotBlank(message = "Product Type is required")
	private String productType;
}
