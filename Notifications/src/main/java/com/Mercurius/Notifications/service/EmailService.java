package com.Mercurius.Notifications.service;

import java.io.IOException;

import com.Mercurius.Notifications.entity.EmailDetails;

import jakarta.mail.MessagingException;

public interface EmailService {
	String sendSimpleMail(EmailDetails details);
	public void sendEmailWithAttachment(EmailDetails details,String invoiceName) throws MessagingException, IOException;
}
