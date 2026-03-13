package products.modern;
import products.CoffeeTable;

public class ModernCoffeeTable implements CoffeeTable {
    @Override
    public void putCoffee() { System.out.println("Đặt cafe lên bàn Hiện đại: Mặt kính cường lực."); }
    @Override
    public String getSize() { return "80x80cm"; }
}