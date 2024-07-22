package com.Mercurius.accountservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verifyNoInteractions;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriBuilderFactory;

import com.Mercurius.accountservice.entity.AccountRepresentation;
import com.Mercurius.accountservice.repository.AccountsRepository;
import com.netflix.discovery.EurekaClient;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@AutoConfigureWebTestClient
@Slf4j
@ActiveProfiles("test")
class AccountsHandlerTest {
	@Autowired
	WebTestClient webTestClient;
	@Autowired
	AccountsRepository accountsRepository;
	static String ACCOUNTS_URL = "/api/accuonts";
	static String SUBSCRIBE_URL = "/subscribe";

	@MockBean
	private EurekaClient eurekaClient;

	@BeforeEach
	void setUp() throws Exception {
		List<AccountRepresentation> allAccount = List.of(
				new AccountRepresentation(null, "ACC12345", "John Doe", "Savings", 1000.0, new Date(), new Date(),
						false, 30, "New York", 50000.0, "Employed", true, new HashSet<>()),

				new AccountRepresentation(null, "ACC67890", "Jane Smith", "Checking", 2000.0, new Date(), new Date(),
						true, 28, "Los Angeles", 60000.0, "Self-Employed", false, new HashSet<>()),

				new AccountRepresentation(null, "ACC13579", "Alice Johnson", "Savings", 3000.0, new Date(), new Date(),
						true, 35, "Chicago", 70000.0, "Unemployed", true, new HashSet<>()),

				new AccountRepresentation(null, "ACC24680", "Bob Brown", "Checking", 4000.0, new Date(), new Date(),
						false, 40, "San Francisco", 80000.0, "Retired", false, new HashSet<>()),
				new AccountRepresentation(null, "ACC11223", "Charlie White", "Savings", 5000.0, new Date(), new Date(),
						true, 25, "Miami", 90000.0, "Student", true, new HashSet<>()));

		accountsRepository.saveAll(allAccount).blockLast();

	}

	@AfterEach
	void tearDown() throws Exception {
		accountsRepository.deleteAll().block();
	}

//	@Test
//	void testCreateAccount() {
//			
//		
//	}

	@Test
	void testGetAccounts() {
		var accountId = "ACC12345";

		webTestClient.get().uri(uriBuilder -> {
			return uriBuilder.path(ACCOUNTS_URL).queryParam("accountId", accountId).build();
		}).exchange().expectStatus().isOk().expectBody(AccountRepresentation.class).consumeWith(response -> {
			AccountRepresentation account = response.getResponseBody();
			assertNotNull(account, "The account should not be null");
			assertEquals("John Doe", account.getAccountName(), "Account name should be Bob Brown");
			// Add more assertions if necessary
		});
	}

//	@Test
//	void testUpdateAccount() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testDeleteAccount() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testSubscribeToOrderEvents() {
//		fail("Not yet implemented");
//	}

}
