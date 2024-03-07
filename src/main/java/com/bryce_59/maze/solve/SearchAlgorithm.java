package com.bryce_59.maze.solve;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import com.bryce_59.maze.create.*;

/**
 * Abstract class SearchAlgorithm
 * 
 * @author Bryce Richardson
 * @version 06-03-2024
 */
public abstract class SearchAlgorithm
{

    /**
     * Get the start point
     * 
     * @return the start point
     */
    public Maze.Node getStart() {
        return start;
     }
    
    /**
     * Get the end point
     * 
     * @return the end point
     */
    public Maze.Node getEnd() {
        return end;
     }

   /**
    * Verify if the Algorithm is in a completed state
    * 
    * @return  true if solved else false
    */
   public boolean isSolved() {
       return isSolved;
   }
   
   /**
    * Reset the Search Algorithm to its base state
    */
   public void reset() {
       isSolved = false;
       fringe.clear();
       
       List<Maze.Node> tmp = new ArrayList<>();
       tmp.add(start);
       fringe.add(tmp);
   }
   
   /**
    * Set the start and end points
    * 
    * @param start  the start point
    * @param end  the endpoint
    */
   public void setParam(Maze.Node start, Maze.Node end) {
       this.start = start;
       this.end = end;
       reset();
   }
   
   /**
    * Update the state of the Search Algorithm
    * 
    * @return the current path
    */
   public List<Maze.Node> update() {
        // result if end does not exist
        if (fringe.isEmpty()) {
            isSolved = true;
            return new ArrayList<>();
        }

        // result if end is found
        List<Maze.Node> path = getNext();
        Maze.Node prev = path.get(path.size() - 1);
        if (prev.equals(end)) {
            isSolved = true;
            return path;
        }
        
       // calculate intermediate step
       for (Maze.Node n : prev.getEdgeSet()) {
           if (!path.contains(n)) {
               List<Maze.Node> next = new ArrayList<>(path);
               next.add(n);
               fringe.add(next);
           }
       }
       return path;
   }

   // *Abstract Methods*
   
   /**
    * Gets the next node to visit based on the current algorithm
    *
    * @return the next node
    */
   abstract List<Maze.Node> getNext();
   
   // *Instance Variables*

   private Maze.Node start;
   private Maze.Node end;
   protected Collection<List<Maze.Node>> fringe;
   
   private boolean isSolved;
}
