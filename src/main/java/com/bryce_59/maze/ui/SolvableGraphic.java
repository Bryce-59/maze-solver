package com.bryce_59.maze.ui;

import java.awt.*;
import java.util.List;
import java.util.Set;

import javax.swing.*;

import com.bryce_59.maze.create.*;
import com.bryce_59.maze.solve.*;

abstract class SolvableGraphic extends JComponent implements Solvable, Cloneable {
    
    // *Public Methods*

    /** {@inheritDoc} */
    public void setAlgorithm(SearchAlgorithm search) {
        getSolvable().setAlgorithm(search);
    }

    /** {@inheritDoc} */
    public void setEndPoint(int x, int y) {
        getSolvable().setEndPoint(x, y);
    }

    /** {@inheritDoc} */
    public boolean isSolved() {
        return getSolvable().isSolved();
    }

    /** {@inheritDoc} */
    public void reset() {
        getSolvable().reset();
    }

    /** {@inheritDoc} */
    public void setStartPoint(int x, int y) {
        getSolvable().setStartPoint(x, y);
    }

    /** {@inheritDoc} */
    public void update() {
        getSolvable().update();
    }

    /** {@inheritDoc} */
    public List<Maze.Node> getPath() {
        return getSolvable().getPath();
    }

    /** {@inheritDoc} */
    public Maze.Node getEndPoint() {
        return getSolvable().getEndPoint();
    }
    
    /** {@inheritDoc} */
    @Override
    public Dimension getPreferredSize() {
        // so that our GUI is big enough
        return new Dimension(400, 400);
    }

    /** {@inheritDoc} */
    public Maze.Node getStartPoint() {
        return getSolvable().getStartPoint();
    }

    /** {@inheritDoc} */
    public Set<Maze.Node> getVisited() {
        return getSolvable().getVisited();
    }

    /** {@inheritDoc} */
    public void setEndPoint(Maze.Node endNode) {
        getSolvable().setEndPoint(endNode);
    }

    /** {@inheritDoc} */
    public void setStartPoint(Maze.Node startNode) {
        getSolvable().setStartPoint(startNode);
    }

    //  *Abstract Methods*

    /** {@inheritDoc} */
    @Override
    public abstract SolvableGraphic clone();

    /**
     * Helper method which returns the Object that this SolvableGraphic wraps
     * 
     * @return the Solvable Object
     */
    protected abstract Solvable getSolvable();
}