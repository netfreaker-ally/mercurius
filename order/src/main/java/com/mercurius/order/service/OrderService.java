package com.mercurius.order.service;

import java.util.List;

import com.mercurius.order.entity.Order;
import com.mercurius.order.entity.OrderItem;

public interface OrderService {
	String createOrder(Order order);
    String createOrderItem(OrderItem orderItem);
    List<Order> getAllOrders();
    Order getOrderById(String orderId);
    List<OrderItem> getOrderItemsByOrderId(String orderId);
	  
}
