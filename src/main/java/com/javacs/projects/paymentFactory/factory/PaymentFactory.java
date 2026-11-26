package com.javacs.projects.paymentFactory.factory;

import com.javacs.projects.paymentFactory.PaymentProcess;
import com.javacs.projects.paymentFactory.RefundProcess;

public interface PaymentFactory {

    PaymentProcess createPaymentProcess();

    RefundProcess createRefundProcess();
}
