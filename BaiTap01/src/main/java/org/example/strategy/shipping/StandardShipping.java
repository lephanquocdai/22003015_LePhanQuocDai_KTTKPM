package org.example.strategy.shipping;

public class StandardShipping implements ShippingStrategy {
    @Override
    public void ship() {
        System.out.println("Giao hàng tiêu chuẩn.");
    }
}