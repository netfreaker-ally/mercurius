package com.mercurius.order.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercurius.order.constants.OrderConstants;
import com.mercurius.order.dto.ResponseDto;
import com.mercurius.order.entity.Order;
import com.mercurius.order.entity.OrderItem;
import com.mercurius.order.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	@Autowired
	OrderService orderService;

	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createOrder(@RequestBody Order order) {

		orderService.createOrder(order);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(OrderConstants.STATUS_201, OrderConstants.MESSAGE_201));
	}

	@PostMapping("/createOrderItem")
	public ResponseEntity<ResponseDto> createOrderItem(@RequestBody OrderItem orderItem) {
		
		orderService.createOrderItem(orderItem);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(OrderConstants.STATUS_201, OrderConstants.MESSAGE_201));
	}
}