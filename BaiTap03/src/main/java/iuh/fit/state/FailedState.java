package iuh.fit.state;

import iuh.fit.context.PaymentContext;

public class FailedState implements PaymentState {
    @Override
    public void process(PaymentContext context, double amount) {
        System.out.println("Trạng thái: Giao dịch thất bại");
    }
}