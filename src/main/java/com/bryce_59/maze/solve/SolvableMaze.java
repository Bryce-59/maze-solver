package com.bryce_59.maze.solve;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bryce_59.maze.create.*;

/**
 * Write a description of class SolvableMaze here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SolvableMaze extends Maze
{
    // *Constructors*

    /**
     * Constructor for objects of class SolvableMaze
     */
    public SolvableMaze()
    {
        super();
    }
    
    /**
     * Constructor for objects of class SolvableMaze
     * 
     * @param search  the SearchAlgorithm
     */
    public SolvableMaze(SearchAlgorithm search)
    {
        super();
        this.search = search;
    }
    
    /**
     * Constructor for objects of class SolvableMaze
     * 
     * @param  numCol the number of columns
     * @param  numRows the number of rows
     */
    public SolvableMaze(int numCol, int numRows)
    {
        this(numCol, numRows, null, null, null);
    }
    
    /**
     * Constructor for objects of class SolvableMaze
     * 
     * @param  numCol the number of columns
     * @param  numRows the number of rows
     * @param search  the SearchAlgorithm
     */
    public SolvableMaze(int numCol, int numRows, SearchAlgorithm search)
    {
        this(numCol, numRows, null, null, search);
    }
    
    /**
     * Constructor for objects of class SolvableMaze
     * 
     * @param  numCol the number of columns
     * @param  numRows the number of rows
     * @param  startNode the starting position
     * @param  endNode  the ending location
     */
    protected SolvableMaze(int numCol, int numRows, Maze.Node startNode, Maze.Node endNode)
    {
        this(numCol, numRows, startNode, endNode, null);
    }
    
    /**
     * Constructor for objects of class SolvableMaze
     * 
     * @param  numCol the number of columns
     * @param  numRows the number of rows
     * @param  startNode the starting position
     * @param  endNode  the ending location
     * @param search  the SearchAlgorithm
     */
    protected SolvableMaze(int numCol, int numRows, Maze.Node startNode, Maze.Node endNode, SearchAlgorithm search)
    {
        super(numCol, numRows);
        setAlgorithm(search);
        if (startNode != null) {
            setStartPoint(startNode);
        }
        if (endNode != null) {
            setEndPoint(endNode);
        }
    }

    /**
     * Copy constructor for objects of class SolvableMaze
     */
    public SolvableMaze(SolvableMaze src)
    {
        super(src);
        setAlgorithm(src.search);
        if (src.startNode != null) {
            setStartPoint(src.startNode);
        }
        if (endNode != null) {
            setEndPoint(src.endNode);
        }
    }
    
    // *Public Methods*

    /**
     * Get the start point
     * 
     * @return the start point
     */
    public Maze.Node getStartPoint() {
        return startNode;
    }

    /**
     * Verify if the Maze is in a completed state
     * 
     * @return  true if solved else false
     */
    public boolean isSolved() {
        return search != null && search.isSolved();
    }
    
    /**
     * Reset the Maze to its base state
     */
    public void reset() {
        if (search != null) {
            search.reset();
            search.setParam(startNode, endNode);
        }
        visited.clear();
        path.clear();
        mustReset = false;
    }
    
    /**
     * Set the SearchAlgorithm
     * 
     * @param search  the SearchAlgorithm
     */
    public void setAlgorithm(SearchAlgorithm search) {        
        // set
        this.search = search;
        mustReset = true;
    }
    
    /**
     * Set the end point
     * 
     * @param x  the x-position
     * @param y  the y-position
     * @throws IllegalArgumentException if x or y are out of range
     */
    public void setEndPoint(int x, int y) {
        // preconditions
        if (y < 0 || y >= getRows() || x < 0 || x >= getCols()) {
            throw new IllegalArgumentException("Indicies must be in range");
        }
        
        // set
        setEndPoint(board[y][x]);
    }
    
    /**
     * Set the start point
     * 
     * @param x  the x-position
     * @param y  the y-position
     * @throws IllegalArgumentException if x or y are out of range
     */
    public void setStartPoint(int x, int y) {
        // preconditions
        if (y < 0 || y >= getRows() || x < 0 || x >= getCols()) {
            throw new IllegalArgumentException("Indicies must be in range");
        }
        
        // set
        setStartPoint(board[y][x]);
    }
    
    /**
     * Update the Maze state
     * 
     * @throws  IllegalStateException if a parameter is not set, or if the Maze is solved
     */
    public void update() {
        // preconditions
        if (search == null) {
            throw new IllegalStateException("SearchAlgorithm must be set");
        } else if (startNode == null) {
            throw new IllegalStateException("Start point must be set");
        } else if (mustReset) {
            throw isSolved() ? new IllegalStateException("Cannot update a solved Maze") : new IllegalStateException("Must reset after initializing or modifying a parameter");
        }
        
        // update
        path = search.update();
        visited.addAll(path);
        
        mustReset = mustReset || isSolved();
    }

    /**
     * Get the current path
     * 
     * @return the current path
     */
    public List<Maze.Node> getPath() {
        return path;
    }

    /**
     * Get the visited list
     * 
     * @return the visited list
     */
    public Set<Maze.Node> getVisited() {
        return visited;
    }

    /**
     * Initialize the Maze board
     * 
     * @param  numCol the number of columns
     * @param  numRows the number of rows
     * @throws IllegalArgumentException  if numCol < 0 or numRows < 0
     */
    public void resize(int numCol, int numRows) {
        super.resize(numCol, numRows);
        if (startNode != null) {
            setStartPoint(startNode.getX(), startNode.getY());
        }
        if (endNode != null) {
            setEndPoint(numCol-1, numRows-1);
        }
    }

    /**
     * Set the end point
     * 
     * @param endPoint  the end point
     * @throws NullPointerException if node is null
     * @throws IllegalArgumentException if node is not valid
     */
    public void setEndPoint(Maze.Node endNode) {
        // preconditions
        if (endNode == null) {
            throw new NullPointerException("Maze.Node cannot be null");
        }
        else if (endNode != null && !hasNode(endNode)) {
            throw new IllegalArgumentException("Maze must contain endpoint");
        }
        
        // set
        this.endNode = endNode;
        mustReset = true;
    }

    /**
     * Set the start point
     * 
     * @param endPoint  the end point
     * @throws NullPointerException  if node is null
     * @throws IllegalArgumentException if node is not valid
     */
    public void setStartPoint(Maze.Node startNode) {
        // preconditions
         if (startNode == null) {
            throw new NullPointerException("Maze.Node cannot be null");
        }
        else if (startNode != null && !hasNode(startNode)) {
            throw new IllegalArgumentException("Maze must contain startpoint");
        }
        
        // set
        this.startNode = startNode;
        mustReset = true;
    }

    // *Private Methods*

    /**
     * Verify that a node exists within the Maze.
     * 
     * @param current  the node to verify
     * @return  true if the Maze contains Maze.Node else false
     * @throws NullPointerException  if Maze.Node is null
     */
    private boolean hasNode(Maze.Node current) {
        if (current == null) {
            throw new NullPointerException("Maze.Node cannot be null");
        }
                
        int x = current.getX();
        int y = current.getY();
        if (y < 0 || y >= board.length || x < 0 || x >= board[0].length) {
            return false;
        }
        
        return board[y][x] != null && board[y][x].equals(current);
    }
    
    // instance variables
    protected Maze.Node startNode;
    protected Maze.Node endNode;
    
    protected SearchAlgorithm search = null;
    protected Set<Maze.Node> visited = new HashSet<>();
    protected List<Maze.Node> path = new ArrayList<>();
    
    protected boolean mustReset = true;
}
