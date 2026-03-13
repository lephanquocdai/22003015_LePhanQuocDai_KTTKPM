package client;
import factories.FurnitureFactory;
import products.*;

public class InteriorDesigner {
    private Chair chair;
    private Sofa sofa;
    private CoffeeTable table;

    // Client chỉ làm việc với Interface, không quan tâm class cụ thể là gì
    public InteriorDesigner(FurnitureFactory factory) {
        this.chair = factory.createChair();
        this.sofa = factory.createSofa();
        this.table = factory.createCoffeeTable();
    }

    public void decorate() {
        System.out.println("--- Bắt đầu setup nội thất cho căn phòng ---");
        chair.sitOn();
        sofa.lieOn();
        table.putCoffee();
        System.out.println("--- Hoàn tất trang trí ---\n");
    }
}