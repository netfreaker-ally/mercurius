package com.Mercurius.Bridge.service.serviceImpl;


import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.Mercurius.Bridge.entity.AccountRepresentation;
import com.Mercurius.Bridge.exception.ResourceNotFoundException;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class AccountsWebclientServices {

	private WebClient webClient;

	
	public AccountsWebclientServices(WebClient.Builder webClientBuilder) {
		super();
		this.webClient = webClientBuilder.baseUrl("http://account-service/api/accuonts").build();
	}

	public Flux<AccountRepresentation> getAccounts() {
		String movieInfoId = "ACC1234522";

		return webClient.get().uri("").retrieve().onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
			if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) {
				log.error("clientError--------------------");
				return Mono.error(new ResourceNotFoundException("There is no movie with this id: ", movieInfoId,
						String.valueOf(clientResponse.statusCode().value())));
			}
			return Mono.error(new ResourceNotFoundException("4xx error: ", movieInfoId,
					String.valueOf(clientResponse.statusCode().value())));
		}).onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
			if (clientResponse.statusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
				log.error("clientError--------------------");
				return Mono.error(new ResourceNotFoundException("Internal server error: ", movieInfoId,
						String.valueOf(clientResponse.statusCode().value())));
			}
			return Mono.error(new ResourceNotFoundException("5xx error: ", movieInfoId,
					String.valueOf(clientResponse.statusCode().value())));
		}).bodyToFlux(AccountRepresentation.class).onErrorReturn(new AccountRepresentation());
	}

}
