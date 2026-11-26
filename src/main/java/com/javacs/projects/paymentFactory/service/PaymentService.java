package com.javacs.projects.paymentFactory.service;

import com.javacs.projects.paymentFactory.PaymentProcess;
import com.javacs.projects.paymentFactory.RefundProcess;
import com.javacs.projects.paymentFactory.factory.PaymentFactory;

import java.util.Objects;

public class PaymentService {
    private final PaymentProcess paymentProcess;
    private final RefundProcess refundProcess;

    public PaymentService(PaymentFactory paymentFactory) {
        Objects.requireNonNull(paymentFactory);

        this.paymentProcess = paymentFactory.createPaymentProcess();
        this.refundProcess = paymentFactory.createRefundProcess();
    }

    public void pay(int amount) {
        paymentProcess.pay(amount);
    }

    public void refund(int amount) {
        refundProcess.refund(amount);
    }
}
