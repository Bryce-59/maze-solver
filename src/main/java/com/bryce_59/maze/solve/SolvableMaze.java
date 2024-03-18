package com.bryce_59.maze.solve;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bryce_59.maze.create.*;

/**
 * Write a description of class SolvableMaze here.
 * 
 * @author Bryce-59
 * @version 18-03-2024
 */
public class SolvableMaze extends SimpleMaze implements Solvable {
    // *Constructors*

    /**
     * Constructor for objects of class SolvableMaze
     */
    public SolvableMaze() {
        super();
    }

    /**
     * Constructor for objects of class SolvableMaze
     * 
     * @param search the SearchAlgorithm
     */
    public SolvableMaze(SearchAlgorithm search) {
        super();
        this.search = search;
    }

    /**
     * Constructor for objects of class SolvableMaze
     * 
     * @param numCol  the number of columns
     * @param numRows the number of rows
     */
    public SolvableMaze(int numCol, int numRows) {
        this(numCol, numRows, null, null, null);
    }

    /**
     * Constructor for objects of class SolvableMaze
     * 
     * @param numCol  the number of columns
     * @param numRows the number of rows
     * @param search  the SearchAlgorithm
     */
    public SolvableMaze(int numCol, int numRows, SearchAlgorithm search) {
        this(numCol, numRows, null, null, search);
    }

    /**
     * Constructor for objects of class SolvableMaze
     * 
     * @param numCol    the number of columns
     * @param numRows   the number of rows
     * @param startNode the starting position
     * @param endNode   the ending location
     */
    protected SolvableMaze(int numCol, int numRows, Maze.Node startNode, Maze.Node endNode) {
        this(numCol, numRows, startNode, endNode, null);
    }

    /**
     * Constructor for objects of class SolvableMaze
     * 
     * @param numCol    the number of columns
     * @param numRows   the number of rows
     * @param startNode the starting position
     * @param endNode   the ending location
     * @param search    the SearchAlgorithm
     */
    protected SolvableMaze(int numCol, int numRows, Maze.Node startNode, Maze.Node endNode, SearchAlgorithm search) {
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
    public SolvableMaze(SolvableMaze src) {
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
    
    /** {@inheritDoc} */
    public List<Maze.Node> getPath() {
        return path;
    }

    /** {@inheritDoc} */
    public Maze.Node getEndPoint() {
        return startNode;
    }

    /** {@inheritDoc} */
    public Maze.Node getStartPoint() {
        return startNode;
    }

    /** {@inheritDoc} */
    public Set<Maze.Node> getVisited() {
        return visited;
    }

    /** {@inheritDoc} */
    public boolean isSolved() {
        return search != null && search.isSolved();
    }

    /** {@inheritDoc} */
    public void reset() {
        if (search != null) {
            search.reset();
            search.setParam(startNode, endNode);
        }
        visited.clear();
        path.clear();
        mustReset = false;
    }

    /** {@inheritDoc} */
    public void resize(int numCol, int numRows) {
        super.resize(numCol, numRows);
        if (startNode != null) {
            setStartPoint(startNode.getX(), startNode.getY());
        }
        if (endNode != null) {
            setEndPoint(numCol - 1, numRows - 1);
        }
    }

    /** {@inheritDoc} */
    public void setAlgorithm(SearchAlgorithm search) {
        this.search = search;
        mustReset = true;
    }

    /** {@inheritDoc} */
    public void setEndPoint(int x, int y) {
        if (y < 0 || y >= getRows() || x < 0 || x >= getCols()) {
            throw new IllegalArgumentException("Indicies must be in range");
        }

        setEndPoint(board[y][x]);
    }

    /** {@inheritDoc} */
    public void setEndPoint(Maze.Node endNode) {
        if (endNode == null) {
            throw new NullPointerException("Maze.Node cannot be null");
        } else if (endNode != null && !hasNode(endNode)) {
            throw new IllegalArgumentException("Maze must contain endpoint");
        }

        this.endNode = endNode;
        mustReset = true;
    }

    /** {@inheritDoc} */
    public void setStartPoint(int x, int y) {
        if (y < 0 || y >= getRows() || x < 0 || x >= getCols()) {
            throw new IllegalArgumentException("Indicies must be in range");
        }

        setStartPoint(board[y][x]);
    }

    /** {@inheritDoc} */
    public void setStartPoint(Maze.Node startNode) {
        if (startNode == null) {
            throw new NullPointerException("Maze.Node cannot be null");
        } else if (startNode != null && !hasNode(startNode)) {
            throw new IllegalArgumentException("Maze must contain startpoint");
        }

        this.startNode = startNode;
        mustReset = true;
    }

    /** {@inheritDoc} */
    public void update() {
        if (search == null) {
            throw new IllegalStateException("SearchAlgorithm must be set");
        } else if (startNode == null) {
            throw new IllegalStateException("Start point must be set");
        } else if (mustReset) {
            throw isSolved() ? new IllegalStateException("Cannot update a solved Maze")
                    : new IllegalStateException("Must reset after initializing or modifying a parameter");
        }

        path = search.update();
        visited.addAll(path);

        mustReset = mustReset || isSolved();
    }

    // *Private Methods*

    /**
     * Verify that a node exists within the Maze.
     * 
     * @param current the node to verify
     * @return true if the Maze contains Maze.Node else false
     * @throws NullPointerException if Maze.Node is null
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

    // *Instance Variables*
    protected Maze.Node startNode;
    protected Maze.Node endNode;

    protected SearchAlgorithm search = null;
    protected Set<Maze.Node> visited = new HashSet<>();
    protected List<Maze.Node> path = new ArrayList<>();

    protected boolean mustReset = true;
}
