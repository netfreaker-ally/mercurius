package com.Mercurius.Bridge.controller;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.HashSet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.Mercurius.Bridge.entity.AccountRepresentation;
import com.Mercurius.Bridge.exception.GlobalExceptionHandler;
import com.Mercurius.Bridge.service.serviceImpl.AccountsWebclientServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@AutoConfigureWebTestClient
@WebFluxTest
@ContextConfiguration(classes = { AccountsWebClientController.class, AccountsWebclientServices.class,
		GlobalExceptionHandler.class })
@Slf4j
class AccountsWebClientControllerTest2 {
	@Autowired
	private WebTestClient webTestClient;

	static String ACCOUNTS_URL = "/api/bridge/account-webclient";
	@MockBean
	private AccountsWebclientServices accountsWebclientServices;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetAccount() {
		AccountRepresentation demoAccount = new AccountRepresentation("1", "ACC12345", "John Doe", "Savings", 1000.0,
				new Date(), new Date(), true, 30, "New York", 50000.0, "Employed", false);

		try {
			stubFor(get(urlEqualTo("/api/accuonts")).willReturn(aResponse().withHeader("Content-Type", "application/json")
					.withBody(new ObjectMapper().writeValueAsString(demoAccount))));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		webTestClient.get().uri(ACCOUNTS_URL).exchange().expectBody(AccountRepresentation.class)
				.consumeWith(consumer -> {
					AccountRepresentation account = consumer.getResponseBody();
					assertNotNull(account, "The account should not be null");
					log.info("all accounts : \t" + account.toString());
				});

	}

}
