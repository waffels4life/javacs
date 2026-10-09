package com.javacs.design.examples.notification.channels;

import com.javacs.design.examples.notification.NotificationCenter;

public class EmailNotification implements NotificationCenter {

    @Override public void send(String message) {
        System.out.println(
                "Email: " + message
        );
    }
}
