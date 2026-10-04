package com.javacs.principles.examples.Notification.NotificationEngin;

import com.javacs.principles.examples.Notification.NotificationCenter;

import java.util.Objects;

public class NotificationService {

    private NotificationCenter notificationCenter = null;

    public NotificationService() {}

    public void notify(NotificationCenter notificationCenter) {
        this.notificationCenter = notificationCenter;
        service("Working");
    }

    public void service(String message) {
        Objects.requireNonNull(notificationCenter);
        notificationCenter.send(message);
    }
}
