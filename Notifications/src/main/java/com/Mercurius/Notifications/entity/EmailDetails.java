package com.Mercurius.Notifications.entity;

import jakarta.validation.constraints.Email;

public class EmailDetails {
	@Email
	private String recipient;
	private String msgBody;
	private String subject;
	private String attachment;

	public EmailDetails(String recipient, String subject, String msgBody) {
		super();
		this.recipient = recipient;
		this.subject = subject;
		this.msgBody = msgBody;

	}

	public EmailDetails(String recipient, String msgBody, String subject, String attachment) {
		super();
		this.recipient = recipient;
		this.msgBody = msgBody;
		this.subject = subject;
		this.attachment = attachment;
	}

	public EmailDetails() {
		super();
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getMsgBody() {
		return msgBody;
	}

	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	@Override
	public String toString() {
		return "EmailDetails [recipient=" + recipient + ", msgBody=" + msgBody + ", subject=" + subject
				+ ", attachment=" + attachment + "]";
	}

}
