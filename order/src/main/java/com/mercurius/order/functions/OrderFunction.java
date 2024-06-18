package com.mercurius.order.functions;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mercurius.order.entity.Order;
import com.mercurius.order.service.OrderService;



@Configuration
public class OrderFunction {

	public OrderFunction() {
		// TODO Auto-generated constructor stub
	}
	@Bean
	public Consumer<Order> orderconfirmed(OrderService orderService) {
		return orderRepresentation -> {

			orderService.UpdateCommunicationStatus(orderRepresentation);
		};
	}

}
