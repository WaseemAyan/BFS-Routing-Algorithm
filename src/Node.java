import java.awt.*;

public class Node {
    public String id;
    public int x, y;
    public Color color = Color.BLUE;
    public static final int DIAMETER = 50;

    public Node(String id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public boolean contains(Point p) {
        return Math.pow(p.x - x, 2) + Math.pow(p.y - y, 2) <= Math.pow(DIAMETER / 2, 2);
    }
}