package org.example.state;

import org.example.decorator.TaxComponent;

public class TaxExemptState implements ProductState {

    @Override
    public TaxComponent applyTax(TaxComponent component) {
        return component; // không áp thuế
    }
}