package com.Mercurius.Bridge.service.serviceImpl;


import org.apache.hc.core5.reactor.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.Mercurius.Bridge.entity.AccountRepresentation;
import com.Mercurius.Bridge.exception.ResourceNotFoundException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountsWebclientServices {

	private WebClient webClient;

	@Autowired
	public AccountsWebclientServices(WebClient.Builder webClientBuilder) {
		super();
		this.webClient = webClientBuilder.baseUrl("http://account-service/api/accuonts").build();
	}

	public Flux<AccountRepresentation> getAccounts() {
		String movieInfoId = "ACC1234522";
		
		return webClient.get().uri("")

				.retrieve().onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
					if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) {

						return Mono.error(new ResourceNotFoundException("There is no movie with this id: ", movieInfoId,
								String.valueOf(clientResponse.statusCode().value())));
					}
					return clientResponse.bodyToMono(String.class)
							.flatMap(response -> Mono
									.error(new ResourceNotFoundException("Some error as occured art client side: ",
											movieInfoId, String.valueOf(clientResponse.statusCode().value()))));

				}).bodyToFlux(AccountRepresentation.class)

				.onErrorReturn(new AccountRepresentation());
	}

}
