package com.mercurius.order.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Cart {

	@Id
	private String accountId;
	private List<OrderItem> orderItems = new ArrayList<>();

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
