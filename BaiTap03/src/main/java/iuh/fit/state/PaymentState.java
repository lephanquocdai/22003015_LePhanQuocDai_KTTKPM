package iuh.fit.state;

import iuh.fit.context.PaymentContext;

public interface PaymentState {
    void process(PaymentContext context, double amount);
}