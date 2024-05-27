package com.Mercurius.Bridge.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProductRepresentation {
	private String productId;
    private String productName;
    private double price;
    private String description;
    private AccountRepresentation account;
	public ProductRepresentation(String productId, String productName, double price, String description,
			AccountRepresentation account) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.description = description;
		this.account = account;
	}
	public ProductRepresentation() {
		super();
		// TODO Auto-generated constructor stub
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
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public AccountRepresentation getAccount() {
		return account;
	}
	public void setAccount(AccountRepresentation account) {
		this.account = account;
	}
    
}
