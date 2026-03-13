package products.victorian;
import products.Sofa;

public class VictorianSofa implements Sofa {
    @Override
    public void lieOn() { System.out.println("Nằm trên Sofa Cổ điển: Rất quý tộc và nặng nề."); }
    @Override
    public boolean isComfortable() { return false; }
}