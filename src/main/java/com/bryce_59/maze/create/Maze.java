package com.bryce_59.maze.create;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Bryce-59
 * @version 06-03-2024
 */
public class Maze
{
    // *Constructors*
    
    /**
     * Constructor for objects of class Maze
     */
    public Maze() 
    {
        this(DEFAULT_GENERATOR);
    }

    /**
     * Constructor for objects of class Maze
     * 
     * @param generator  the maze-generation algorithm to use
     */
    public Maze(MazeGenerator generator) {
        this(DEFAULT_COL, DEFAULT_ROWS, generator);
    }
    
    /**
     * Constructor for objects of class Maze
     * 
     * @param numCol  the number of columns
     * @param numRows  the number of rows
     */
    public Maze (int numCol, int numRows) 
    {
        this(numCol, numRows, DEFAULT_GENERATOR);
    }

    /**
     * Constructor for objects of class Maze
     * 
     * @param numCol  the number of columns
     * @param numRows  the number of rows
     * @param generator  the maze-generation algorithm to use
     */
    public Maze (int numCol, int numRows, MazeGenerator generator) 
    {
        this.generator = generator;
        setDim(numCol, numRows);
    }

    /**
     * Copy constructor for objects of class Maze
     * 
     * @param src  the maze to copy
     */
    public Maze (Maze src) 
    {
        this.generator = src.generator;
        this.board = src.getBoard();
    }
    
    // *Public Methods*
    
    /**
     * Generate a new Maze
     */
    public void generateMaze() {
        board = generator.generateMaze();
    }

    /**
     * Get a copy of the Maze board
     * 
     * @return a copy of the board
     */
    public Maze.Node[][] getBoard() {
        int numRows = this.board.length;
        int numCol = numRows > 0 ? this.board[0].length : 0;
        
        // initialize board copy
        Maze.Node[][] ret = new Maze.Node[numRows][];
        for (int i = 0; i < numRows; i++) {
            ret[i] = new Maze.Node[numCol];
            for (int j = 0; j < numCol; j++) {
                ret[i][j] = new Node(j, i);
            }
        }

        // fill board copy
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCol; j++) {
                if (i < numRows - 1 && this.board[i][j].hasEdge(this.board[i+1][j])) {
                    ret[i][j].addEdge(ret[i+1][j]);
                }
                if (j < numCol - 1 && this.board[i][j].hasEdge(this.board[i][j+1])) {
                    ret[i][j].addEdge(ret[i][j+1]);
                }
                ret[i][j].setDepth(this.board[i][j].getDepth());
            }
        }
        return ret;
    }
    
    /**
     * Get the number of columns in the Maze
     * 
     * @return the number of columns
     */
    public int getCols() {
        return numCol;
    }
    
    /**
     * Get the number of rows in the Maze
     * 
     * @return the number of rows
     */
    public int getRows() {
        return numRows;
    }
    
    /**
     * Initialize the Maze board
     * 
     * @param  numCol the number of columns
     * @param  numRows the number of rows
     * @throws IllegalArgumentException  if numCol < 0 or numRows < 0
     */
    public void setDim(int numCol, int numRows) {
        if (numCol <= 0 || numRows <= 0) {
            throw new IllegalArgumentException("Invalid dimensions");
        }

        this.numCol = numCol;
        this.numRows = numRows;
        this.generator.loadParam(numCol, numRows);
    }
    
    // *Instance Variables*
    protected Maze.Node[][] board;
    protected MazeGenerator generator;

    private int numCol;
    private int numRows;
    

    // settings
    private static final int DEFAULT_COL = 10;
    private static final int DEFAULT_ROWS = 10;
    private static final MazeGenerator DEFAULT_GENERATOR = new GrowingTreeGenerator(0.7);

    /**
     * Maze.Node class for Maze objects.
     * 
     * @author Bryce-59
     * @version 26-02-2024
     */
    public static class Node
    {
        // *Constructors*
        
        /**
         * Constructor for objects of class Maze.Node
         * 
         * @param x  the x-positin
         * @param y  the y-position
         */
        public Node(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
        
        // *Public Methods*
        
        /**
         * Add another Maze.Node object to the edge set of this Maze.Node
         * 
         * @param other  the edge to add
         */
        public void addEdge(Maze.Node other)
        {
            // preconditions
            if (other == null) {
                throw new NullPointerException("Cannot add a null edge");
            }
            
            // add edge
            if (!hasEdge(other)) {
                edges.put(other, 1);
            }
        }
        
        /**
         * Return the set of edges for the Maze.Node
         * @return  the set of edges
         */
        public Set<Maze.Node> getEdgeSet() {
            return edges.keySet();
        }

        /**
         * Return the depth of the Maze.Node
         * @return   the depth
         */
        public int getDepth()
        {
            return depth;
        }
        
        /**
         * Return the x-position of the Maze.Node
         * @return   the x-position
         */
        public int getX()
        {
            return x;
        }
        
        /**
         * Return the y-position of the Maze.Node
         * @return   the x-position
         */
        public int getY()
        {
            return y;
        }
    
        /**
         * Verify whether this Maze.Node object contains another Maze.Node object in its edge set
         * @return   true if the edge set contains node else false
         */
        public boolean hasEdge(Node other)
        {
            // preconditions
            if (other == null) {
                throw new NullPointerException("Cannot add a null edge");
            }
            
            // verify edge
            return edges.containsKey(other);
        }

        /** {@inheritDoc} */
        public String toString() {
            return "("+x+","+y+")";
        }

        // *Protected Methods*

        /**
         * Modify the depth of the Node
         * @param   the new depth
         */
        protected void setDepth(int depth)
        {
            this.depth = depth;
        }
        
        // *Instance Variables*
        private Map<Node, Integer> edges = new HashMap<>();
        private int depth;
        private int x;
        private int y;
    }
}