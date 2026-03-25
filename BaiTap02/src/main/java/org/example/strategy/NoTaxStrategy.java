package org.example.strategy;

import org.example.model.Product;

public class NoTaxStrategy implements TaxStrategy {

    @Override
    public double calculate(Product product) {
        return 0;
    }
}