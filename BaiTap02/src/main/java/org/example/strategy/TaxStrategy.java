package org.example.strategy;

import org.example.model.Product;

public interface TaxStrategy {
    double calculate(Product product);
}