package com.javacs.design.examples.notification.engine;

import com.javacs.design.examples.notification.NotificationCenter;
import com.javacs.design.examples.notification.channels.AppNotification;
import com.javacs.design.examples.notification.channels.EmailNotification;
import com.javacs.design.examples.notification.channels.SMSNotification;

import java.util.List;

public class NotificationRunner {

    private static final NotificationService notificationService = new NotificationService();

    public static void main(String[] args) {

        List<NotificationCenter> notificationCenterList = List.of(
                new SMSNotification(),
                new EmailNotification(),
                new AppNotification()
        );

        notificationCenterList.forEach(notificationService::notify);
    }
}
