package org.example.state;

import org.example.decorator.ExciseDecorator;
import org.example.decorator.TaxComponent;
import org.example.decorator.VATDecorator;

public class LuxuryState implements ProductState {

    @Override
    public TaxComponent applyTax(TaxComponent component) {
        component = new VATDecorator(component);
        component = new ExciseDecorator(component);
        return component;
    }
}