package org.example;

import org.example.context.TaxContext;
import org.example.model.Product;
import org.example.state.LuxuryState;
import org.example.state.NormalState;
import org.example.state.TaxExemptState;

public class Main {

    public static void main(String[] args) {

        Product normalProduct = new Product("Áo", 100);
        Product luxuryProduct = new Product("Đồng hồ", 1000);
        Product freeProduct = new Product("Sách", 50);

        TaxContext context = new TaxContext();

        // Normal
        context.setState(new NormalState());
        System.out.println("Thuế sản phẩm thường: "
                + context.calculateTax(normalProduct));

        // Luxury
        context.setState(new LuxuryState());
        System.out.println("Thuế sản phẩm xa xỉ: "
                + context.calculateTax(luxuryProduct));

        // Tax Free
        context.setState(new TaxExemptState());
        System.out.println("Thuế sản phẩm miễn thuế: "
                + context.calculateTax(freeProduct));
    }
}