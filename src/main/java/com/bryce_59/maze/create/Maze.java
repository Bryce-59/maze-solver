package com.bryce_59.maze.create;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Bryce-59
 * @version 18-03-2024
 */
public interface Maze {
    // *Constructors*

    /**
     * Generate a new Maze
     */
    public void generateMaze();

    /**
     * Get a copy of the Maze board
     * 
     * @return a copy of the board
     */
    public Maze.Node[][] getBoard();

    /**
     * Get the number of columns in the Maze
     * 
     * @return the number of columns
     */
    public int getCols();

    /**
     * Get the number of rows in the Maze
     * 
     * @return the number of rows
     */
    public int getRows();

    /**
     * Initialize the Maze board
     * 
     * @param numCol  the number of columns
     * @param numRows the number of rows
     * @throws IllegalArgumentException if numCol < 0 or numRows < 0
     */
    public void resize(int numCol, int numRows);

    /**
     * Maze.Node class for Maze objects.
     * 
     * @author Bryce-59
     * @version 26-02-2024
     */
    static class Node {
        // *Constructors*

        /**
         * Constructor for objects of class Maze.Node
         * 
         * @param x the x-positin
         * @param y the y-position
         */
        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        // *Public Methods*

        /**
         * Return the set of edges for the Maze.Node
         * 
         * @return the set of edges
         */
        public Set<Maze.Node> getEdgeSet() {
            return edges.keySet();
        }

        /**
         * Return the depth of the Maze.Node
         * 
         * @return the depth
         */
        public int getDepth() {
            return depth;
        }

        /**
         * Return the x-position of the Maze.Node
         * 
         * @return the x-position
         */
        public int getX() {
            return x;
        }

        /**
         * Return the y-position of the Maze.Node
         * 
         * @return the x-position
         */
        public int getY() {
            return y;
        }

        /**
         * Verify whether this Maze.Node object contains another Maze.Node object in its
         * edge set
         * 
         * @return true if the edge set contains node else false
         */
        public boolean hasEdge(Node other) {
            // preconditions
            if (other == null) {
                throw new NullPointerException("Cannot add a null edge");
            }

            // verify edge
            return edges.containsKey(other);
        }

        /** {@inheritDoc} */
        public String toString() {
            return "(" + x + "," + y + ")";
        }

        // *Protected Methods*

        /**
         * Add another Maze.Node object to the edge set of this Maze.Node
         * 
         * @param other the edge to add
         */
        protected void addEdge(Maze.Node other) {
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
         * Modify the depth of the Node
         * 
         * @param the new depth
         */
        protected void setDepth(int depth) {
            this.depth = depth;
        }

        // *Instance Variables*
        private Map<Node, Integer> edges = new HashMap<>();
        private int depth;
        private int x;
        private int y;
    }
}