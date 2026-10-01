package com.javacs.principles.examples.Notification.NotificationEngin;

import com.javacs.principles.examples.Notification.NotificationCenter;
import com.javacs.principles.examples.Notification.NotificationType.AppNotification;
import com.javacs.principles.examples.Notification.NotificationType.EmailNotification;
import com.javacs.principles.examples.Notification.NotificationType.SMSNotification;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class NotificationRunner {

    private static final NotificationService notificationService = new NotificationService();

    public static void main(String[] args) {

        List<NotificationCenter> notificationCenterList = List.of(
                new SMSNotification(),
                new EmailNotification(),
                new AppNotification()
        );

        for (NotificationCenter notificationCenter : notificationCenterList) {
            notificationService.setNotificationCenter(notificationCenter);
            notificationService.service("Hi :D");
        }
    }
}
