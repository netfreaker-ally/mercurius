package com.Mercurius.Bridge.entity;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityReference;



public class Order {

	private String orderId;
	private String accountId;
	
	private Date orderDate;

	@JsonIdentityReference(alwaysAsId = true)
	private List<OrderItem> orderItems;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public List<OrderItem> getorderItems() {
		return orderItems;
	}

	public Order(String orderId, String accountId, Date orderDate, List<OrderItem> orderItems) {
		this.orderId = orderId;
		this.accountId = accountId;
		this.orderDate = orderDate;
		this.orderItems = orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public Order() {
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", accountId=" + accountId + ", orderDate=" + orderDate + ", orderItems="
				+ orderItems.toString() + "]";
	}

	
}
