package com.javacs.projects.paymentFactory.factory;

import com.javacs.projects.paymentFactory.PaymentProcess;
import com.javacs.projects.paymentFactory.RefundProcess;
import com.javacs.projects.paymentFactory.paypal.PaypalPaymentProcess;
import com.javacs.projects.paymentFactory.paypal.PaypalRefundProcess;

public class PaypalPaymentFactory implements PaymentFactory {
    @Override public PaymentProcess createPaymentProcess() {
        return new PaypalPaymentProcess();
    }

    @Override public RefundProcess createRefundProcess() {
        return new PaypalRefundProcess();
    }
}
