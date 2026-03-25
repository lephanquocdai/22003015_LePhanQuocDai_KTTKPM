package iuh.fit.context;

import iuh.fit.state.PaymentState;
import iuh.fit.state.PendingState;
import iuh.fit.strategy.PaymentStrategy;

public class PaymentContext {
    private PaymentState state;
    private PaymentStrategy strategy;

    public PaymentContext(PaymentStrategy strategy) {
        this.strategy = strategy;
        this.state = new PendingState(); // trạng thái mặc định
    }

    public void setState(PaymentState state) {
        this.state = state;
    }

    public void process(double amount) {
        strategy.pay(amount);
        state.process(this, amount);
    }
}