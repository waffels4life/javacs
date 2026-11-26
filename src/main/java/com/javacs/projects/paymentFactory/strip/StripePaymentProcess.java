package com.javacs.projects.paymentFactory.strip;

import com.javacs.projects.paymentFactory.PaymentProcess;

public class StripePaymentProcess implements PaymentProcess {
    @Override public void pay(int amount) {
        System.out.println(
                "Paying " + amount + " with Strip."
        );
    }
}
