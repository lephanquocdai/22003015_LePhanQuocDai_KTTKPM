package iuh.fit.strategy;

public class CreditCardPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Thanh toán bằng Thẻ tín dụng: " + amount + " VND");
    }
}