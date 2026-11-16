package com.javacs.projects.Omail;

import java.io.IOException;

public class OMail {
    public static void main(String[] args) {

        SmtpClient smtpClient = new SmtpClient("smtp.gmail.com");

        try {
            smtpClient.createSMTP();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
