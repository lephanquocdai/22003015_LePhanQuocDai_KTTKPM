package org.example.strategy;

import org.example.model.Product;

public class VATStrategy implements TaxStrategy {

    @Override
    public double calculate(Product product) {
        return product.getPrice() * 0.1; // 10%
    }
}