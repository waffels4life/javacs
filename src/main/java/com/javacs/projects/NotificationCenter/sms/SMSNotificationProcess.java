package com.javacs.projects.NotificationCenter.sms;

import com.javacs.projects.NotificationCenter.NotificationProcess;

public class SMSNotificationProcess implements NotificationProcess {
    @Override public void send(String message) {
        System.out.println(
                "[From SMS]: " + message
        );
    }
}
