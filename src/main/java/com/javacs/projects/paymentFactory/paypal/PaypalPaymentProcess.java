package com.javacs.projects.paymentFactory.paypal;

import com.javacs.projects.paymentFactory.PaymentProcess;

public class PaypalPaymentProcess implements PaymentProcess {
    @Override public void pay(int amount) {
        System.out.println(
                "Paying " + amount + " with PayPal."
        );
    }
}
