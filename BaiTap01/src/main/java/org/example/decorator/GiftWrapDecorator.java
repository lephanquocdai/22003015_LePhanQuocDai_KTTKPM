package org.example.decorator;

public class GiftWrapDecorator extends OrderDecorator {

    public GiftWrapDecorator(OrderService orderService) {
        super(orderService);
    }

    @Override
    public void process() {
        super.process();
        System.out.println("Thêm gói quà.");
    }
}