package com.javacs.projects.NotificationCenter.NotificationFactory;

import com.javacs.projects.NotificationCenter.NotificationProcess;
import com.javacs.projects.NotificationCenter.email.EmailNotificationProcess;

public class EmailNotificationFactory implements NotificationFactory {
    @Override public NotificationProcess createNotificationProcess() {
        return new EmailNotificationProcess();
    }
}
