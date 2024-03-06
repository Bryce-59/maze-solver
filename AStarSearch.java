import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
/**
 * AStarSearch
 * 
 * @author Bryce-59
 * @version 26-02-24
 */
public class AStarSearch extends SearchAlgorithm
{
    /**
     * Constructor for objects of class AStarSearch
     */
    public AStarSearch()
    {
        fringe = new PriorityQueue<>(new ManhattanComparator());
    }

    /**
     * Constructor for objects of class AStarSearch
     * 
     * @param cmp  Comparator to use for ranking individual paths
     */
    public AStarSearch(Comparator<List<Maze.Node>> cmp)
    {
        fringe = new PriorityQueue<>(cmp);
    }
    
    /** {@inheritDoc} */
    protected List<Maze.Node> getNext() {
        return ((PriorityQueue<List<Maze.Node>>) fringe).poll();
    }
    
    /**
     * A Comparator for AStarSearch using Manhattan Distance as a comparison
     */
    private class ManhattanComparator implements Comparator<List<Maze.Node>> {
        @Override
        public int compare(List<Maze.Node> lhs, List<Maze.Node> rhs) {
            Maze.Node lhs_prev = lhs.get(lhs.size() - 1);
            Maze.Node rhs_prev = rhs.get(rhs.size() - 1);
            int lhs_dist = lhs.size() + (Math.abs(getEnd().getX() - lhs_prev.getX()) + Math.abs(getEnd().getY() - lhs_prev.getY()));
            int rhs_dist = rhs.size() + (Math.abs(getEnd().getX() - rhs_prev.getX()) + Math.abs(getEnd().getY() - rhs_prev.getY()));
            return lhs_dist - rhs_dist;
        }
    }

    /**
     * A Comparator for AStarSearch using Euclidean Distance as a comparison
     */
    // private class EuclideanComparator implements Comparator<List<Maze.Node>> {
    //     @Override
    //     public int compare(List<Maze.Node> lhs, List<Maze.Node> rhs) {
    //         Maze.Node lhs_prev = lhs.get(lhs.size() - 1);
    //         Maze.Node rhs_prev = rhs.get(rhs.size() - 1);
    //         int lhs_dist = lhs.size() + (int) (Math.pow(getEnd().getX() - lhs_prev.getX(), 2) + Math.pow(getEnd().getY() - lhs_prev.getY(), 2));
    //         int rhs_dist = rhs.size() + (int) (Math.pow(getEnd().getX() - rhs_prev.getX(), 2) + Math.pow(getEnd().getY() - rhs_prev.getY(), 2));
    //         return lhs_dist - rhs_dist;
    //     }
    // }
}
