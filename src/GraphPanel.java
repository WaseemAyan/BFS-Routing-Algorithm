import javax.swing.*; // For Swing GUI components
import java.awt.*;    // For AWT components like Graphics, Color, etc.
import java.awt.event.*; // For handling user events like mouse clicks
import java.util.*;       // For collections like Map, List, Set, etc.
import java.util.List;    // Explicitly importing List

// Represents an edge between two nodes in the graph
class Edge {
    Node from, to;             // Start and end nodes of the edge
    Color color = Color.BLACK; // Default color of the edge

    public Edge(Node from, Node to) {
        this.from = from;
        this.to = to;
    }
}

// Graph class containing all logic for nodes, edges, BFS traversal, and path tracking
class Graph {
    List<Node> nodes = new ArrayList<>();                // All nodes in the graph
    List<Edge> edges = new ArrayList<>();                // All edges in the graph
    Map<Node, List<Node>> adjList = new HashMap<>();     // Adjacency list for each node

    // Adds a node and initializes its adjacency list
    public void addNode(Node node) {
        nodes.add(node);
        adjList.put(node, new ArrayList<>());
    }

    // Adds an undirected edge between two nodes
    public void addEdge(Node a, Node b) {
        edges.add(new Edge(a, b));
        adjList.get(a).add(b);
        adjList.get(b).add(a);
    }

    // Breadth-First Search algorithm from start to end node
    public List<Node> bfs(Node start, Node end) {
        Map<Node, Node> parentMap = new HashMap<>(); // To reconstruct the path later
        Queue<Node> queue = new LinkedList<>();      // BFS queue
        Set<Node> visited = new HashSet<>();         // Track visited nodes

        queue.add(start);
        visited.add(start);
        parentMap.put(start, null); // Start node has no parent

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (current == end) break; // Stop if end is reached

            // Explore neighbors
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

    // Reconstructs path from parent map by backtracking from end to start
    private List<Node> reconstructPath(Map<Node, Node> parentMap, Node end) {
        LinkedList<Node> path = new LinkedList<>();
        Node current = end;

        while (current != null) {
            path.addFirst(current);
            current = parentMap.get(current);
        }

        return path;
    }

    // Resets all node and edge colors to default
    public void resetColors() {
        for (Node node : nodes) node.color = Color.BLUE;
        for (Edge edge : edges) edge.color = Color.BLACK;
    }
}

// JPanel subclass for rendering and interacting with the graph
public class GraphPanel extends JPanel {
    private Graph graph = new Graph();                         // Graph instance
    private Node startNode, endNode;                           // Start and end selected by user
    private List<Node> currentPath = new ArrayList<>();        // Path returned by BFS
    private Map<String, Node> nodeMap = new HashMap<>();       // Maps labels (A, B...) to nodes

    public GraphPanel() {
        setPreferredSize(new Dimension(1000, 800));            // Set panel size
        setBackground(new Color(240, 248, 255));               // Light blue background
        createCustomGraph();                                   // Creates nodes and edges

        // Mouse click listener to select start and end nodes
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                handleNodeSelection(e.getPoint());
                repaint(); // Refresh view after selection
            }
        });
    }

    // Creates the circular layout of labeled nodes and their connections
    private void createCustomGraph() {
        int centerX = 500, centerY = 400, radius = 250;

        // Labels for each node
        String[] labels = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M"};

        // Place nodes in a circular layout
        for (int i = 0; i < 13; i++) {
            double angle = 2 * Math.PI * i / 13;
            int x = centerX + (int) (radius * Math.cos(angle));
            int y = centerY + (int) (radius * Math.sin(angle));
            Node node = new Node(labels[i], x, y);
            graph.addNode(node);
            nodeMap.put(labels[i], node);
        }

        // Connect nodes to form the desired graph
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
        connect("M", "B");
        connect("M", "C");
    }

    // Helper to add edge between two labeled nodes
    private void connect(String a, String b) {
        graph.addEdge(nodeMap.get(a), nodeMap.get(b));
    }

    // Handles user clicks: set start and end nodes and run BFS
    private void handleNodeSelection(Point point) {
        for (Node node : graph.nodes) {
            if (node.contains(point)) {
                if (startNode == null) {
                    startNode = node;
                    node.color = Color.YELLOW;
                } else if (endNode == null && node != startNode) {
                    endNode = node;
                    visualizeBFS(); // Run BFS once both are selected
                } else {
                    resetGraph(); // Reset on third click
                    startNode = node;
                    node.color = Color.YELLOW;
                }
                break;
            }
        }
    }

    // Performs BFS and colors the path
    private void visualizeBFS() {
        graph.resetColors(); // Clear previous colors
        startNode.color = Color.YELLOW;

        if (startNode != null && endNode != null) {
            currentPath = graph.bfs(startNode, endNode);

            // Color all nodes in path
            for (Node node : currentPath) node.color = Color.GREEN;

            // Color corresponding edges in path
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

    // Resets all graph colors and selection state
    private void resetGraph() {
        graph.resetColors();
        startNode = null;
        endNode = null;
        currentPath.clear();
    }

    // Draws the graph: edges, nodes, and instruction text
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Enable smooth drawing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw all edges
        for (Edge edge : graph.edges) {
            g2d.setColor(edge.color);
            g2d.setStroke(new BasicStroke(edge.color == Color.GREEN ? 3 : 2));
            g2d.drawLine(edge.from.x, edge.from.y, edge.to.x, edge.to.y);
        }

        // Draw all nodes
        for (Node node : graph.nodes) {
            // Fill node
            g2d.setColor(node.color);
            g2d.fillOval(node.x - Node.DIAMETER / 2, node.y - Node.DIAMETER / 2, Node.DIAMETER, Node.DIAMETER);

            // Draw node border
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(1.5f));
            g2d.drawOval(node.x - Node.DIAMETER / 2, node.y - Node.DIAMETER / 2, Node.DIAMETER, Node.DIAMETER);

            // Draw node label
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("SansSerif", Font.BOLD, 16));
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(node.id);
            g2d.drawString(node.id, node.x - textWidth / 2, node.y + fm.getAscent() / 3);
        }

        // Display instruction based on current selection state
        g2d.setColor(Color.DARK_GRAY);
        g2d.setFont(new Font("SansSerif", Font.PLAIN, 16));
        String instruction = startNode == null ? "Click start node" :
                             endNode == null ? "Click end node" :
                             "Click any node to reset";
        g2d.drawString(instruction, 20, 30);
    }
}
