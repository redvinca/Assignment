package com.redvinca.assignment.ecom_backend.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service("emailService")
public class EmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;


	public void sendEmail(SimpleMailMessage email) {
		javaMailSender.send(email);
	}

	public void sendEmail(MimeMessage message) {
		javaMailSender.send(message);
	}

	public MimeMessage createMimeMessage() {
		return javaMailSender.createMimeMessage();
	}
}
