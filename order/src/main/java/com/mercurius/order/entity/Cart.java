package com.mercurius.order.entity;


import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Cart {

	@Id
	private String accountId;
	@OneToMany(mappedBy = "cart")
    private List<OrderItem> orderItems;

	public Cart() {
	}

	public Cart(String accountId, List<OrderItem> orderItems) {
		this.accountId = accountId;
		this.orderItems = orderItems;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
}
