import javax.swing.*;
import java.awt.*;

public class BFSVisualizer extends JFrame {
    public BFSVisualizer() {
        setTitle("BFS Pathfinding Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        GraphPanel graphPanel = new GraphPanel();
        add(graphPanel, BorderLayout.CENTER);

        JLabel footer = new JLabel("Breadth-First Search Visualization • Path shown in green", SwingConstants.CENTER);
        footer.setFont(new Font("SansSerif", Font.ITALIC, 14));
        footer.setForeground(new Color(70, 70, 70));
        footer.setBackground(new Color(220, 230, 240));
        footer.setOpaque(true);
        add(footer, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BFSVisualizer().setVisible(true);
        });
    }
}