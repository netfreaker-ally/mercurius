package com.mercurius.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mercurius.order.constants.OrderConstants;
import com.mercurius.order.dto.ResponseDto;

import com.mercurius.order.entity.Order;
import com.mercurius.order.entity.OrderItem;
import com.mercurius.order.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	@Autowired
	OrderService orderService;

	public OrderController(OrderService orderService) {
		super();
		this.orderService = orderService;
	}

	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createOrder(@Valid @RequestBody Order order) {
		
		orderService.createOrder( order);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(OrderConstants.STATUS_201, OrderConstants.MESSAGE_201));
	}

	@PostMapping("/createOrderItem")
	public ResponseEntity<ResponseDto> createOrderItem(@RequestBody OrderItem orderItem) {

		orderService.createOrderItem(orderItem);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(OrderConstants.STATUS_201, OrderConstants.MESSAGE_201));
	}

	@GetMapping("/checkout")
	public ResponseEntity<List<Order>> getAllOrders() {
		return ResponseEntity.status(HttpStatus.FOUND).body(orderService.getAllOrders());
	}

	

	@GetMapping("/cart")
	public ResponseEntity<List<OrderItem>> getCart(@RequestParam String accString) {
		return ResponseEntity.status(HttpStatus.FOUND).body(orderService.getAllCartItems(accString));
	}
}