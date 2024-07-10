package com.Mercurius.Notifications.service.impl;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.Mercurius.Notifications.entity.EmailDetails;
import com.Mercurius.Notifications.service.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {
	@Autowired
	private JavaMailSender javaMailSender;

	public EmailServiceImpl(JavaMailSender javaMailSender) {
		super();
		this.javaMailSender = javaMailSender;
	}

	/*
	 * @Value("${spring.mail.username}") private String sender;
	 */

	@Override
	public String sendSimpleMail(EmailDetails details) {

		try {

			SimpleMailMessage mailMessage = new SimpleMailMessage();

			System.out.println("Details are: " + details.toString());
			mailMessage.setFrom("netfreakerr@gmail.com");

			mailMessage.setTo("hanumaramavath9010@gmail.com");
			mailMessage.setText("This is sample message");
			mailMessage.setSubject("just a message");

//			javaMailSender.send(mailMessage);
			return "Mail Sent Successfully...";
		} catch (Exception e) {
			return "Error while Sending Mail:\n" + e.getMessage();
		}
	}

//	@Override
//	public void sendEmailWithAttachment(EmailDetails details, String invoiceName)
//			throws MessagingException, IOException {
//		
//			MimeMessage message = javaMailSender.createMimeMessage();
//			MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//			helper.setTo(details.getRecipient());
//			helper.setSubject(details.getSubject());
//			helper.setText(details.getMsgBody());
//
////			FileSystemResource file = new FileSystemResource(new File(invoiceName));
//			File invoiceFile = new File(invoiceName);
//			if (invoiceFile.exists()) {
//			    try (FileSystemResource file = new FileSystemResource(invoiceFile)) {
//			        helper.addAttachment(invoiceName, file);
//			    }
//			} else {
//			    // Handle missing file scenario (e.g., log error or throw exception)
//			}
//
//			helper.addAttachment(invoiceName, file);
//
//			javaMailSender.send(message);
//		 
//		
//	}

    public void sendEmailWithAttachment(EmailDetails details, String invoiceName) throws MessagingException, IOException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        try {
            helper.setTo(details.getRecipient());
            helper.setSubject(details.getSubject());
            helper.setText(details.getMsgBody());

        
            File invoiceFile = new File(invoiceName);
            if (invoiceFile.exists()) {
                FileSystemResource fileResource = new FileSystemResource(invoiceFile);
                helper.addAttachment(invoiceFile.getName(), fileResource);
            } else {
                System.err.println("Invoice file not found: " + invoiceName);
            }

           
            javaMailSender.send(message);
            System.out.println("Email sent successfully with attachment: " + invoiceName);

        } catch (Exception e) {
            System.err.println("Failed to send email with attachment: " + e.getMessage());
            throw e;
        }
    }

}
