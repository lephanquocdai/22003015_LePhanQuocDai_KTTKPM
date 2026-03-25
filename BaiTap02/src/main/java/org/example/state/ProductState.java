package org.example.state;

import org.example.decorator.TaxComponent;

public interface ProductState {
    TaxComponent applyTax(TaxComponent component);
}