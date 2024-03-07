package com.bryce_59.maze.create;

import java.util.ArrayList;

/**
 * Generate a new Maze with the Growing Tree Algorithm
 * 
 * @author Bryce Richardson
 * @version 06-03-2024
 */
public class GrowingTreeGenerator extends MazeGenerator
{
    public GrowingTreeGenerator(double isRandom) {
        this.isRandom = isRandom < 0 ? 0 : isRandom > 1 ? 1 : isRandom;
    }

    public Maze.Node[][] generateMaze() {
        Maze.Node[][] board = new Maze.Node[getRows()][getCols()];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Maze.Node(j, i);
            }
        }
        
        if (getRows() > 1 && getCols() > 1) {
            ArrayList<Maze.Node> visited = new ArrayList<>();
            
            ArrayList<Maze.Node> branch = new ArrayList<>();
            Maze.Node startNode = board[(int) (Math.random() * board.length)][(int) (Math.random() * board[0].length)];
            visited.add(startNode);
            branch.add(startNode);

            while (!branch.isEmpty()) {
                Maze.Node current = Math.random() < isRandom ? branch.get((int) (Math.random() * branch.size())) : branch.get(branch.size() - 1);

                int x = current.getX();
                int y = current.getY();

                ArrayList<Maze.Node> tmp = new ArrayList<>();
                if (x > 0 && !visited.contains(board[y][x-1])) {
                    tmp.add(board[y][x-1]);
                }
                if (x < board[0].length - 1 && !visited.contains(board[y][x+1])) {
                    tmp.add(board[y][x+1]);
                }
                if (y > 0 && !visited.contains(board[y-1][x])) {
                    tmp.add(board[y-1][x]);
                }
                if (y < board.length - 1 && !visited.contains(board[y+1][x])) {
                    tmp.add(board[y+1][x]);
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

    private double isRandom = 0.5;

    /**
     * Generate a new Maze with Prim's Algorithm
     * 
     * @author Bryce Richardson
     * @version 06-03-2024
     */
    public static class PrimGenerator extends GrowingTreeGenerator
    {
        public PrimGenerator() {
            super(1);
        }
    }

    /**
     * Generate a new Maze with the Recursive Backtracking Algorithm
     * 
     * @author Bryce Richardson
     * @version 06-03-2024
     */
    public static class BacktrackGenerator extends GrowingTreeGenerator
    {
        public BacktrackGenerator() {
            super(0);
        }
    }
}
