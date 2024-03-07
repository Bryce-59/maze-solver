package com.bryce_59.maze.create;

import java.util.ArrayList;

/**
 * Generate a new Maze with the Wilson's Algorithm
 * 
 * @author Bryce Richardson
 * @version 06-03-2024
 */
public class WilsonGenerator extends MazeGenerator {
    /** {@inheritDoc} */
    public Maze.Node[][] generateMaze(int numCol, int numRows) {
        Maze.Node[][] board = initializeBoard(numCol, numRows);

        if (board.length > 1 && board[0].length > 1) {
            // initialize data structures
            ArrayList<Maze.Node> unvisited = new ArrayList<>();
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    unvisited.add(board[i][j]);
                }
            }

            // select initial node
            Maze.Node startNode = unvisited.get((int) (Math.random() * unvisited.size()));
            unvisited.remove(startNode);
            while (!unvisited.isEmpty()) {
                // branch until meet visited node
                Maze.Node current = unvisited.get((int) (Math.random() * unvisited.size()));
                ArrayList<Maze.Node> branch = new ArrayList<>();
                while (unvisited.contains(current)) {
                    branch.add(current);
                    Maze.Node next = branch.size() >= 1 ? sampleAdjacent(board, current, branch.get(branch.size() - 1))
                            : sampleAdjacent(board, current);
                    // remove any loops
                    if (branch.contains(next)) {
                        Maze.Node prev = null;
                        while (!next.equals(prev)) {
                            prev = branch.remove(branch.size() - 1);
                        }
                    }
                    current = next;
                }
                branch.add(current);
                unvisited.removeAll(branch);
                for (int i = 0; i < branch.size() - 1; i++) {
                    branch.get(i).addEdge(branch.get(i + 1));
                    branch.get(i + 1).addEdge(branch.get(i));
                }
            }
        }
        return board;
    }
}
