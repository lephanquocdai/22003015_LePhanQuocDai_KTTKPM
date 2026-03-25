package org.example.state;

import org.example.decorator.TaxComponent;
import org.example.decorator.VATDecorator;

public class NormalState implements ProductState {

    @Override
    public TaxComponent applyTax(TaxComponent component) {
        return new VATDecorator(component);
    }
}
