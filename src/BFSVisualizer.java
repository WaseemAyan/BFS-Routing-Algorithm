import javax.swing.*;   // Imports for building GUI components
import java.awt.*;      // Imports for layout and color-related classes

// Main class that extends JFrame to create a windowed GUI application
public class BFSVisualizer extends JFrame {

    // Constructor to set up the GUI
    public BFSVisualizer() {
        setTitle("BFS Pathfinding Visualizer"); // Sets the window title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit the app when window is closed
        setLayout(new BorderLayout()); // Sets the layout manager for the frame

        GraphPanel graphPanel = new GraphPanel(); // Custom panel where the BFS graph will be drawn
        add(graphPanel, BorderLayout.CENTER); // Adds the graph panel to the center of the frame

        // Creates a footer label to display description
        JLabel footer = new JLabel("Breadth-First Search Visualization • Path shown in green", SwingConstants.CENTER);
        footer.setFont(new Font("SansSerif", Font.ITALIC, 14)); // Sets font style and size
        footer.setForeground(new Color(70, 70, 70)); // Sets font color
        footer.setBackground(new Color(220, 230, 240)); // Sets background color of the label
        footer.setOpaque(true); // Makes the background color visible
        add(footer, BorderLayout.SOUTH); // Adds the label to the bottom (south) of the frame

        pack(); // Adjusts window size to fit all components
        setLocationRelativeTo(null); // Centers the window on the screen
    }

    // Main method to start the application
    public static void main(String[] args) {
        // Schedules this GUI to be created on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new BFSVisualizer().setVisible(true); // Creates and shows the GUI
        });
    }
}
