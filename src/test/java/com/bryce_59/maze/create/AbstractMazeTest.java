package com.bryce_59.maze.create;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class AbstractMazeTest {

    private static class TestMaze extends AbstractMaze {

        TestMaze(int cols, int rows) {
            super(cols, rows);
        }

        Maze.Node[][] rawBoard() {
            return board;
        }
    }

    @Test
    void getBoardReturnsCorrectDimensions() {
        TestMaze maze = new TestMaze(12, 8);

        Maze.Node[][] board = maze.getBoard();

        assertEquals(8, board.length);
        assertEquals(12, board[0].length);
    }

    @Test
    void getBoardReturnsNewNodeObjects() {
        TestMaze maze = new TestMaze(10, 10);

        Maze.Node[][] first = maze.getBoard();
        Maze.Node[][] second = maze.getBoard();

        for (int y = 0; y < maze.getRows(); y++) {
            for (int x = 0; x < maze.getCols(); x++) {
                assertNotSame(
                        first[y][x],
                        second[y][x]);
            }
        }
    }

    @Test
    void internalGeneratedMazeIsConnected() {
        TestMaze maze = new TestMaze(20, 20);

        assertTrue(
                MazeTestUtils.isConnected(maze.rawBoard()));
    }

    @Test
    void internalGeneratedMazeHasReciprocalEdges() {
        TestMaze maze = new TestMaze(20, 20);

        assertTrue(
                MazeTestUtils.hasReciprocalEdges(maze.rawBoard()));
    }

    @Test
    void getBoardPreservesReciprocalEdges() {
        TestMaze maze = new TestMaze(20, 20);

        Maze.Node[][] board = maze.getBoard();

        assertTrue(
                MazeTestUtils.hasReciprocalEdges(board));
    }

    @Test
    void generatedMazeHasTreeNumberOfEdges() {
        TestMaze maze = new TestMaze(20, 20);

        int nodes = maze.getRows() * maze.getCols();
        int expectedEdges = 2 * (nodes - 1);

        assertEquals(
                expectedEdges,
                MazeTestUtils.countDirectedEdges(maze.rawBoard()));
    }

    @Test
    void rejectsInvalidDimensions() {
        assertThrows(IllegalArgumentException.class,
                () -> new TestMaze(0, 10));

        assertThrows(IllegalArgumentException.class,
                () -> new TestMaze(10, 0));

        assertThrows(IllegalArgumentException.class,
                () -> new TestMaze(-1, 10));
}
}