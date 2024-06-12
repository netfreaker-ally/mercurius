package com.mercurius.order.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercurius.order.Repository.CartRepository;
import com.mercurius.order.entity.Cart;
import com.mercurius.order.entity.Order;
import com.mercurius.order.entity.OrderItem;

@Service
public class ShoppingCartService {

	Order odr = new Order();
	private List<OrderItem> orderItems = new ArrayList<>();

	public ShoppingCartService() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShoppingCartService(Order odr, List<OrderItem> orderItems, Cart cart) {
		super();
		this.odr = odr;
		this.orderItems = orderItems;

		this.cart = cart;
	}

	Cart cart = new Cart();

	public Order getOrderId() {

		if (odr.getOrderId() == null) {
			odr.setOrderId(UUID.randomUUID().toString());
		}
		return odr;
	}

//	public void addOrderItem(OrderItem orderItem) {
//		orderItem.setOrder(getOrderId());
//
//		orderItems.add(orderItem);
//
//	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
}