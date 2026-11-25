package com.javacs.oop.abstracts.ex2;

public class EmailChannel extends NotificationChannel {
    @Override public void send(String message) {
        System.out.println("Sending via Email.");
    }
}
