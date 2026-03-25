package org.example.state;

import org.example.context.OrderContext;

public class ProcessingState implements OrderState {
    @Override
    public String handle(OrderContext context) {
        System.out.println("Đóng gói và vận chuyển...");
        context.getShippingStrategy().ship();
        context.setState(new DeliveredState());
        return "Đang xử lý";
    }
}
