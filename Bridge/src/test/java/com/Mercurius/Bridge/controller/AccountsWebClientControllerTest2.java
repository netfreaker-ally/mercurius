package com.Mercurius.Bridge.controller;


import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;

import com.Mercurius.Bridge.entity.AccountRepresentation;
import com.Mercurius.Bridge.exception.GlobalExceptionHandler;
import com.Mercurius.Bridge.service.serviceImpl.AccountsWebclientServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.netflix.discovery.EurekaClient;

import reactor.core.publisher.Flux;

@WebFluxTest(controllers = AccountsWebClientController.class)
@Import({ AccountsWebclientServices.class, GlobalExceptionHandler.class })
@WireMockTest(httpPort = 8080)
public class AccountsWebClientControllerTest2 {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private EurekaClient eurekaClient;

    @MockBean
    private AccountsWebclientServices accountsWebclientServices;

    @TestConfiguration
    static class WebClientTestConfig {
        @Bean
        public WebClient.Builder webClientBuilder() {
            return WebClient.builder();
        }
    }

    @Test
    void testGetAccount() {
        AccountRepresentation demoAccount = new AccountRepresentation("1", "ACC12345", "John Doe", "Savings", 1000.0,
                new Date(), new Date(), true, 30, "New York", 50000.0, "Employed", false);
        List<AccountRepresentation> accountsList = List.of(demoAccount);

        try {
            stubFor(get(urlEqualTo("/api/accuonts"))
                    .willReturn(aResponse().withHeader("Content-Type", "application/json")
                            .withBody(new ObjectMapper().writeValueAsString(accountsList))));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Mockito.when(accountsWebclientServices.getAccounts()).thenReturn(Flux.fromIterable(accountsList));

        webTestClient.get().uri("/api/bridge/account-webclient").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(AccountRepresentation.class)
                .consumeWith(response -> {
                    List<AccountRepresentation> accounts = response.getResponseBody();
                    assertNotNull(accounts, "The accounts list should not be null");
                    assertNotNull(accounts.get(0), "The account should not be null");
                    System.out.println("all accounts : \t" + accounts.toString());
                });
    }
}
