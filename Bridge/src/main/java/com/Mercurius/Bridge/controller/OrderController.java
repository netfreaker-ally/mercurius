package com.Mercurius.Bridge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Mercurius.Bridge.dto.ResponseDto;
import com.Mercurius.Bridge.entity.Order;
import com.Mercurius.Bridge.entity.OrderItem;
import com.Mercurius.Bridge.service.IBridgeService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	@Autowired
	IBridgeService bridgeService;


    public OrderController(IBridgeService bridgeService) {
		super();
		this.bridgeService = bridgeService;
	}

	@PostMapping("/api/orders/create")
    ResponseEntity<ResponseDto> createOrder(@RequestBody Order order){
		return bridgeService.createOrder(order);
    	
    }

    @PostMapping(value = "/api/orders/createOrderItem", consumes = "application/json", produces = "application/json")
    ResponseEntity<ResponseDto> createOrderItem(@RequestBody OrderItem orderItem){
    	return bridgeService.createOrderItem(orderItem);
    }

    @GetMapping(value = "/api/orders/getAllOrders", produces = "application/json")
    ResponseEntity<List<Order>> getAllOrders(){
    	return bridgeService.getAllOrders();
    }

    @GetMapping(value = "/api/orders/cart", produces = "application/json")
    ResponseEntity<List<OrderItem>> getCart(@RequestParam("accString") String accString){
    	return bridgeService.getCart(accString);
    }
	
	
	
}
