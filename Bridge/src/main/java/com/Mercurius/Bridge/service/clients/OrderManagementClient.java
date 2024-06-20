package com.Mercurius.Bridge.service.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.Mercurius.Bridge.dto.ResponseDto;
import com.Mercurius.Bridge.entity.Order;
import com.Mercurius.Bridge.entity.OrderItem;

@FeignClient(name = "ORDER")
public interface OrderManagementClient {

    @PostMapping("/api/orders/create")
    ResponseEntity<ResponseDto> createOrder(@RequestBody Order order);

    @PostMapping(value = "/api/orders/createOrderItem", consumes = "application/json", produces = "application/json")
    ResponseEntity<ResponseDto> createOrderItem(@RequestBody OrderItem orderItem);

    @GetMapping(value = "/api/orders/getAllOrders", produces = "application/json")
    ResponseEntity<List<Order>> getAllOrders();

    @GetMapping(value = "/api/orders/cart", produces = "application/json")
    ResponseEntity<List<OrderItem>> getCart(@RequestParam("accString") String accString);
}
