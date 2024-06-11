package com.mercurius.order.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
@Entity
public class Order {
	@Id
	private String orderId;
	private String accountId;
	private LocalDateTime orderDate;
	 @OneToMany(mappedBy = "orderId")
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

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public List<OrderItem> getorderItems() {
		return orderItems;
	}

	public void setorderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public Order(String orderId, String accountId, LocalDateTime orderDate, List<OrderItem> orderItems) {
		this.orderId = orderId;
		this.accountId = accountId;
		this.orderDate = orderDate;
		this.orderItems = orderItems;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public Order() {
	}

	@Override
	public String toString() {
		return "Order{" + "orderId='" + orderId + '\'' + ", accountId='" + accountId + '\'' + ", orderDate=" + orderDate
				+ ", orderItems=" + orderItems + '}';
	}
}
