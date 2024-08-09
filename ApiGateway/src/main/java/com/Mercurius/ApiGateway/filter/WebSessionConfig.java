package com.Mercurius.ApiGateway.filter;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.WebFilter;

@Configuration
public class WebSessionConfig {

	private final ConcurrentHashMap<String, String> userSessionMap = new ConcurrentHashMap<>();

	@Bean
	 WebFilter webSessionTimeoutFilter() {
		return (exchange, chain) -> {
			return exchange.getSession().flatMap(webSession -> 
				ReactiveSecurityContextHolder.getContext()
					.map(securityContext -> securityContext.getAuthentication().getName())
					.defaultIfEmpty("anonymousUser")
					.flatMap(username -> {
						String currentSessionId = webSession.getId();
						String existingSessionId = userSessionMap.put(username, currentSessionId);
						
						// Invalidate the old session if it exists
						if (existingSessionId != null && !existingSessionId.equals(currentSessionId)) {
							return exchange.getSession().flatMap(session -> {
								session.invalidate();
								return chain.filter(exchange);
							});
						}
						
						// Set session timeout and proceed
						webSession.setMaxIdleTime(Duration.ofMinutes(30));
						return chain.filter(exchange);
					})
			);
		};
	}

}
