package com.Mercurius.accountservice.service.impl;

import org.springframework.stereotype.Component;

import com.Mercurius.accountservice.entity.AccountRepresentation;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Component
public class OrderService {

	 private  Sinks.Many<AccountRepresentation> accountInfoSink = Sinks.many().replay().latest();

	    public Sinks.Many<AccountRepresentation> getAccountInfoSink() {
	        return accountInfoSink;
	    }

	   

}
