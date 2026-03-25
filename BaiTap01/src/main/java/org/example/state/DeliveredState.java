package org.example.state;

import org.example.context.OrderContext;

public class DeliveredState implements OrderState {
    @Override
    public String handle(OrderContext context) {
        System.out.println("Cập nhật trạng thái đơn hàng là đã giao.");
        return "Đã giao";
    }
}