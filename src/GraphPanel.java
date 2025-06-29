import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

class Edge {
    Node from, to;
    Color color = Color.BLACK;

    public Edge(Node from, Node to) {
        this.from = from;
        this.to = to;
    }
}

class Graph {
    List<Node> nodes = new ArrayList<>();
    List<Edge> edges = new ArrayList<>();
    Map<Node, List<Node>> adjList = new HashMap<>();

    public void addNode(Node node) {
        nodes.add(node);
        adjList.put(node, new ArrayList<>());
    }

    public void addEdge(Node a, Node b) {
        edges.add(new Edge(a, b));
        adjList.get(a).add(b);
        adjList.get(b).add(a);
    }

    public List<Node> bfs(Node start, Node end) {
        Map<Node, Node> parentMap = new HashMap<>();
        Queue<Node> queue = new LinkedList<>();
        Set<Node> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);
        parentMap.put(start, null);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (current == end) break;

            for (Node neighbor : adjList.get(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                    parentMap.put(neighbor, current);
                }
            }
        }

        return reconstructPath(parentMap, end);
    }

    private List<Node> reconstructPath(Map<Node, Node> parentMap, Node end) {
        LinkedList<Node> path = new LinkedList<>();
        Node current = end;

        while (current != null) {
            path.addFirst(current);
            current = parentMap.get(current);
        }

        return path;
    }

    public void resetColors() {
        for (Node node : nodes) node.color = Color.BLUE;
        for (Edge edge : edges) edge.color = Color.BLACK;
    }
}

public class GraphPanel extends JPanel {
    private Graph graph = new Graph();
    private Node startNode, endNode;
    private List<Node> currentPath = new ArrayList<>();
    private Map<String, Node> nodeMap = new HashMap<>();

    public GraphPanel() {
        setPreferredSize(new Dimension(1000, 800));
        setBackground(new Color(240, 248, 255));
        createCustomGraph();

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                handleNodeSelection(e.getPoint());
                repaint();
            }
        });
    }

    private void createCustomGraph() {
        int centerX = 500;
        int centerY = 400;
        int radius = 250;

        String[] labels = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M"};
        for (int i = 0; i < 13; i++) {
            double angle = 2 * Math.PI * i / 13;
            int x = centerX + (int) (radius * Math.cos(angle));
            int y = centerY + (int) (radius * Math.sin(angle));
            Node node = new Node(labels[i], x, y);
            graph.addNode(node);
            nodeMap.put(labels[i], node);
        }

        connect("A", "B");
        connect("B", "C");
        connect("C", "D");
        connect("D", "E");
        connect("E", "A");
        connect("E", "B");
        connect("B", "D");
        connect("B", "F");
        connect("F", "G");
        connect("G", "A");
        connect("G", "H");
        connect("H", "D");
        connect("H", "B");
        connect("M", "A");
        connect("M" , "B");
        connect("M","C");
    }

    private void connect(String a, String b) {
        graph.addEdge(nodeMap.get(a), nodeMap.get(b));
    }

    private void handleNodeSelection(Point point) {
        for (Node node : graph.nodes) {
            if (node.contains(point)) {
                if (startNode == null) {
                    startNode = node;
                    node.color = Color.YELLOW;
                } else if (endNode == null && node != startNode) {
                    endNode = node;
                    visualizeBFS();
                } else {
                    resetGraph();
                    startNode = node;
                    node.color = Color.YELLOW;
                }
                break;
            }
        }
    }

    private void visualizeBFS() {
        graph.resetColors();
        startNode.color = Color.YELLOW;

        if (startNode != null && endNode != null) {
            currentPath = graph.bfs(startNode, endNode);

            for (Node node : currentPath) node.color = Color.GREEN;

            for (int i = 0; i < currentPath.size() - 1; i++) {
                Node current = currentPath.get(i);
                Node next = currentPath.get(i + 1);
                for (Edge edge : graph.edges) {
                    if ((edge.from == current && edge.to == next) ||
                            (edge.from == next && edge.to == current)) {
                        edge.color = Color.GREEN;
                        break;
                    }
                }
            }
        }
    }

    private void resetGraph() {
        graph.resetColors();
        startNode = null;
        endNode = null;
        currentPath.clear();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Edge edge : graph.edges) {
            g2d.setColor(edge.color);
            g2d.setStroke(new BasicStroke(edge.color == Color.GREEN ? 3 : 2));
            g2d.drawLine(edge.from.x, edge.from.y, edge.to.x, edge.to.y);
        }

        for (Node node : graph.nodes) {
            g2d.setColor(node.color);
            g2d.fillOval(node.x - Node.DIAMETER / 2, node.y - Node.DIAMETER / 2, Node.DIAMETER, Node.DIAMETER);

            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(1.5f));
            g2d.drawOval(node.x - Node.DIAMETER / 2, node.y - Node.DIAMETER / 2, Node.DIAMETER, Node.DIAMETER);

            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("SansSerif", Font.BOLD, 16));
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(node.id);
            g2d.drawString(node.id, node.x - textWidth / 2, node.y + fm.getAscent() / 3);
        }

        g2d.setColor(Color.DARK_GRAY);
        g2d.setFont(new Font("SansSerif", Font.PLAIN, 16));
        String instruction = startNode == null ? "Click start node" :
                endNode == null ? "Click end node" :
                        "Click any node to reset";
        g2d.drawString(instruction, 20, 30);
    }
}