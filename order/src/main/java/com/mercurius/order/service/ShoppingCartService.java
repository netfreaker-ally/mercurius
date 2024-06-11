package com.mercurius.order.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercurius.order.Repository.CartRepository;
import com.mercurius.order.entity.Cart;
import com.mercurius.order.entity.OrderItem;

@Service
public class ShoppingCartService {

	private String orderId;
	private List<OrderItem> orderItems = new ArrayList<>();
	@Autowired
	CartRepository cartRepository;
	Cart cart=new Cart();
	public String getOrderId() {
		if (orderId == null) {
			orderId = UUID.randomUUID().toString();
		}
		return orderId;
	}

	public void addOrderItem(OrderItem orderItem) {
		orderItem.setOrderId(getOrderId());
		orderItems.add(orderItem);
		cart.setOrderItems(orderItems);
		cart.setAccountId("123");
		cartRepository.save(cart);
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
}