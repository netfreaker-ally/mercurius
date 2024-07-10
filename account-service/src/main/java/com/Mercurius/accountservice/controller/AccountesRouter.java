package com.Mercurius.accountservice.controller;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;


import com.Mercurius.accountservice.service.impl.AccountsHandler;

@Configuration
public class AccountesRouter {
	@Bean
	public RouterFunction<ServerResponse> AccountesRoutes(AccountsHandler accountsHandler) {

		return route().path("/api/accuonts",
				builder -> builder.POST("/create", accountsHandler::createAccount).GET("", accountsHandler::getAccounts)
						.DELETE("", accountsHandler::deleteAccount).PUT("", accountsHandler::updateAccount))
				.build();

	}

}
