package com.bryce_59.maze.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import com.bryce_59.maze.create.*;
import com.bryce_59.maze.solve.Solvable;
import com.bryce_59.maze.solve.SolvableMaze;

public class SolvableMazeGraphic extends SolvableGraphic implements Maze {

    // *Constructors*

    public SolvableMazeGraphic(int numCol, int numRows) {
        solvable = new SolvableMaze(numCol, numRows);
    }

    public SolvableMazeGraphic(SolvableMaze src) {
        solvable = src.clone();
    }

    // *Public Methods*

    /** {@inheritDoc} */
    @Override
    public SolvableGraphic clone() {
        return new SolvableMazeGraphic(solvable);
    }

    /** {@inheritDoc} */
    public void generateMaze() {
        solvable.generateMaze();
    }

    /** {@inheritDoc} */
    public Maze.Node[][] getBoard() {
        return solvable.getBoard();
    }

    /** {@inheritDoc} */
    public int getCols() {
        return solvable.getCols();
    }

    /** {@inheritDoc} */
    public void resize(int numCol, int numRows) {
        solvable.resize(numCol, numRows);
    }

    /** {@inheritDoc} */
    @Override
   public Dimension getPreferredSize() {
      // so that our GUI is big enough
      return new Dimension(RECT_WIDTH + 2 * RECT_X, RECT_HEIGHT + 2 * RECT_Y);
   }


    /** {@inheritDoc} */
    public int getRows() {
        return solvable.getRows();
    }

    /**
     * Set the color variable
     * 
     * @param c
     */
    public void toggleColor(boolean c) {
        color = c;
    }

    /**
     * Set the walls variable
     * 
     * @param w
     */
    public void toggleWalls(boolean w) {
        walls = w;
    }

    // *Protected Methods*

    /** {@inheritDoc} */
    protected Solvable getSolvable() {
        return solvable;
    }

    /** {@inheritDoc} */
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        // depth map
        if (color) {
            paintDepthMap(g);
        }


        // color search path
        paintSearchPath(g);
    
        // draw maze
        if (walls) {
            paintMaze(g);
        }

        // draw the rectangle here
        int offset = (getWidth() - getCellSize() * getCols()) / 2;
        g.drawRect(offset, RECT_Y, getCellSize() * getCols(), getCellSize() * getRows());
    }

    // *Private Methods*

    private int getCellSize() {
        int h = Math.min(getHeight() - 2 * RECT_X, getWidth() - 2 * RECT_Y);

        return getRows() > 0 && getCols() > 0 ? Math.min(h / getRows(), h / getCols()) : 0;
    }

    /**
     * Paint the maze
     * 
     * @param g
     */
    private void paintMaze(Graphics g) {
        int numRows = getRows();
        int numCol = getCols();
        int sizeOfCell = getCellSize();

        Maze.Node[][] board = solvable.getBoard();

        int offset = (getWidth() - getCellSize() * getCols()) / 2;

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCol; j++) {
                
                int startX = (offset) + sizeOfCell*j;
                int startY = RECT_Y + sizeOfCell*i;

                // the horizontal edges
                if (i < numRows - 1 && !board[i][j].hasEdge(board[i+1][j])) {
                    g.drawLine(startX, startY + sizeOfCell, startX + sizeOfCell, startY + sizeOfCell);
                }
                
                // the vertical edges 
                if (j < numCol - 1 && !board[i][j].hasEdge(board[i][j+1])) {
                    g.drawLine(startX + sizeOfCell, startY, startX + sizeOfCell, startY + sizeOfCell);   
                }
            }
        }
    }

    /**
     * Paint the Search Path
     * 
     * @param g
     */
    private void paintSearchPath(Graphics g) {
        int sizeOfCell = getCellSize();

        int offset = (getWidth() - getCellSize() * getCols()) / 2;

        g.setColor(Color.GRAY);
        for (Maze.Node n : getVisited()) {
            g.fillRect(offset + sizeOfCell * n.getX(), RECT_Y + sizeOfCell * n.getY(), sizeOfCell, sizeOfCell);
        }
        
        g.setColor(Color.GREEN);
        for (Maze.Node n: getPath()) {
            g.fillRect(offset + sizeOfCell * n.getX(), RECT_Y + sizeOfCell * n.getY(), sizeOfCell, sizeOfCell);
        }
    
        g.setColor(Color.BLACK);
    }

    /**
     * Paint the Depth Map
     * 
     * @param g
     */
    private void paintDepthMap(Graphics g) {
        int numRows = getRows();
        int numCol = getCols();
        int sizeOfCell = getCellSize();
        ArrayList<Maze.Node> queue = new ArrayList<>();
        Maze.Node start = getStartPoint();
        queue.add(start);
        int red = 255;
        int green = 255;
        int blue = 255;
        ArrayList<Maze.Node> visited = new ArrayList<>();
        visited.add(start);
        int limit = 0;
        int offset = (getWidth() - getCellSize() * getCols()) / 2;
        
        while (!queue.isEmpty()) {
            try {
            g.setColor(new Color(Math.abs(red % 256), Math.abs(green % 256), Math.abs(blue % 256)));
            } catch (Exception e) {
                System.out.println(red);
            }
            Maze.Node prev = queue.get(0);
            for (Maze.Node current : prev.getEdgeSet()) {
                if (!visited.contains(current)) {
                    g.fillRect(offset + sizeOfCell * current.getX(), RECT_Y + sizeOfCell * current.getY(), sizeOfCell, sizeOfCell);
                    queue.add(current);
                    visited.add(current);
                }
            }
            queue.remove(prev);

            final int BASE_X = 100;
            final int BASE_Y = 100;
            int BASE = numRows * numCol;
            final int MAX = 8 * Math.max(BASE / (BASE_X * BASE_Y), 1);
            if (limit % MAX == 0) {
                int a = Math.max((BASE_X * BASE_Y) / (BASE), 1);
                if (green >= 255 && blue >= 255 && red > 0) {
                    red -= a;  
                }
                else if (red >= 255 && green >= 255) {
                    blue += a;
                }
                else if (red >= 255 && blue <= 0) {
                    green += a;
                }
                else if (red >= 255 && green <= 0) {
                    blue -= a;
                }
                else if (green <= 0 && blue >= 255) {
                    red += a;
                }
                else if (red <= 0) {
                    green -= a;
                }
            }
            limit += 1;
        }
    }

    // *Instance Methods*

    private SolvableMaze solvable;
    private boolean walls = true;
    private boolean color = false;

    // SETTINGS

    private static final int RECT_X = 20;
    private static final int RECT_Y = 20;
    private static final int RECT_HEIGHT = 200;
    private static final int RECT_WIDTH = 200;
}