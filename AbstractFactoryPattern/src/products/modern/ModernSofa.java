package products.modern;
import products.Sofa;

public class ModernSofa implements Sofa {
    @Override
    public void lieOn() { System.out.println("Nằm trên Sofa Hiện đại: Chất liệu vải nỉ xám."); }
    @Override
    public boolean isComfortable() { return true; }
}