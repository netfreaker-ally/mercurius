package com.Mercurius.ApiGateway.filter;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.WebFilter;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class WebSessionConfig {
//have to store in db
	private final ConcurrentHashMap<String, String> userSessionMap = new ConcurrentHashMap<>();

	@Bean
	WebFilter webSessionTimeoutFilter() {
		return (exchange, chain) -> {
			return exchange.getSession()
					.flatMap(webSession -> ReactiveSecurityContextHolder.getContext()
							.map(securityContext -> securityContext.getAuthentication().getName())
							.defaultIfEmpty("anonymousUser").flatMap(username -> {
								String currentSessionId = webSession.getId();
								String existingSessionId = userSessionMap.put(username, currentSessionId);
								log.info("userSessionMap: "+userSessionMap);
								if (existingSessionId != null && !existingSessionId.equals(currentSessionId)) {
									return exchange.getSession().flatMap(session -> {
										session.invalidate();
										return chain.filter(exchange);
									});
								}

								webSession.setMaxIdleTime(Duration.ofMinutes(30));
								return chain.filter(exchange);
							}));
		};
	}

}
