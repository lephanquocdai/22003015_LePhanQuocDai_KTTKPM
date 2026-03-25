package org.example.context;


import org.example.state.CancelledState;
import org.example.state.NewOrderState;
import org.example.state.OrderState;
import org.example.strategy.payment.PaymentStrategy;
import org.example.strategy.shipping.ShippingStrategy;

public class OrderContext {

    private OrderState state;
    private ShippingStrategy shippingStrategy;
    private PaymentStrategy paymentStrategy;

    public OrderContext(ShippingStrategy shippingStrategy, PaymentStrategy paymentStrategy) {
        this.state = new NewOrderState();
        this.shippingStrategy = shippingStrategy;
        this.paymentStrategy = paymentStrategy;
    }

    public String process() {
        return state.handle(this);
    }

    public void cancel() {
        this.state = new CancelledState();
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public ShippingStrategy getShippingStrategy() {
        return shippingStrategy;
    }

    public PaymentStrategy getPaymentStrategy() {
        return paymentStrategy;
    }
}