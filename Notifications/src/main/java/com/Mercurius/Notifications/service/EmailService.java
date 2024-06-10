package com.Mercurius.Notifications.service;

import com.Mercurius.Notifications.entity.EmailDetails;

public interface EmailService {
	String sendSimpleMail(EmailDetails details);

	String sendMailWithAttachment(EmailDetails details);
}
