package com.reddit.clone.project.service.impl;

import com.reddit.clone.project.model.NotificationEmail;

public interface MailService {
	void sendMail(NotificationEmail notificationEmail);
}
