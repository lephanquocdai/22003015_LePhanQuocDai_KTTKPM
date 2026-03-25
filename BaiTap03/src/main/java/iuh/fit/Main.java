package iuh.fit;

import iuh.fit.context.PaymentContext;
import iuh.fit.decorator.DiscountDecorator;
import iuh.fit.decorator.ProcessingFeeDecorator;
import iuh.fit.strategy.CreditCardPayment;
import iuh.fit.strategy.PayPalPayment;
import iuh.fit.strategy.PaymentStrategy;

public class Main {
    public static void main(String[] args) {
        double amount = 100000;

        // Thanh toán thẻ tín dụng + phí xử lý + giảm giá
        PaymentStrategy creditCard = new CreditCardPayment();
        creditCard = new ProcessingFeeDecorator(creditCard, 5000);
        creditCard = new DiscountDecorator(creditCard, 10000);

        PaymentContext context1 = new PaymentContext(creditCard);
        context1.process(amount);

        System.out.println("-----");

        // Thanh toán PayPal chỉ với phí xử lý
        PaymentStrategy paypal = new PayPalPayment();
        paypal = new ProcessingFeeDecorator(paypal, 3000);

        PaymentContext context2 = new PaymentContext(paypal);
        context2.process(amount);
    }
}