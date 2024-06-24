package com.Mercurius.Notifications.functions;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.Mercurius.Notifications.entity.EmailDetails;
import com.Mercurius.Notifications.entity.Order;
import com.Mercurius.Notifications.entity.PdfGenerator;
import com.Mercurius.Notifications.entity.ProductRepresentation;
import com.Mercurius.Notifications.service.EmailService;

@Configuration
public class NotificationFunction {
	@Autowired
	StreamBridge streamBridge;
	@Autowired
	private EmailService emailService;
	private static final Logger log = LoggerFactory.getLogger(NotificationFunction.class);

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
		boolean isSend = streamBridge.send("updateCommunication-in-0", product);
		if (isSend) {
			System.out.println("---order Confirmattion send to  product service----");
		}

	}

	@Bean
	public Function<Order, Order> orderCreated() {
		return order -> {
			receivedOrderConfirmation(order);
			log.info("order Confirmattion received from  order service");
			

			EmailDetails details = new EmailDetails("hanumaramavath9010@gmail.com", "order is created", "Hey,This mail regarding confirmation");
			/* String status = emailService.sendSimpleMail(details); */
			PdfGenerator pdfGenerator=new PdfGenerator();
			byte[] pdfData = null;
			try {
				pdfData = pdfGenerator.generatePdf(order);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			if (pdfData != null) {	
				String accountId=order.getAccountId();
			    try (FileOutputStream outputStream = new FileOutputStream(accountId+"_"+"invoice.pdf")) {
			        outputStream.write(pdfData);
			    } catch (IOException e) {
			        // Handle file writing exception
			        System.err.println("Error writing PDF to file: " + e.getMessage());
			    }
			} else {
			  
			    System.out.println("Failed to generate PDF.");
			}
			System.out.println("---order Confirmattion received from  order service----" + order.toString());

			return order;
		};
	}

	public void receivedOrderConfirmation(Order order) {
		/*
		 * System.out.println("---order Confirmattion received from  order service----"
		 * );
		 */		boolean isSend = streamBridge.send("orderconfirmed-in-0", order);
		/*
		 * if (isSend)
		 * System.out.println("---order Confirmattion send to order service----");
		 */
	}
}
