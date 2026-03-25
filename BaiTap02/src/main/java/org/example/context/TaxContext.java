package org.example.context;

import org.example.decorator.BaseTax;
import org.example.decorator.TaxComponent;
import org.example.model.Product;
import org.example.state.ProductState;

public class TaxContext {

    private ProductState state;

    public void setState(ProductState state) {
        this.state = state;
    }

    public double calculateTax(Product product) {
        TaxComponent component = new BaseTax();

        component = state.applyTax(component);

        return component.calculate(product);
    }
}