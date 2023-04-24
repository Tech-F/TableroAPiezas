import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class BinPacking {

    public static Rectangle[] pack(Rectangle[] items, Rectangle bin) {
        ArrayList<Rectangle> itemList = new ArrayList<Rectangle>();
        for(Rectangle r : items) {
            itemList.add(r);
        }
        Collections.sort(itemList, new Comparator<Rectangle>() {
            public int compare(Rectangle r1, Rectangle r2) {
                return Math.max(r2.width, r2.height) - Math.max(r1.width, r1.height);
            }
        });
        ArrayList<Rectangle> packedList = new ArrayList<Rectangle>();
        packedList.add(new Rectangle(0, 0, bin.width, bin.height));
        for(Rectangle item : itemList) {
            boolean placed = false;
            for(int i=0; i<packedList.size(); i++) {
                Rectangle node = packedList.get(i);
                if(node.width >= item.width && node.height >= item.height) {
                    item.x = node.x;
                    item.y = node.y;
                    packedList.remove(i);
                    if(item.width < node.width) {
                        packedList.add(new Rectangle(node.x + item.width, node.y, node.width - item.width, item.height));
                    }
                    if(item.height < node.height) {
                        packedList.add(new Rectangle(node.x, node.y + item.height, item.width, node.height - item.height));
                    }
                    packedList.add(item);
                    placed = true;
                    break;
                }
            }
            if(!placed) {
                return null;
            }
        }
        Rectangle[] result = new Rectangle[itemList.size()];
        return itemList.toArray(result);
    }

    public static void main(String[] args) {
        Rectangle bin = new Rectangle(0, 0, 800, 600);
        Rectangle[] items = new Rectangle[] {
                new Rectangle(100, 100, 200, 150),
                new Rectangle(300, 300, 100, 100),
                new Rectangle(200, 200, 150, 150),
                new Rectangle(400, 200, 150, 100),
                new Rectangle(500, 400, 100, 100),
                new Rectangle(600, 200, 150, 150)
        };
        Rectangle[] result = pack(items, bin);
        if(result != null) {
            System.out.println("Items packed:");
            for(Rectangle r : result) {
                System.out.println(r);
            }
        }
        else {
            System.out.println("Failed to pack items.");
        }
    }
}