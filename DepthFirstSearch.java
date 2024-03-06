import java.util.ArrayDeque;
import java.util.List;
/**
 * DepthFirstSearch
 * 
 * @author Bryce Richardson
 * @version 26-02-2024
 */
public class DepthFirstSearch extends SearchAlgorithm
{

    /**
     * Constructor for objects of class DepthFirstSearch
     */
    public DepthFirstSearch()
    {
        fringe = new ArrayDeque<>();
        reset();
    }
    
    /** {@inheritDoc} */
    protected List<Maze.Node> getNext() {
        return ((ArrayDeque<List<Maze.Node>>) fringe).pollLast();
    }
}
