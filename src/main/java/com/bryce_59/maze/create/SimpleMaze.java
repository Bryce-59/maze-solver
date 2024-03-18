package com.bryce_59.maze.create;

/**
 * @author Bryce-59
 * @version 06-03-2024
 */
public class SimpleMaze implements Maze {
    // *Constructors*

    /**
     * Constructor for objects of class Maze
     */
    public SimpleMaze() {
        this(DEFAULT_GENERATOR);
    }

    /**
     * Constructor for objects of class Maze
     * 
     * @param generator the maze-generation algorithm to use
     */
    public SimpleMaze(MazeGenerator generator) {
        this(DEFAULT_COL, DEFAULT_ROWS, generator);
    }

    /**
     * Constructor for objects of class Maze
     * 
     * @param numCol  the number of columns
     * @param numRows the number of rows
     */
    public SimpleMaze(int numCol, int numRows) {
        this(numCol, numRows, DEFAULT_GENERATOR);
    }

    /**
     * Constructor for objects of class Maze
     * 
     * @param numCol    the number of columns
     * @param numRows   the number of rows
     * @param generator the maze-generation algorithm to use
     */
    public SimpleMaze(int numCol, int numRows, MazeGenerator generator) {
        this.generator = generator;
        resize(numCol, numRows);
    }

    /**
     * Copy constructor for objects of class Maze
     * 
     * @param src the maze to copy
     */
    public SimpleMaze(SimpleMaze src) {
        this.generator = src.generator;
        this.board = src.getBoard();
    }

    // *Public Methods*

    /** {@inheritDoc} */
    public void generateMaze() {
        resize(board[0].length, board.length);
    }

    /** {@inheritDoc} */
    public Maze.Node[][] getBoard() {
        int numRows = this.board.length;
        int numCol = numRows > 0 ? this.board[0].length : 0;

        // initialize board copy
        Maze.Node[][] ret = new Maze.Node[numRows][];
        for (int i = 0; i < numRows; i++) {
            ret[i] = new Maze.Node[numCol];
            for (int j = 0; j < numCol; j++) {
                ret[i][j] = new Node(j, i);
            }
        }

        // fill board copy
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCol; j++) {
                if (i < numRows - 1 && this.board[i][j].hasEdge(this.board[i + 1][j])) {
                    ret[i][j].addEdge(ret[i + 1][j]);
                }
                if (j < numCol - 1 && this.board[i][j].hasEdge(this.board[i][j + 1])) {
                    ret[i][j].addEdge(ret[i][j + 1]);
                }
                ret[i][j].setDepth(this.board[i][j].getDepth());
            }
        }
        return ret;
    }

    /** {@inheritDoc} */
    public int getCols() {
        return board[0].length;
    }

    /** {@inheritDoc} */
    public int getRows() {
        return board.length;
    }

    /** {@inheritDoc} */
    public void resize(int numCol, int numRows) {
        if (numCol <= 0 || numRows <= 0) {
            throw new IllegalArgumentException("Invalid dimensions");
        }

        this.board = generator.generateMaze(numCol, numRows);
    }

    // *Instance Variables*
    protected Maze.Node[][] board;
    private MazeGenerator generator;

    // SETTINGS
    private static final int DEFAULT_COL = 10;
    private static final int DEFAULT_ROWS = 10;
    private static final MazeGenerator DEFAULT_GENERATOR = new GrowingTreeGenerator(0.7);
}