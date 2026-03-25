package iuh.fit.decorator;

import iuh.fit.strategy.PaymentStrategy;

public class ProcessingFeeDecorator extends PaymentDecorator {
    private double fee;

    public ProcessingFeeDecorator(PaymentStrategy payment, double fee) {
        super(payment);
        this.fee = fee;
    }

    @Override
    public void pay(double amount) {
        double total = amount + fee;
        System.out.println("Thêm phí xử lý: " + fee + " VND");
        super.pay(total);
    }
}