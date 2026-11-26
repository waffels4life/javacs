package com.javacs.projects.paymentFactory.strip;

import com.javacs.projects.paymentFactory.RefundProcess;

public class StripRefundProcess implements RefundProcess {
    @Override public void refund(int amount) {
        System.out.println(
                "Refunding " + amount + " with Strip."
        );
    }
}
