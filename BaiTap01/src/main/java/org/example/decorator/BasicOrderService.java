package org.example.decorator;

public class BasicOrderService implements OrderService {

    @Override
    public void process() {
        System.out.println("Xử lý đơn hàng cơ bản");
    }
}