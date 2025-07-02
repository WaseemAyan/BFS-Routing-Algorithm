# BFS Pathfinding Visualizer (Java Swing)

This is a **Breadth-First Search (BFS)** pathfinding visualizer built using **Java Swing**. It allows users to interactively select two nodes in a graph and visualize the shortest path between them using the BFS algorithm.

## 💡 Features

- Interactive GUI using Java Swing.
- Circular custom graph layout with labeled nodes.
- Click to select start and end nodes.
- Visualize the shortest path using BFS.
- Green-colored path indicates the shortest route.
- Yellow node = start, Green node = part of path.

## 🧠 How It Works

1. The program displays a static graph with 13 labeled nodes (A to M) in a circular layout.
2. You can click on:
   - **First node** → sets the **start** (Yellow).
   - **Second node** → sets the **end**, and the BFS algorithm runs.
3. The **shortest path** from start to end (if one exists) is shown in **green** (both nodes and connecting edges).
4. Click any node again to reset and choose a new path.

## 📂 Project Structure

- `BFSVisualizer.java` – The main class, sets up the JFrame and footer.
- `GraphPanel.java` – Contains the main visual component, handles drawing, user input, and graph logic.
- `Graph.java` – Handles graph data, BFS logic, and path reconstruction.
- `Edge.java` – Defines edges between nodes.
- `Node.java` – Defines individual graph nodes and their visuals.

## 🛠️ How to Run

### Prerequisites:
- JDK 8 or higher installed
- Java-compatible IDE (like IntelliJ IDEA, Eclipse, or VS Code with Java extensions)

### Run with Terminal:
1. Save all the classes (`BFSVisualizer.java`, `GraphPanel.java`, `Graph.java`, `Node.java`, `Edge.java`) in the same directory.
2. Compile the files:
   ```bash
   javac *.java
