package org.example.strategy.payment;

public class EWalletPayment implements PaymentStrategy {
    @Override
    public void refund() {
        System.out.println("Hoàn tiền qua ví điện tử.");
    }
}