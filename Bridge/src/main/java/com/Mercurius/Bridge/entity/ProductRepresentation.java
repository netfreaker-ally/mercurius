package com.Mercurius.Bridge.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Data
@NoArgsConstructor
public class ProductRepresentation {
	
	private Long id;

	private String productId;


	private String productName;

	private Double price;

	private String description;


	private Integer minAge;
	private boolean isMembershipRequired;

	private Long Stock;
	private boolean isAvailable;

	
	private String productType;

	

}
