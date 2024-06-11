package com.mercurius.order.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercurius.order.Repository.CartRepository;
import com.mercurius.order.Repository.OrderItemRepository;
import com.mercurius.order.Repository.OrderRepository;
import com.mercurius.order.entity.Cart;
import com.mercurius.order.entity.Order;
import com.mercurius.order.entity.OrderItem;
import com.mercurius.order.service.OrderService;
import com.mercurius.order.service.ShoppingCartService;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;
	@Autowired
	private ShoppingCartService shoppingCartService;
	@Autowired
	CartRepository cartRepository;
	@Override
	public String createOrder(Order order) {
		
		List<OrderItem> orderitem=null;
		Cart cart=cartRepository.findById("123").orElse(null);
		order.setorderItems(cart.getOrderItems());
		orderRepository.save(order);
		Cart newcart=cartRepository.findById("123").orElse(null);
		newcart.setOrderItems(null);
		cartRepository.save(newcart);
		return "Order created successfully";
	}

	public String createOrderItem(OrderItem orderItem) {
		shoppingCartService.addOrderItem(orderItem);
		orderItemRepository.save(orderItem);
		return "Order item created successfully";
	}

	@Override
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	@Override
	public Order getOrderById(String orderId) {
		return orderRepository.findById(orderId).orElse(null);
	}

	@Override
	public List<OrderItem> getOrderItemsByOrderId(String orderId) {
		List<OrderItem> orderItem = orderItemRepository.findByOrderId(orderId);
		if (orderItem.size() == 0) {
			return null;
		}

		return orderItem;

	}
}
