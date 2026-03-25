package org.example.decorator;

import org.example.model.Product;

public interface TaxComponent {
    double calculate(Product product);
}