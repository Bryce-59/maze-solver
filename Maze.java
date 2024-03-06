import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * Write a description of class Maze here.
 * a
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Maze
{
    // *Constructors*
    
    /**
     * Constructor for objects of class Maze
     */
    public Maze() 
    {
        resize(DEFAULT_SIZE, DEFAULT_SIZE);
    }
    
    /**
     * Constructor for objects of class Maze
     * 
     * @param numCol  the number of columns
     * @param numRows  the number of rows
     */
    public Maze (int numCol, int numRows) 
    {
        resize(numCol, numRows);
    }

    /**
     * Constructor for objects of class Maze
     * 
     * @param src  the maze to copy
     */
    public Maze (Maze src) 
    {
        board = new Maze.Node[src.board.length][];
        for (int i = 0; i < src.board.length; i++) {
            board[i] = Arrays.copyOf(src.board[i], src.board[i].length);
        }
    }
    
    // *Public Methods*
    
    /**
     * Generate a new Maze
     */
    public void generateMaze() {
        generateTree(0.75);
    }

    /**
     * Get a copy of the Maze board
     * 
     * @return a copy of the board
     */
    public Maze.Node[][] getBoard() {
        if (board == null) {
            return null;
        }

        final Maze.Node[][] result = new Maze.Node[board.length][];
        for (int i = 0; i < board.length; i++) {
            result[i] = Arrays.copyOf(board[i], board[i].length);
        }
        return result;
    }
    
    /**
     * Get the number of columns in the Maze
     * 
     * @return the number of columns
     */
    public int getCols() {
        return board[0].length;
    }
    
    /**
     * Get the number of rows in the Maze
     * 
     * @return the number of rows
     */
    public int getRows() {
        return board.length;
    }
    
    /**
     * Initialize the Maze board
     * 
     * @param  numCol the number of columns
     * @param  numRows the number of rows
     * @throws IllegalArgumentException  if numCol < 0 or numRows < 0
     */
    public void resize(int numCol, int numRows) {
        // preconditions
        if (numCol <= 0 || numRows <= 0) {
            throw new IllegalArgumentException("Invalid dimensions");
        }
        
        // resize
        board = new Node[numRows][numCol];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = new Node(j, i);
            }
        }
    }
    
    // *Private Methods*
    /**
     * Verify that a node exists within the Maze.
     * 
     * @param current  the node to verify
     * @return  true if the Maze contains Node else false
     * @throws NullPointerException  if Node is null
     */
    protected boolean hasNode(Maze.Node current) {
        // preconditions
        if (current == null) {
            throw new NullPointerException("Node cannot be null");
        }
                
        // verify
        int x = current.getX();
        int y = current.getY();
        if (y < 0 || y >= board.length || x < 0 || x >= board[0].length) {
            return false;
        }
        
        return board[y][x] != null && board[y][x].equals(current);
    }

    /**
     * Generate a new Maze with Prim's Algorithm
     */
    private void generatePrim() {
        generateTree(1);
    }

    /**
     * Generate a new Maze with the Recursive Backtracking Algorithm
     */
    private void generateRecursive() {
        generateTree(0);
    }

    /**
     * Generate a new Maze with the Growing Tree Algorithm
     */
    private void generateTree(double isRandom) {
        if (getRows() > 1 && getCols() > 1) {
            ArrayList<Maze.Node> visited = new ArrayList<>();
            
            ArrayList<Maze.Node> branch = new ArrayList<>();
            Maze.Node startNode = board[(int) (Math.random() * board.length)][(int) (Math.random() * board[0].length)];
            visited.add(startNode);
            branch.add(startNode);

            while (!branch.isEmpty()) {
                Maze.Node current = Math.random() < isRandom ? branch.get((int) (Math.random() * branch.size())) : branch.get(branch.size() - 1);

                int x = current.getX();
                int y = current.getY();

                ArrayList<Maze.Node> tmp = new ArrayList<>();
                if (x > 0 && !visited.contains(board[y][x-1])) {
                    tmp.add(board[y][x-1]);
                }
                if (x < board[0].length - 1 && !visited.contains(board[y][x+1])) {
                    tmp.add(board[y][x+1]);
                }
                if (y > 0 && !visited.contains(board[y-1][x])) {
                    tmp.add(board[y-1][x]);
                }
                if (y < board.length - 1 && !visited.contains(board[y+1][x])) {
                    tmp.add(board[y+1][x]);
                }

                if (tmp.isEmpty()) {
                    branch.remove(current);
                    continue;
                }
                
                Maze.Node next = tmp.get((int) (Math.random() * tmp.size()));
                visited.add(next);
                branch.add(next);

                current.addEdge(next);
                next.addEdge(current);
                

            }
        }
    }

    /**
     * Generate a new Maze with the Wilson's Algorithm
     */
    private void generateWilson() {
        if (getRows() > 1 && getCols() > 1) {
            ArrayList<Maze.Node> unvisited = new ArrayList<>();
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    unvisited.add(board[i][j]);
                }
            }
            
            Maze.Node startNode = unvisited.get((int) (Math.random() * unvisited.size()));
            unvisited.remove(startNode);
            
            while(!unvisited.isEmpty()) {
                Maze.Node current = unvisited.get((int) (Math.random() * unvisited.size()));                
                ArrayList<Maze.Node> branch = new ArrayList<>();
                while(unvisited.contains(current)) {
                    branch.add(current);
                    Maze.Node next = branch.size() >= 1 ? sampleAdjacent(current, branch.get(branch.size() - 1)) : sampleAdjacent(current);
                    if (branch.contains(next)) {
                        Maze.Node prev = null;
                        while(!next.equals(prev)) {
                            prev = branch.remove(branch.size() - 1);
                        }
                    }
                    current = next;
                }
                branch.add(current);
                unvisited.removeAll(branch);
                for (int i = 0; i < branch.size() - 1; i++) {
                    branch.get(i).addEdge(branch.get(i + 1));
                    branch.get(i + 1).addEdge(branch.get(i));
                }
            }
        }
    }
    
    /**
     * Get a list of nodes adjacent to the parameter
     * 
     * @param current  the node to get adjacent Nodes for
     * @return  a list of adjacent nodes
     * @throws NullPointerException  if node is null
     */
    private List<Node> getAdjacent(Node current) {
        // preconditions
        if (current == null) {
            throw new NullPointerException("Node cannot be null");
        }
        
        int x = current.getX();
        int y = current.getY();
        
        List<Node> tmp = new ArrayList<>();
        if (x > 0) {
            tmp.add(board[y][x-1]);
        }
        if (x < board[0].length - 1) {
            tmp.add(board[y][x+1]);
        }
        if (y > 0) {
            tmp.add(board[y-1][x]);
        }
        if (y < board.length - 1) {
            tmp.add(board[y+1][x]);
        }
           
        return tmp;
    }
    
    /**
     * Choose a random neighbor of a node.
     * 
     * @param current  the current node
     * @return  a random neighbor of the node
     * @throws NullPointerException  if node is null
     */
    private Node sampleAdjacent(Node current)
    {
        // preconditions
        if (current == null) {
            throw new NullPointerException("Node cannot be null");
        }
        
        // sample
        List<Node> tmp = getAdjacent(current);
        return tmp.size() > 0 ? tmp.get((int) (Math.random() * tmp.size())) : null;
    }
    
    /**
     * Choose a random neighbor of a Node.
     * 
     * @param current  the current Node
     * @param exclude  the neighbor to exclude
     * @return  a random neighbor of the current Node
     * @throws NullPointerException  if Node is null
     * @throws InvalidInputException  if exclude is not adjacent to current
     */
    private Node sampleAdjacent(Node current, Node exclude)
    {
        // preconditions
        if (current == null) {
            throw new NullPointerException("Node cannot be null");
        } else if (exclude == null) {
            return sampleAdjacent(current);
        }
        
        // sample
        List<Node> tmp = getAdjacent(current);
        tmp.remove(exclude);
        return tmp.size() > 0 ? tmp.get((int) (Math.random() * tmp.size())) : null;
    }
    
    // *Instance Variables*
    protected Node[][] board = new Node[0][0];

    // settings
    private final int DEFAULT_SIZE = 10;


    /**
     * Node class for Maze objects.
     * 
     * @author Bryce-59
     * @version 26-02-2024
     */
    protected class Node
    {
        // *Constructors*
        
        /**
         * Constructor for objects of class Node
         * 
         * @param x  the x-positin
         * @param y  the y-position
         */
        public Node(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
        
        // *Public Methods*
        
        /**
         * Add another Node object to the edge set of this Node
         * 
         * @param other  the edge to add
         */
        public void addEdge(Node other)
        {
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
         * Return the set of edges for the Node
         * @return  the set of edges
         */
        public Set<Node> getEdgeSet() {
            return edges.keySet();
        }
        
        /**
         * Return the x-position of the Node
         * @return   the x-position
         */
        public int getX()
        {
            return x;
        }
        
        /**
         * Return the y-position of the Node
         * @return   the x-position
         */
        public int getY()
        {
            return y;
        }
    
        /**
         * Verify whether this Node object contains another Node object in its edge set
         * @return   true if the edge set contains node else false
         */
        public boolean hasEdge(Node other)
        {
            // preconditions
            if (other == null) {
                throw new NullPointerException("Cannot add a null edge");
            }
            
            // verify edge
            return edges.containsKey(other);
        }

        public String toString() {
            return "("+x+","+y+")";
        }
        
        // *Instance Variables*
        private Map<Node, Integer> edges = new HashMap<>();
        private int x;
        private int y;
    }

}