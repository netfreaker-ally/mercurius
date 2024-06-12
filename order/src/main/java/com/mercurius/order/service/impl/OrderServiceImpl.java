package com.mercurius.order.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private ShoppingCartService shoppingCartService;

	@Autowired
	private CartRepository cartRepository;

	public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
			ShoppingCartService shoppingCartService, CartRepository cartRepository) {
		super();
		this.orderRepository = orderRepository;
		this.orderItemRepository = orderItemRepository;
		this.shoppingCartService = shoppingCartService;
		this.cartRepository = cartRepository;
	}

	@Override
	@Transactional
	public String createOrder(Order order) {
		Cart cart = cartRepository.findById("123").orElse(null);
		if (cart == null || cart.getOrderItems() == null || cart.getOrderItems().isEmpty()) {
			return "Cart is empty";
		}

		order.setOrderItems(cart.getOrderItems());
		orderRepository.save(order);

		cart.setOrderItems(null);
		cartRepository.save(cart);

		return "Order created successfully";
	}

	@Override
	@Transactional
	public String createOrderItem(OrderItem orderItem) {
	    orderItem.setOrderItemId(UUID.randomUUID().toString()); // Generate new ID

	    // Find or create associated Order
	    Order order = orderRepository.findById("123").orElse(new Order());
	    order.setOrderId(UUID.randomUUID().toString()); // Manually assign Order ID
	    order.setAccountId("123");

	    // Find or create associated Cart
	    Cart cart = cartRepository.findById("123").orElse(new Cart());
	    cart.setAccountId("123");

	    // Set Order and Cart to the OrderItem
	    orderItem.setOrder(order);
	    orderItem.setCart(cart);

	    // Save OrderItem
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
//		List<OrderItem> orderItem = orderItemRepository.findByOrderId(orderId);
		List<OrderItem> orderItem = null;
		if (orderItem.size() == 0) {
			return null;
		}

		return orderItem;

	}

	@Override
	public List<OrderItem> getAllOrderItems() {

		return orderItemRepository.findAll();
	}

	@Override
	public List<Cart> getAllCartItems() {
		// TODO Auto-generated method stub
		return cartRepository.findAll();
	}
}
