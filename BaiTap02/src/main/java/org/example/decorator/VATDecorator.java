package org.example.decorator;

import org.example.model.Product;
import org.example.strategy.VATStrategy;

public class VATDecorator extends TaxDecorator {

    private VATStrategy vatStrategy = new VATStrategy();

    public VATDecorator(TaxComponent component) {
        super(component);
    }

    @Override
    public double calculate(Product product) {
        return super.calculate(product) + vatStrategy.calculate(product);
    }
}