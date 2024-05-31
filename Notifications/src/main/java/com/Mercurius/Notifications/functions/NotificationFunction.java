package com.Mercurius.Notifications.functions;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.Mercurius.Notifications.entity.ProductRepresentation;

@Configuration
public class NotificationFunction {
	@Autowired
	StreamBridge streamBridge;

	@Bean
	public Function<ProductRepresentation, ProductRepresentation> productcreated() {
		return product -> {
			sendMessageToProduct(product);
			return product;
		};
	}

	public void sendMessageToProduct(ProductRepresentation product) {
		product.setProductName("modifiedInNotification");
		boolean isSend = streamBridge.send("communication-sent", product);

	}
}
