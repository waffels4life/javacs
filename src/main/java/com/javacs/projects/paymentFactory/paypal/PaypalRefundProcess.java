package com.javacs.projects.paymentFactory.paypal;

import com.javacs.projects.paymentFactory.RefundProcess;

public class PaypalRefundProcess implements RefundProcess {
    @Override public void refund(int amount) {
        System.out.println(
                "Refunding " + amount + "with PayPal."
        );
    }
}
