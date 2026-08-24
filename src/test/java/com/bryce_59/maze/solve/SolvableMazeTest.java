package com.bryce_59.maze.solve;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.bryce_59.maze.create.Maze;
import com.bryce_59.maze.create.MazeTestUtils;

class SolvableMazeTest {

    /**
     * Construct a deterministic tree-shaped maze.
     *
     * Coordinates:
     *
     * (0,0) -- (1,0) -- (2,0)
     *   |       |         |
     * (0,1)    (1,1)    (2,1)
     *   |       |
     * (0,2)    (1,2) -- (2,2)
     *
     * with the actual edges deliberately forming a tree:
     *
     * (0,0)-(1,0)
     * (0,0)-(0,1)
     * (0,1)-(0,2)
     * (1,0)-(2,0)
     * (1,0)-(1,1)
     * (2,0)-(2,1)
     * (1,1)-(1,2)
     * (1,2)-(2,2)
     */
    private static Maze.Node[][] createTestBoard() {
        Maze.Node[][] board = MazeTestUtils.createGrid(3, 3);

        MazeTestUtils.connect(
            board[0][0],
            board[1][0]
        );

        MazeTestUtils.connect(
            board[0][0],
            board[0][1]
        );

        MazeTestUtils.connect(
            board[0][1],
            board[0][2]
        );

        MazeTestUtils.connect(
            board[0][1],
            board[1][1]
        );

        MazeTestUtils.connect(
            board[0][2],
            board[1][2]
        );

        MazeTestUtils.connect(
            board[1][1],
            board[2][1]
        );

        MazeTestUtils.connect(
            board[2][0],
            board[2][1]
        );

        MazeTestUtils.connect(
            board[2][1],
            board[2][2]
        );

        return board;
    }

    /**
     * Replace the generated board with a deterministic board for testing.
     */
    private static class FixedSolvableMaze extends SolvableMaze {

        FixedSolvableMaze(Maze.Node[][] board) {
            super(board[0].length, board.length);
            this.board = board;
        }
    }

    private static FixedSolvableMaze createMaze() {
        FixedSolvableMaze maze = new FixedSolvableMaze(
            createTestBoard()
        );

        maze.setStartPoint(0, 0);
        maze.setEndPoint(2, 2);

        return maze;
    }

    @Test
    void endpointsAreReturnedCorrectly() {
        SolvableMaze maze = createMaze();

        assertNotNull(maze.getStartPoint());
        assertNotNull(maze.getEndPoint());

        assertEquals(0, maze.getStartPoint().getX());
        assertEquals(0, maze.getStartPoint().getY());

        assertEquals(2, maze.getEndPoint().getX());
        assertEquals(2, maze.getEndPoint().getY());
    }

    @Test
    void invalidStartCoordinatesAreRejected() {
        SolvableMaze maze = new SolvableMaze(5, 5);

        assertThrows(
            IllegalArgumentException.class,
            () -> maze.setStartPoint(-1, 0)
        );

        assertThrows(
            IllegalArgumentException.class,
            () -> maze.setStartPoint(0, 5)
        );
    }

    @Test
    void invalidEndCoordinatesAreRejected() {
        SolvableMaze maze = new SolvableMaze(5, 5);

        assertThrows(
            IllegalArgumentException.class,
            () -> maze.setEndPoint(-1, 0)
        );

        assertThrows(
            IllegalArgumentException.class,
            () -> maze.setEndPoint(0, 5)
        );
    }

    @Test
    void nullStartPointIsRejected() {
        SolvableMaze maze = new SolvableMaze(5, 5);

        assertThrows(
            NullPointerException.class,
            () -> maze.setStartPoint((Maze.Node) null)
        );
    }

    @Test
    void nullEndPointIsRejected() {
        SolvableMaze maze = new SolvableMaze(5, 5);

        assertThrows(
            NullPointerException.class,
            () -> maze.setEndPoint((Maze.Node) null)
        );
    }

    @Test
    void changingAlgorithmRequiresReset() {
        SolvableMaze maze = createMaze();

        maze.setAlgorithm(new BreadthFirstSearch());

        assertThrows(
            IllegalStateException.class,
            maze::update
        );
    }

    @Test
    void resetAllowsSearchToBegin() {
        SolvableMaze maze = createMaze();

        maze.setAlgorithm(new BreadthFirstSearch());
        maze.reset();

        assertFalse(maze.isSolved());

        maze.update();

        assertFalse(maze.getPath().isEmpty());
    }

    @Test
    void breadthFirstSearchFindsValidPath() {
        SolvableMaze maze = createMaze();

        maze.setAlgorithm(new BreadthFirstSearch());
        maze.reset();

        solve(maze);

        assertTrue(maze.isSolved());
        assertTrue(MazeTestUtils.isValidPath(maze.getPath()));

        assertEquals(
            maze.getStartPoint(),
            maze.getPath().get(0)
        );

        assertEquals(
            maze.getEndPoint(),
            maze.getPath().get(maze.getPath().size() - 1)
        );
    }

    @Test
    void breadthFirstSearchFindsShortestPath() {
        SolvableMaze maze = createMaze();

        maze.setAlgorithm(new BreadthFirstSearch());
        maze.reset();

        solve(maze);

        // The shortest path is:
        // (0,0) -> (1,0) -> (1,1) -> (1,2) -> (2,2)
        assertEquals(5, maze.getPath().size());
    }

    @Test
    void depthFirstSearchFindsValidPath() {
        SolvableMaze maze = createMaze();

        maze.setAlgorithm(new DepthFirstSearch());
        maze.reset();

        solve(maze);

        assertTrue(maze.isSolved());
        assertTrue(MazeTestUtils.isValidPath(maze.getPath()));

        assertEquals(
            maze.getStartPoint(),
            maze.getPath().get(0)
        );

        assertEquals(
            maze.getEndPoint(),
            maze.getPath().get(maze.getPath().size() - 1)
        );
    }

    @Test
    void aStarSearchFindsValidShortestPath() {
        SolvableMaze maze = createMaze();

        maze.setAlgorithm(new AStarSearch());
        maze.reset();

        solve(maze);

        assertTrue(maze.isSolved());
        assertTrue(MazeTestUtils.isValidPath(maze.getPath()));

        assertEquals(
            5,
            maze.getPath().size()
        );
    }

    @Test
    void solvedMazeCannotBeUpdatedFurther() {
        SolvableMaze maze = createMaze();

        maze.setAlgorithm(new BreadthFirstSearch());
        maze.reset();

        solve(maze);

        assertTrue(maze.isSolved());

        assertThrows(
            IllegalStateException.class,
            maze::update
        );
    }

    @Test
    void changingStartPointRequiresReset() {
        SolvableMaze maze = createMaze();

        maze.setAlgorithm(new BreadthFirstSearch());
        maze.reset();

        maze.setStartPoint(0, 1);

        assertThrows(
            IllegalStateException.class,
            maze::update
        );
    }

    @Test
    void changingEndPointRequiresReset() {
        SolvableMaze maze = createMaze();

        maze.setAlgorithm(new BreadthFirstSearch());
        maze.reset();

        maze.setEndPoint(2, 1);

        assertThrows(
            IllegalStateException.class,
            maze::update
        );
    }

    @Test
    void changingDimensionsResetsMazeState() {
        SolvableMaze maze = new SolvableMaze(5, 5);

        maze.setStartPoint(1, 1);
        maze.setEndPoint(4, 4);
        maze.setAlgorithm(new BreadthFirstSearch());
        maze.reset();

        maze.resize(8, 6);

        assertEquals(8, maze.getCols());
        assertEquals(6, maze.getRows());

        assertEquals(1, maze.getStartPoint().getX());
        assertEquals(1, maze.getStartPoint().getY());

        assertEquals(7, maze.getEndPoint().getX());
        assertEquals(5, maze.getEndPoint().getY());
    }

    @Test
    void clonePreservesDimensionsAndEndpoints() {
        SolvableMaze original = createMaze();

        SolvableMaze clone = original.clone();

        assertEquals(original.getRows(), clone.getRows());
        assertEquals(original.getCols(), clone.getCols());

        assertEquals(
            original.getStartPoint().getX(),
            clone.getStartPoint().getX()
        );

        assertEquals(
            original.getStartPoint().getY(),
            clone.getStartPoint().getY()
        );

        assertEquals(
            original.getEndPoint().getX(),
            clone.getEndPoint().getX()
        );

        assertEquals(
            original.getEndPoint().getY(),
            clone.getEndPoint().getY()
        );
    }

    private static void solve(SolvableMaze maze) {
        int steps = 0;

        while (!maze.isSolved() && steps < 1000) {
            maze.update();
            steps++;
        }
        System.out.println("Finished");

        assertTrue(
            maze.isSolved(),
            "Maze did not solve within 1000 update calls"
        );
    }
}