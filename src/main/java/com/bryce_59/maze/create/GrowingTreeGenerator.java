package com.bryce_59.maze.create;

import java.util.ArrayList;

/**
 * Generate a new Maze with the Growing Tree Algorithm
 * 
 * @author Bryce Richardson
 * @version 06-03-2024
 */
public class GrowingTreeGenerator extends MazeGenerator {
    /**
     * Constructor for objects of class GrowingTreeGenerator
     */
    public GrowingTreeGenerator(double isRandom) {
        this.isRandom = isRandom < 0 ? 0 : isRandom > 1 ? 1 : isRandom;
    }

    /** {@inheritDoc} */
    public Maze.Node[][] generateMaze(int numCol, int numRows) {
        Maze.Node[][] board = initializeBoard(numCol, numRows);

        if (numCol > 1 && numRows > 1) {
            // initialize data structures
            ArrayList<Maze.Node> visited = new ArrayList<>();

            ArrayList<Maze.Node> branch = new ArrayList<>();
            Maze.Node startNode = board[(int) (Math.random() * board.length)][(int) (Math.random() * board[0].length)];
            visited.add(startNode);
            branch.add(startNode);

            while (!branch.isEmpty()) {
                // select current node from either a random location or the most recently added
                // based on weighted chance
                Maze.Node current = Math.random() < isRandom ? branch.get((int) (Math.random() * branch.size()))
                        : branch.get(branch.size() - 1);

                int x = current.getX();
                int y = current.getY();

                // sample next node from the current node's neighbors
                ArrayList<Maze.Node> tmp = new ArrayList<>();
                if (x > 0 && !visited.contains(board[y][x - 1])) {
                    tmp.add(board[y][x - 1]);
                }
                if (x < board[0].length - 1 && !visited.contains(board[y][x + 1])) {
                    tmp.add(board[y][x + 1]);
                }
                if (y > 0 && !visited.contains(board[y - 1][x])) {
                    tmp.add(board[y - 1][x]);
                }
                if (y < board.length - 1 && !visited.contains(board[y + 1][x])) {
                    tmp.add(board[y + 1][x]);
                }

                if (tmp.isEmpty()) {
                    branch.remove(current);
                    continue;
                }
                Maze.Node next = tmp.get((int) (Math.random() * tmp.size()));
                visited.add(next);
                branch.add(next);

                current.addEdge(next);
                next.addEdge(current);

            }
        }
        return board;
    }

    // *Instance Variables*

    private double isRandom = 0.5;
}
