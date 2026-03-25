package iuh.fit.state;

import iuh.fit.context.PaymentContext;

public class PendingState implements PaymentState {
    @Override
    public void process(PaymentContext context, double amount) {
        System.out.println("Trạng thái: Chờ xử lý");
        context.setState(new CompletedState()); // chuyển sang trạng thái Completed
        context.process(amount);
    }
}
