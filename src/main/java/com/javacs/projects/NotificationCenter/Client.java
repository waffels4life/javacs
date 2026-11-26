package com.javacs.projects.NotificationCenter;

import com.javacs.projects.NotificationCenter.NotificationFactory.EmailNotificationFactory;
import com.javacs.projects.NotificationCenter.NotificationFactory.NotificationFactory;
import com.javacs.projects.NotificationCenter.NotificationFactory.SMSNotificationFactory;
import com.javacs.projects.NotificationCenter.service.NotificationService;

public class Client {
    public static void main(String[] args) {

        NotificationFactory notificationFactory = new SMSNotificationFactory();
        NotificationService notificationService = new NotificationService(notificationFactory);

        notificationService.send("Hello");
    }
}
