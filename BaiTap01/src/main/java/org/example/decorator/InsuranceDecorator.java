package org.example.decorator;

public class InsuranceDecorator extends OrderDecorator {

    public InsuranceDecorator(OrderService orderService) {
        super(orderService);
    }

    @Override
    public void process() {
        super.process();
        System.out.println("Thêm bảo hiểm đơn hàng.");
    }
}