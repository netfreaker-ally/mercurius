package com.Mercurius.ApiGateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.Mercurius.ApiGateway.filter.CustomRedirectFilter;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class GatewayConfig {

    @Bean
    public GatewayFilterFactory<CustomRedirectFilter> redirectFilterFactory() {
        return new GatewayFilterFactory<CustomRedirectFilter>() {

			@Override
			public GatewayFilter apply(CustomRedirectFilter config) {
				// TODO Auto-generated method stub
				log.info("---------Entered in Gateway config apply----------");
				return new CustomRedirectFilter();
			}
           
        };
    }
}
