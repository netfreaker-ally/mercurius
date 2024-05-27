
package com.Mercurius.Bridge.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class EligibilityStatusRepresentation {
	private String userId;
	private String productId;
	private boolean eligible;
	private AccountRepresentation account;
	private ProductRepresentation product;
	public EligibilityStatusRepresentation(String userId, String productId, boolean eligible,
			AccountRepresentation account, ProductRepresentation product) {
		super();
		this.userId = userId;
		this.productId = productId;
		this.eligible = eligible;
		this.account = account;
		this.product = product;
	}
	public EligibilityStatusRepresentation() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public boolean isEligible() {
		return eligible;
	}
	public void setEligible(boolean eligible) {
		this.eligible = eligible;
	}
	public AccountRepresentation getAccount() {
		return account;
	}
	public void setAccount(AccountRepresentation account) {
		this.account = account;
	}
	public ProductRepresentation getProduct() {
		return product;
	}
	public void setProduct(ProductRepresentation product) {
		this.product = product;
	}
	
	
}
