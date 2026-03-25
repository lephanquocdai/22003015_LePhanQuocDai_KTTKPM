package org.example.decorator;

public abstract class OrderDecorator implements OrderService {

    protected OrderService orderService;

    public OrderDecorator(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void process() {
        orderService.process();
    }
}