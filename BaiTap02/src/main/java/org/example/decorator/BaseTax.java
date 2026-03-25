package org.example.decorator;

import org.example.model.Product;

public class BaseTax implements TaxComponent {

    @Override
    public double calculate(Product product) {
        return 0;
    }
}