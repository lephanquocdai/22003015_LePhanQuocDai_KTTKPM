package org.example.decorator;

import org.example.model.Product;
import org.example.strategy.ExciseTaxStrategy;

public class ExciseDecorator extends TaxDecorator {

    private ExciseTaxStrategy strategy = new ExciseTaxStrategy();

    public ExciseDecorator(TaxComponent component) {
        super(component);
    }

    @Override
    public double calculate(Product product) {
        return super.calculate(product) + strategy.calculate(product);
    }
}