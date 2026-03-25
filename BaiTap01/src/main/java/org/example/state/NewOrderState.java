package org.example.state;


import org.example.context.OrderContext;

public class NewOrderState implements OrderState {
    @Override
    public String handle(OrderContext context) {
        System.out.println("Kiểm tra thông tin đơn hàng...");
        context.setState(new ProcessingState());
        return "Mới tạo";
    }
}