package com.Mercurius.accountservice.service.impl;

import org.springframework.stereotype.Service;

import com.Mercurius.accountservice.entity.AccountRepresentation;

import reactor.core.publisher.Sinks;

@Service
public class OrderService {
	private final Sinks.Many<AccountRepresentation> accountInfoSink = Sinks.many().replay().latest();

	public Sinks.Many<AccountRepresentation> getAccountInfoSink() {
		return accountInfoSink;
	}
}
