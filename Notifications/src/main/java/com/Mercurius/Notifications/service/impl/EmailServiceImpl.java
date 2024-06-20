package com.Mercurius.Notifications.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.Mercurius.Notifications.entity.EmailDetails;
import com.Mercurius.Notifications.service.EmailService;

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
		
			
			mailMessage.setFrom("netfreakerr@gmail.com");
			
			mailMessage.setTo("hanumaramavath9010@gmail.com");
			mailMessage.setText("This is sample message");
			mailMessage.setSubject("just a message");
			
			javaMailSender.send(mailMessage);
			return "Mail Sent Successfully...";
		} catch (Exception e) {
			return "Error while Sending Mail:\n" + e.getMessage();
		}
	}

	@Override
	public String sendMailWithAttachment(EmailDetails details) {
		// TODO Auto-generated method stub
		return null;
	}

}
