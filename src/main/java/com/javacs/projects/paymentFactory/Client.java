package com.javacs.projects.paymentFactory;

import com.javacs.projects.paymentFactory.factory.PaymentFactory;
import com.javacs.projects.paymentFactory.factory.PaypalPaymentFactory;
import com.javacs.projects.paymentFactory.factory.StripPaymentFactory;
import com.javacs.projects.paymentFactory.service.PaymentService;

public class Client {
    public static void main(String[] args) {
        PaymentFactory paymentFactory = new StripPaymentFactory();
        PaymentService paymentService = new PaymentService(paymentFactory);

        paymentService.pay(50);
        paymentService.refund(100);
    }
}
