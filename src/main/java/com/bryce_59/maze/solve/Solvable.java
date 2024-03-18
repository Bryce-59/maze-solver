package com.bryce_59.maze.solve;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bryce_59.maze.create.*;

/**
 * Solvable interface
 * 
 * @author Bryce-59
 * @version 06-03-2024
 */
public interface Solvable {

    // *Public Methods*
    
    /**
     * Get the current path
     * 
     * @return the current path
     */
    public List<Maze.Node> getPath();

    /**
     * Get the end point
     * 
     * @return the start point
     */
    public Maze.Node getEndPoint();

    /**
     * Get the start point
     * 
     * @return the start point
     */
    public Maze.Node getStartPoint();

    /**
     * Get the visited list
     * 
     * @return the visited list
     */
    public Set<Maze.Node> getVisited();

    /**
     * Verify if the Maze is in a completed state
     * 
     * @return true if solved else false
     */
    public boolean isSolved();

    /**
     * Reset the Object to its base state
     */
    public void reset();

    /**
     * Set the SearchAlgorithm
     * 
     * @param search the SearchAlgorithm
     */
    public void setAlgorithm(SearchAlgorithm search);

    /**
     * Set the end point
     * 
     * @param x the x-position
     * @param y the y-position
     * @throws IllegalArgumentException if x or y are out of range
     */
    public void setEndPoint(int x, int y);

    /**
     * Set the end point
     * 
     * @param endPoint the end point
     * @throws NullPointerException     if node is null
     * @throws IllegalArgumentException if node is not valid
     */
    public void setEndPoint(Maze.Node endNode);

    /**
     * Set the start point
     * 
     * @param x the x-position
     * @param y the y-position
     * @throws IllegalArgumentException if x or y are out of range
     */
    public void setStartPoint(int x, int y);

    /**
     * Set the start point
     * 
     * @param endPoint the end point
     * @throws NullPointerException     if node is null
     * @throws IllegalArgumentException if node is not valid
     */
    public void setStartPoint(Maze.Node startNode);

    /**
     * Update the Object state
     * 
     * @throws IllegalStateException if a parameter is not set, or if the Maze is
     *                               solved
     */
    public void update();
}
