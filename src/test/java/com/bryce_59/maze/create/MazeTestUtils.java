package com.bryce_59.maze.create;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;

public final class MazeTestUtils {

    private MazeTestUtils() {
    }

    /**
     * Creates a rectangular grid of Maze.Nodes without any edges.
     */
    public static Maze.Node[][] createGrid(int rows, int cols) {
        Maze.Node[][] board = new Maze.Node[rows][cols];

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                board[y][x] = new Maze.Node(x, y);
            }
        }

        return board;
    }

    /**
     * Connect two nodes in both directions.
     */
    public static void connect(Maze.Node a, Maze.Node b) {
        if (a == null || b == null) {
            throw new NullPointerException("Nodes cannot be null");
        }

        a.addEdge(b);
        b.addEdge(a);
    }

    /**
     * Counts directed edges in the graph.
     *
     * A normal undirected maze should have each connection represented
     * twice, once from each endpoint.
     */
    public static int countDirectedEdges(Maze.Node[][] board) {
        int count = 0;

        for (Maze.Node[] row : board) {
            for (Maze.Node node : row) {
                count += node.getEdgeSet().size();
            }
        }

        return count;
    }

    /**
     * Verify that every edge is reciprocal.
     */
    public static boolean hasReciprocalEdges(Maze.Node[][] board) {
        for (Maze.Node[] row : board) {
            for (Maze.Node node : row) {
                for (Maze.Node neighbor : node.getEdgeSet()) {
                    if (!neighbor.hasEdge(node)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Verify that every node in the board can be reached from board[0][0].
     */
    public static boolean isConnected(Maze.Node[][] board) {
        if (board.length == 0 || board[0].length == 0) {
            return true;
        }

        Set<Maze.Node> visited = new HashSet<>();
        ArrayDeque<Maze.Node> queue = new ArrayDeque<>();

        Maze.Node start = board[0][0];
        visited.add(start);
        queue.add(start);

        while (!queue.isEmpty()) {
            Maze.Node current = queue.removeFirst();

            for (Maze.Node neighbor : current.getEdgeSet()) {
                if (visited.add(neighbor)) {
                    queue.addLast(neighbor);
                }
            }
        }

        int expected = board.length * board[0].length;
        return visited.size() == expected;
    }

    /**
     * Get the node at the specified coordinates.
     */
    public static Maze.Node node(Maze.Node[][] board, int x, int y) {
        return board[y][x];
    }

    /**
     * Verify that a path is valid for the supplied maze.
     */
    public static boolean isValidPath(java.util.List<Maze.Node> path) {
        if (path == null || path.isEmpty()) {
            return false;
        }

        for (int i = 1; i < path.size(); i++) {
            Maze.Node previous = path.get(i - 1);
            Maze.Node current = path.get(i);

            if (!previous.hasEdge(current)) {
                return false;
            }
        }

        return true;
    }
}