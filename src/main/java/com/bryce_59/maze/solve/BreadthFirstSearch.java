package com.bryce_59.maze.solve;

import java.util.ArrayDeque;
import java.util.List;

import com.bryce_59.maze.create.Maze;
/**
 * BreadthFirstSearch
 * 
 * @author Bryce-59
 * @version 26-02-24
 */
public class BreadthFirstSearch extends SearchAlgorithm
{
    /**
     * Constructor for objects of class BreadthFirstSearch
     */
    public BreadthFirstSearch()
    {
        fringe = new ArrayDeque<>();
        reset();
    }
    
    /** {@inheritDoc} */
    protected List<Maze.Node> getNext() {
        return ((ArrayDeque<List<Maze.Node>>) fringe).pollFirst();
    }
}
