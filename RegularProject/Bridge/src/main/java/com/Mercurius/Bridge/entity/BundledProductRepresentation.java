package com.Mercurius.Bridge.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
//@Data
//@AllArgsConstructor

public class BundledProductRepresentation {
	private String productId;
    private String name;
    private String description;
    private double price;
    private List<ProductRepresentation> products;
	public BundledProductRepresentation(String productId, String name, String description, double price,
			List<ProductRepresentation> products) {
		super();
		this.productId = productId;
		this.name = name;
		this.description = description;
		this.price = price;
		this.products = products;
	}
	public BundledProductRepresentation() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public List<ProductRepresentation> getProducts() {
		return products;
	}
	public void setProducts(List<ProductRepresentation> products) {
		this.products = products;
	}
    
    

}
