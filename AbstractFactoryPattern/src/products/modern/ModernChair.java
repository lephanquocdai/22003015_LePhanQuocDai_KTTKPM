package products.modern;
import products.Chair;

public class ModernChair implements Chair {
    @Override
    public void sitOn() { System.out.println("Ngồi trên ghế Hiện đại: Rất thoải mái và tối giản."); }
    @Override
    public boolean hasLegs() { return true; }
}