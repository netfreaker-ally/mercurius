package com.Mercurius.accountservice.routes;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.Mercurius.accountservice.service.impl.AccountsHandler;

@Configuration
public class AccountesRouter {
//	.POST("/subscribe", accountsHandler::subscribeToOrderEvents)
//	.POST("/publish", accountsHandler::publishOrderEvent)

	@Bean
	RouterFunction<ServerResponse> AccountesRoutes(AccountsHandler accountsHandler) {
		return route()
				.path("/api/accuonts", builder -> builder.POST("/create", accountsHandler::createAccount)
						.GET("", accountsHandler::getAccounts).DELETE("/{accountId}", accountsHandler::deleteAccount)
						.PUT("/{accountId}", accountsHandler::updateAccount))
				.GET("/hello", request -> ServerResponse.ok().bodyValue("Hello World"))
				.POST("/create-offer", accountsHandler::createOffer)
				.GET("/subscribe", accountsHandler::subscribeToOrderEvents)
				.build();
	}
}
