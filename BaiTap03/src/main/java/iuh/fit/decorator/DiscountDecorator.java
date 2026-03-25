package iuh.fit.decorator;

import iuh.fit.strategy.PaymentStrategy;

public class DiscountDecorator extends PaymentDecorator {
    private double discount;

    public DiscountDecorator(PaymentStrategy payment, double discount) {
        super(payment);
        this.discount = discount;
    }

    @Override
    public void pay(double amount) {
        double total = amount - discount;
        System.out.println("Áp dụng mã giảm giá: " + discount + " VND");
        super.pay(total);
    }
}