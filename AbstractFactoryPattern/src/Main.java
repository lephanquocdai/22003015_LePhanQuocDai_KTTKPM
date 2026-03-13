import client.InteriorDesigner;
import factories.*;

public class Main {
    public static void main(String[] args) {
        // 1. Setup phòng theo phong cách Hiện Đại
        System.out.println("KHÁCH HÀNG A CHỌN PHONG CÁCH HIỆN ĐẠI:");
        FurnitureFactory modernFactory = new ModernFurnitureFactory();
        InteriorDesigner room1 = new InteriorDesigner(modernFactory);
        room1.decorate();

        // 2. Setup phòng theo phong cách Cổ Điển
        System.out.println("KHÁCH HÀNG B CHỌN PHONG CÁCH CỔ ĐIỂN:");
        FurnitureFactory victorianFactory = new VictorianFurnitureFactory();
        InteriorDesigner room2 = new InteriorDesigner(victorianFactory);
        room2.decorate();
    }
}