package com.javacs.projects.paymentFactory.factory;

import com.javacs.projects.paymentFactory.PaymentProcess;
import com.javacs.projects.paymentFactory.RefundProcess;
import com.javacs.projects.paymentFactory.strip.StripRefundProcess;
import com.javacs.projects.paymentFactory.strip.StripePaymentProcess;

public class StripPaymentFactory implements PaymentFactory {
    @Override public PaymentProcess createPaymentProcess() {
        return new StripePaymentProcess();
    }

    @Override public RefundProcess createRefundProcess() {
        return new StripRefundProcess();
    }
}
