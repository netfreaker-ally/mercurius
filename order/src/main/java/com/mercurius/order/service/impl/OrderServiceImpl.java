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

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;
//
//	@Autowired
//	private ShoppingCartService shoppingCartService;

	@Autowired
	private CartRepository cartRepository;

	public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
			CartRepository cartRepository) {
		super();
		this.orderRepository = orderRepository;
		this.orderItemRepository = orderItemRepository;

		this.cartRepository = cartRepository;
	}

//	@Override
//	@Transactional
//	public String createOrder(Order order) {
//		List<OrderItem> orderitem = orderItemRepository.findByAccountId(order.getAccountId());
//		if (orderitem == null || orderitem.isEmpty()) {
//			return "Cart is empty";
//		}
//		
//		order.setOrderId("ORD"+UUID.randomUUID().toString());
//		order.setOrderItems(orderitem);
//		orderRepository.save(order);
//
//		
//		orderItemRepository.deleteAll();
//
//		return "Order created successfully";
//	}
//
//
//	@Override
//	@Transactional
//	public String createOrderItem(OrderItem orderItem) {
//	   
//	    orderItem.setOrderItemId(UUID.randomUUID().toString());
//
//	 
//	    Cart cart = cartRepository.findById(orderItem.getCart().getAccountId()).orElse(new Cart());
//	    
//	  
//	    if (cart.getAccountId() == null || cart.getAccountId().isEmpty()) {
//	        cart.setAccountId(orderItem.getCart().getAccountId());
//	        cart.setOrderItems(new ArrayList<>());
//	    }
//	    
//	  
//	    List<OrderItem> listOfOrderItems = cart.getOrderItems();
//	    listOfOrderItems.add(orderItem);
//	    cart.setOrderItems(listOfOrderItems);
//
//	 
//	    orderItem.setCart(cart);
//	    
//	    
//	    cartRepository.save(cart);
////	    orderItem.setOrder(new Order() {{ setOrderId("ORD:" + UUID.randomUUID().toString()); }});
//
//	    orderItemRepository.save(orderItem);
//
//	    return "Order item created successfully";
//	}
	@Override
	@Transactional
	public String createOrder(Order order) {
		List<OrderItem> orderItems = orderItemRepository.findByAccountId(order.getAccountId());
		if (orderItems == null || orderItems.isEmpty()) {
			return "Cart is empty";
		}

		// Set a unique order ID
		order.setOrderId("ORD" + UUID.randomUUID().toString());

		order.setOrderItems(orderItems);

		Order ord=orderRepository.save(order);
		System.out.println("---------------------"+ord.toString());
		/*
		 * cartRepository.deleteAll();
		 */

		return "Order created successfully";
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
		return orderRepository.findAll();
	}


	@Override
	public List<OrderItem> getAllCartItems(String accountId) {
		
		List<OrderItem> oi=orderItemRepository.findByAccountId(accountId);
		
		return oi;
	}
}
