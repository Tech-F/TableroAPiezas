import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Obcion2 extends JPanel {

    private static final long serialVersionUID = 1L;
    private ArrayList<Rectangle> itemList;
    private ArrayList<Rectangle> packedList;
    private Rectangle bin;

    public Obcion2(Rectangle[] items, Rectangle bin) {
        this.itemList = new ArrayList<Rectangle>();
        for (Rectangle r : items) {
            itemList.add(r);
        }
        Collections.sort(itemList, new Comparator<Rectangle>() {
            public int compare(Rectangle r1, Rectangle r2) {
                return Math.max(r2.width, r2.height) - Math.max(r1.width, r1.height);
            }
        });
        this.packedList = new ArrayList<Rectangle>();
        this.packedList.add(new Rectangle(0, 0, bin.width, bin.height));
        this.bin = bin;
    }

    public Rectangle[] pack() {
        for (Rectangle item : itemList) {
            boolean placed = false;
            for (int i = 0; i < packedList.size(); i++) {
                Rectangle node = packedList.get(i);
                if (node.width >= item.width && node.height >= item.height) {
                    item.x = node.x;
                    item.y = node.y;
                    packedList.remove(i);
                    if (item.width < node.width) {
                        packedList.add(new Rectangle(node.x + item.width, node.y, node.width - item.width, item.height));
                    }
                    if (item.height < node.height) {
                        packedList.add(new Rectangle(node.x, node.y + item.height, item.width, node.height - item.height));
                    }
                    packedList.add(item);
                    placed = true;
                    break;
                }
            }
            if (!placed) {
                return null;
            }
        }
        Rectangle[] result = new Rectangle[itemList.size()];
        return itemList.toArray(result);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(bin);
        g2.setColor(Color.BLACK);
        g2.draw(bin);
        for (Rectangle r : packedList) {
            g2.setColor(Color.GREEN);
            g2.fill(r);
            g2.setColor(Color.BLACK);
            g2.draw(r);
        }
        for (Rectangle r : itemList) {
            g2.setColor(Color.RED);
            g2.fill(r);
            g2.setColor(Color.BLACK);
            g2.draw(r);
        }
    }

    public static void main(String[] args) {
        Rectangle bin = new Rectangle(0, 0, 800, 600);
        Rectangle[] items = new Rectangle[]{
                new Rectangle(100, 100, 200, 150),
                new Rectangle(300, 300, 100, 100),
                new Rectangle(200, 200, 150, 150),
                new Rectangle(400, 200, 150, 100),
                new Rectangle(500, 400, 100, 100),
                new Rectangle(600, 300, 100, 200)
        };

        Obcion2 bp = new Obcion2(items, bin);
        Rectangle[] packedItems = bp.pack();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(800, 600));
        frame.add(bp);
        frame.setVisible(true);

        if (packedItems != null) {
            System.out.println("Items packed successfully:");
            for (Rectangle r : packedItems) {
                System.out.println(" - " + r);
            }
        } else {
            System.out.println("Error: Failed to pack items");
        }
    }
}
/*

Este código utiliza la clase `JPanel` para crear un panel donde se dibujarán las figuras geométricas. Se define el método `paintComponent` para dibujar el rectángulo contenedor, las figuras geométricas empaquetadas y las figuras geométricas sin empaquetar. 

En el método `main`, se crea un objeto `Obcion2` con los parámetros del rectángulo contenedor y las figuras geométricas a empaquetar. Se llama al método `pack` para obtener las figuras geométricas empaquetadas y se muestra el resultado por consola.

Finalmente, se crea un objeto `JFrame` que contiene el panel creado y se muestra en pantalla.

Este es solo un ejemplo básico de cómo se podría mostrar el resultado del empaquetado. Es posible modificar el código para hacer que la visualización sea más detallada o para agregar más información sobre el proceso de empaquetado.


 */