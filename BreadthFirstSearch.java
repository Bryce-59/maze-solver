import java.util.ArrayDeque;
import java.util.List;
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
    
    @inheritdoc
    protected List<Maze.Node> getNext() {
        return ((ArrayDeque<List<Maze.Node>>) fringe).pollFirst();
    }
}
