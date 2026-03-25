package iuh.fit.decorator;

import iuh.fit.strategy.PaymentStrategy;

public abstract class PaymentDecorator implements PaymentStrategy {
    protected PaymentStrategy wrappedPayment;

    public PaymentDecorator(PaymentStrategy payment) {
        this.wrappedPayment = payment;
    }

    @Override
    public void pay(double amount) {
        wrappedPayment.pay(amount);
    }
}