package com.Mercurius.Notifications.functions;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.Mercurius.Notifications.entity.EmailDetails;
import com.Mercurius.Notifications.entity.ProductRepresentation;
import com.Mercurius.Notifications.service.EmailService;

@Configuration
public class NotificationFunction {
	@Autowired
	StreamBridge streamBridge;
	@Autowired
	private EmailService emailService;

	@Bean
	public Function<ProductRepresentation, ProductRepresentation> productcreated() {
		return product -> {
			sendMessageToProduct(product);
			EmailDetails details = new EmailDetails("netfreakerr@gmail.com", "Hey,This mail regarding confirmation",
					"Product is created");

			String status = emailService.sendSimpleMail(details);
			System.out.println("Status: " + status);

			return product;
		};
	}

	public void sendMessageToProduct(ProductRepresentation product) {
		product.setProductName("modifiedInNotification");
		boolean isSend = streamBridge.send("communication-sent", product);

	}
}
