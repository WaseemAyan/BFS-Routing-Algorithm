import java.awt.*; // For Point and Color classes

// Represents a graph node with an ID, position, color, and diameter
public class Node {
    public String id;               // Node label (e.g., "A", "B", etc.)
    public int x, y;                // Position of the node on the panel
    public Color color = Color.BLUE; // Default color (blue)
    public static final int DIAMETER = 50; // Fixed size of the node circle

    // Constructor to initialize node with ID and position
    public Node(String id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    // Checks if a given point lies inside the node's circular boundary
    public boolean contains(Point p) {
        return Math.pow(p.x - x, 2) + Math.pow(p.y - y, 2) <= Math.pow(DIAMETER / 2, 2);
    }
}
