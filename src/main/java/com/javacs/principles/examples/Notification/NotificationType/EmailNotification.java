package com.javacs.principles.examples.Notification.NotificationType;

import com.javacs.principles.examples.Notification.NotificationCenter;

public class EmailNotification implements NotificationCenter {

    @Override public void send(String message) {
        System.out.println(
                "Email: " + message
        );
    }
}
