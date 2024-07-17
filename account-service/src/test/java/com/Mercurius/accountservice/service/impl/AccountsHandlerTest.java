package com.Mercurius.accountservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.Mercurius.accountservice.entity.AccountRepresentation;
import com.Mercurius.accountservice.repository.AccountsRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@AutoConfigureWebTestClient
class AccountsHandlerTest {
	@Autowired
	WebTestClient webTestClient;
	@Autowired
	AccountsRepository accountsRepository;
	static String ACCOUNTS_URL = "http://localhost:8083/api/accuonts";
	static String SUBSCRIBE_URL = "/subscribe";

	@BeforeEach
	void setUp() throws Exception {
		List<AccountRepresentation> allAccount = List.of(
				new AccountRepresentation("", "ACC12345", "John Doe", "Savings", 1000.0, new Date(), new Date(), false,
						30, "New York", 50000.0, "Employed", true, new HashSet<>()),

				new AccountRepresentation("", "ACC67890", "Jane Smith", "Checking", 2000.0, new Date(), new Date(),
						true, 28, "Los Angeles", 60000.0, "Self-Employed", false, new HashSet<>()),

				new AccountRepresentation("", "ACC13579", "Alice Johnson", "Savings", 3000.0, new Date(), new Date(),
						true, 35, "Chicago", 70000.0, "Unemployed", true, new HashSet<>()),

				new AccountRepresentation("", "ACC24680", "Bob Brown", "Checking", 4000.0, new Date(), new Date(),
						false, 40, "San Francisco", 80000.0, "Retired", false, new HashSet<>()),
				new AccountRepresentation("", "ACC11223", "Charlie White", "Savings", 5000.0, new Date(), new Date(),
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
		webTestClient.get().uri(ACCOUNTS_URL).exchange().expectBodyList(AccountRepresentation.class).hasSize(5);

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
