package com.Mercurius.accountservice.exception;

import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalExceptionHandlerController implements ErrorWebExceptionHandler {

	public GlobalExceptionHandlerController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
		log.error("Exception Message is : {} ", ex.getMessage(), ex);

		DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();
		var errorMessage = bufferFactory.wrap(ex.getMessage().getBytes());
		if (ex instanceof ResourceNotFoundException) {
			exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);
			return exchange.getResponse().writeWith(Mono.just(errorMessage));
		}

		if (ex instanceof CustomerAlreadyExistsException) {
			exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
			return exchange.getResponse().writeWith(Mono.just(errorMessage));
		}

		exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
		return exchange.getResponse().writeWith(Mono.just(errorMessage));

	}

}
