package com.javacs.projects.NotificationCenter.email;

import com.javacs.projects.NotificationCenter.NotificationProcess;

public class EmailNotificationProcess implements NotificationProcess {
    @Override public void send(String message) {
        System.out.println(
                "[From E-mail]: " + message
        );
    }
}
