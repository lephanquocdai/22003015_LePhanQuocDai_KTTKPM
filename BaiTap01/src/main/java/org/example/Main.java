package org.example;

import org.example.context.OrderContext;
import org.example.decorator.BasicOrderService;
import org.example.decorator.InsuranceDecorator;
import org.example.decorator.GiftWrapDecorator;
import org.example.decorator.OrderService;
import org.example.strategy.payment.EWalletPayment;
import org.example.strategy.payment.PaymentStrategy;
import org.example.strategy.shipping.ExpressShipping;
import org.example.strategy.shipping.ShippingStrategy;

public class Main {
    public static void main(String[] args) {

        // Strategy
        ShippingStrategy shipping = new ExpressShipping();
        PaymentStrategy payment = new EWalletPayment();

        // State
        OrderContext order = new OrderContext(shipping, payment);

        System.out.println("Trạng thái: " + order.process());
        System.out.println();

        System.out.println("Trạng thái: " + order.process());
        System.out.println();

        System.out.println("Trạng thái: " + order.process());
        System.out.println();

        System.out.println("-----");

        order.cancel();
        System.out.println("Trạng thái: " + order.process());

        System.out.println("-----");

        // Decorator demo
        OrderService service = new BasicOrderService();
        service = new GiftWrapDecorator(service);
        service = new InsuranceDecorator(service);

        service.process();
    }
}