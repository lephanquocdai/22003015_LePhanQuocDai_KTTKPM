package org.example.strategy.payment;

public class CreditCardPayment implements PaymentStrategy {
    @Override
    public void refund() {
        System.out.println("Hoàn tiền qua thẻ tín dụng.");
    }
}