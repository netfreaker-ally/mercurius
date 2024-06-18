package com.mercurius.order.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import com.mercurius.order.Repository.CartRepository;
import com.mercurius.order.Repository.OrderItemRepository;
import com.mercurius.order.Repository.OrderRepository;
import com.mercurius.order.entity.Cart;
import com.mercurius.order.entity.Order;
import com.mercurius.order.entity.OrderItem;
import com.mercurius.order.service.OrderService;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;
	@Autowired
	StreamBridge streamBridge;;

	@Autowired
	private CartRepository cartRepository;

	public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
			StreamBridge streamBridge, CartRepository cartRepository) {
		super();
		this.orderRepository = orderRepository;
		this.orderItemRepository = orderItemRepository;
		this.streamBridge = streamBridge;
		this.cartRepository = cartRepository;
	}

	@Override
	@Transactional
	public String createOrder(Order order) {
		List<OrderItem> orderItems = orderItemRepository.findByAccountId(order.getAccountId());
		if (orderItems == null || orderItems.isEmpty()) {
			return "Cart is empty";
		}
		String orderId = "ORD" + UUID.randomUUID().toString();
		order.setOrderId(orderId);
		for (OrderItem orderItem : orderItems) {
			orderItem.setOrder(order);
		}
		order.setOrderItems(orderItems);

		orderRepository.save(order);
		sendOrderConfirmation(order);

		// Optionally clear the cart
		// cartRepository.deleteAll();
		// orderItemRepository.deleteByAccountId(order.getAccountId());

		return "Order created successfully";
	}

	public void sendOrderConfirmation(Order order) {
		boolean isSent = streamBridge.send("sendOrderConfirmation-out-0", order);
		if (isSent) {
			System.out.println("OrderConfirmed Send to rabbitma");
		}
	}

	@Override
	@Transactional
	public String createOrderItem(OrderItem orderItem) {

		orderItem.setOrderItemId(UUID.randomUUID().toString());

		Cart cart = cartRepository.findById(orderItem.getCart().getAccountId()).orElse(new Cart());

		if (cart.getAccountId() == null || cart.getAccountId().isEmpty()) {
			cart.setAccountId(orderItem.getCart().getAccountId());
			cart.setOrderItems(new ArrayList<>());
		}

		cart.getOrderItems().add(orderItem);

		orderItem.setCart(cart);
		cartRepository.save(cart);

		orderItemRepository.save(orderItem);

		return "Order item created successfully";
	}

	@Override
	public List<Order> getAllOrders() {
		List<Order> orders = orderRepository.findAll();
		System.out.println("All orders: " + orders);
		return orders;
	}

	@Override
	public List<OrderItem> getAllCartItems(String accountId) {

		List<OrderItem> oi = orderItemRepository.findByAccountId(accountId);
		System.out.println("oi" + oi);
		return oi;
	}

	@Override
	public Order UpdateCommunicationStatus(Order order) {
		System.out.println("Message Received From RabbitMq ");
		return null;
	}
}
