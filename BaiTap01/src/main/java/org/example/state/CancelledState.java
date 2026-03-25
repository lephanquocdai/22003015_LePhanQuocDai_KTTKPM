package org.example.state;

import org.example.context.OrderContext;

public class CancelledState implements OrderState {
    @Override
    public String handle(OrderContext context) {
        System.out.println("Hủy đơn hàng và hoàn tiền...");
        context.getPaymentStrategy().refund();
        return "Hủy";
    }
}
