package products.victorian;
import products.CoffeeTable;

public class VictorianCoffeeTable implements CoffeeTable {
    @Override
    public void putCoffee() { System.out.println("Đặt cafe lên bàn Cổ điển: Gỗ sồi chạm khắc tinh xảo."); }
    @Override
    public String getSize() { return "120x120cm"; }
}