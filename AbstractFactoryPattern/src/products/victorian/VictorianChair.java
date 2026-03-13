package products.victorian;
import products.Chair;

public class VictorianChair implements Chair {
    @Override
    public void sitOn() { System.out.println("Ngồi trên ghế Cổ điển: Chân gỗ uốn lượn, bọc nhung."); }
    @Override
    public boolean hasLegs() { return true; }
}