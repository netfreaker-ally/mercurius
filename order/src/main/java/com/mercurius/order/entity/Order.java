package com.mercurius.order.entity;

import java.sql.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "ordertbl")
public class Order {
	@Id
	private String orderId;
	private String accountId;
	 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private Date orderDate;
	@OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
	@JsonIgnoreProperties("order")
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
		return "Order{" + "orderId='" + orderId + '\'' + ", accountId='" + accountId + '\'' + ", orderDate=" + orderDate
				+ ", orderItems=" + orderItems + '}';
	}
}
