package org.example.decorator;

import org.example.model.Product;

public abstract class TaxDecorator implements TaxComponent {

    protected TaxComponent component;

    public TaxDecorator(TaxComponent component) {
        this.component = component;
    }

    @Override
    public double calculate(Product product) {
        return component.calculate(product);
    }
}