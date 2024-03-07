package com.bryce_59.maze.create;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class MazeGenerator
 * 
 * @author Bryce Richardson
 * @version 06-03-2024
 */
public abstract class MazeGenerator {

    /**
     * Constructor for objects of class MazeGenerator
     */
    public MazeGenerator() {
    }

    // *Protected Methods*

    /**
     * Initialize an empty board to fill with a maze layout
     * 
     * @param numCol  the number of columns
     * @param numRows the number of rows
     * @return the empty board
     */
    protected Maze.Node[][] initializeBoard(int numCol, int numRows) {
        Maze.Node[][] board = new Maze.Node[numRows][numCol];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Maze.Node(j, i);
            }
        }
        return board;
    }

    /**
     * Choose a random neighbor of a node.
     * 
     * @param current the current node
     * @return a random neighbor of the node
     * @throws NullPointerException if node is null
     */
    protected Maze.Node sampleAdjacent(Maze.Node[][] board, Maze.Node current) {
        // preconditions
        if (current == null) {
            throw new NullPointerException("Maze.Node cannot be null");
        }

        // sample
        List<Maze.Node> tmp = getAdjacent(board, current);
        return tmp.size() > 0 ? tmp.get((int) (Math.random() * tmp.size())) : null;
    }

    /**
     * Choose a random neighbor of a Maze.Node.
     * 
     * @param current the current Maze.Node
     * @param exclude the neighbor to exclude
     * @return a random neighbor of the current Maze.Node
     * @throws NullPointerException  if Maze.Node is null
     * @throws InvalidInputException if exclude is not adjacent to current
     */
    protected Maze.Node sampleAdjacent(Maze.Node[][] board, Maze.Node current, Maze.Node exclude) {
        // preconditions
        if (current == null) {
            throw new NullPointerException("Maze.Node cannot be null");
        } else if (exclude == null) {
            return sampleAdjacent(board, current);
        }

        // sample
        List<Maze.Node> tmp = getAdjacent(board, current);
        tmp.remove(exclude);
        return tmp.size() > 0 ? tmp.get((int) (Math.random() * tmp.size())) : null;
    }

    // *Private Methods*

    /**
     * Get a list of nodes adjacent to the parameter
     * 
     * @param current the node to get adjacent Nodes for
     * @return a list of adjacent nodes
     * @throws NullPointerException if node is null
     */
    private List<Maze.Node> getAdjacent(Maze.Node[][] board, Maze.Node current) {
        // preconditions
        if (current == null) {
            throw new NullPointerException("Maze.Node cannot be null");
        }

        int x = current.getX();
        int y = current.getY();

        List<Maze.Node> tmp = new ArrayList<>();
        if (x > 0) {
            tmp.add(board[y][x - 1]);
        }
        if (x < board[0].length - 1) {
            tmp.add(board[y][x + 1]);
        }
        if (y > 0) {
            tmp.add(board[y - 1][x]);
        }
        if (y < board.length - 1) {
            tmp.add(board[y + 1][x]);
        }

        return tmp;
    }

    // *Abstract Methods*

    /**
     * Generate a maze layout
     * 
     * @return the maze
     */
    abstract Maze.Node[][] generateMaze(int numCol, int numRows);
}
