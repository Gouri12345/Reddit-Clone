package com.reddit.clone.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.reddit.clone.project.model.NotificationEmail;
import com.reddit.clone.project.service.MailService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MailServiceImpl implements MailService{

	private final JavaMailSender mailSender;
	@Autowired
    MailContentBuilder mailContentBuilder;
	@Async
	public void sendMail(NotificationEmail notificationEmail) {
		  MimeMessagePreparator messagePreparator = mimeMessage -> {
	            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
	            messageHelper.setFrom("springreddit@email.com");
	            messageHelper.setTo(notificationEmail.getRecipient());
	            messageHelper.setSubject(notificationEmail.getSubject());
	            messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
	        };
	        
	        try {
	        	mailSender.send(messagePreparator);
	        }
	        catch (MailException e) {
				// TODO: handle exception
			}
	}

}
