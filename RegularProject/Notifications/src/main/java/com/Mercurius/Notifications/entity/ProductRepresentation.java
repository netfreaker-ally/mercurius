package com.Mercurius.Notifications.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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

	public ProductRepresentation(Long id, @NotBlank(message = "Product ID is required") String productId,
			@NotBlank(message = "Product Name is required") String productName,
			@NotNull(message = "Price is required") Double price, String description,
			@NotNull(message = "Minimum Age is required") Integer minAge, boolean isMembershipRequired,
			@NotNull(message = "Stock is required") Long stock, boolean isAvailable,
			@NotBlank(message = "Product Type is required") String productType) {
		super();
		this.id = id;
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.description = description;
		this.minAge = minAge;
		this.isMembershipRequired = isMembershipRequired;
		Stock = stock;
		this.isAvailable = isAvailable;
		this.productType = productType;
	}

	public ProductRepresentation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getMinAge() {
		return minAge;
	}

	public void setMinAge(Integer minAge) {
		this.minAge = minAge;
	}

	public boolean isMembershipRequired() {
		return isMembershipRequired;
	}

	public void setMembershipRequired(boolean isMembershipRequired) {
		this.isMembershipRequired = isMembershipRequired;
	}

	public Long getStock() {
		return Stock;
	}

	public void setStock(Long stock) {
		Stock = stock;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

}
