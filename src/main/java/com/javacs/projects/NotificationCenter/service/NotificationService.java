package com.javacs.projects.NotificationCenter.service;

import com.javacs.projects.NotificationCenter.NotificationFactory.NotificationFactory;
import com.javacs.projects.NotificationCenter.NotificationProcess;

public class NotificationService {
    private final NotificationProcess notificationProcess;

    public NotificationService(NotificationFactory notificationFactory) {
        this.notificationProcess = notificationFactory.createNotificationProcess();
    }

    public void send (String message) {
        notificationProcess.send(message);
    }
}
