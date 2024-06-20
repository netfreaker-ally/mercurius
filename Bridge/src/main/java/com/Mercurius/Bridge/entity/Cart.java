package com.Mercurius.Bridge.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;



public class Cart {

	
	private String accountId;
	@JsonIgnore
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
