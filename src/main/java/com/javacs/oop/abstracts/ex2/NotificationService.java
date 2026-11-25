package com.javacs.oop.abstracts.ex2;

import java.util.Objects;

public class NotificationService {

    private final NotificationChannel notificationChannel;

    public NotificationService(NotificationChannel notificationChannel) {
        Objects.requireNonNull(notificationChannel);
        this.notificationChannel = notificationChannel;
    }

    public void send() {
        notificationChannel.send("");
    }
}
