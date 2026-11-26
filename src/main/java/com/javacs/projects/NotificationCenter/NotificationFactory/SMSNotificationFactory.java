package com.javacs.projects.NotificationCenter.NotificationFactory;

import com.javacs.projects.NotificationCenter.NotificationProcess;
import com.javacs.projects.NotificationCenter.sms.SMSNotificationProcess;

public class SMSNotificationFactory implements NotificationFactory {
    @Override public NotificationProcess createNotificationProcess() {
        return new SMSNotificationProcess();
    }
}
