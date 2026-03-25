package org.example.state;

import org.example.context.OrderContext;

public interface OrderState {
    String handle(OrderContext context);
}