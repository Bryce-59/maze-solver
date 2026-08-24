package com.bryce_59.maze.create;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class GrowingTreeGeneratorTest {

    @Test
    void generatedMazeHasRequestedDimensions() {
        GrowingTreeGenerator generator = new GrowingTreeGenerator(0.5);

        Maze.Node[][] board = generator.generateMaze(12, 8);

        assertEquals(8, board.length);
        assertEquals(12, board[0].length);
    }

    @Test
    void generatedMazeContainsAllNodes() {
        GrowingTreeGenerator generator = new GrowingTreeGenerator(0.5);

        Maze.Node[][] board = generator.generateMaze(10, 10);

        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                assertEquals(x, board[y][x].getX());
                assertEquals(y, board[y][x].getY());
            }
        }
    }

    @Test
    void generatedMazeIsConnected() {
        GrowingTreeGenerator generator = new GrowingTreeGenerator(0.5);

        Maze.Node[][] board = generator.generateMaze(20, 20);

        assertTrue(MazeTestUtils.isConnected(board));
    }

    @Test
    void generatedMazeHasReciprocalEdges() {
        GrowingTreeGenerator generator = new GrowingTreeGenerator(0.5);

        Maze.Node[][] board = generator.generateMaze(20, 20);

        assertTrue(MazeTestUtils.hasReciprocalEdges(board));
    }

    @Test
    void generatedMazeHasTreeNumberOfEdges() {
        GrowingTreeGenerator generator = new GrowingTreeGenerator(0.5);

        Maze.Node[][] board = generator.generateMaze(20, 20);

        int nodes = 20 * 20;
        int expectedDirectedEdges = 2 * (nodes - 1);

        assertEquals(
            expectedDirectedEdges,
            MazeTestUtils.countDirectedEdges(board)
        );
    }

    @Test
    void zeroRandomnessStillGeneratesConnectedMaze() {
        GrowingTreeGenerator generator = new GrowingTreeGenerator(0.0);

        Maze.Node[][] board = generator.generateMaze(20, 20);

        assertTrue(MazeTestUtils.isConnected(board));
        assertTrue(MazeTestUtils.hasReciprocalEdges(board));
    }

    @Test
    void fullRandomnessStillGeneratesConnectedMaze() {
        GrowingTreeGenerator generator = new GrowingTreeGenerator(1.0);

        Maze.Node[][] board = generator.generateMaze(20, 20);

        assertTrue(MazeTestUtils.isConnected(board));
        assertTrue(MazeTestUtils.hasReciprocalEdges(board));
    }

    @Test
    void randomnessOutsideRangeIsClamped() {
        GrowingTreeGenerator negative = new GrowingTreeGenerator(-1.0);
        GrowingTreeGenerator greaterThanOne = new GrowingTreeGenerator(2.0);

        assertTrue(
            MazeTestUtils.isConnected(
                negative.generateMaze(10, 10)
            )
        );

        assertTrue(
            MazeTestUtils.isConnected(
                greaterThanOne.generateMaze(10, 10)
            )
        );
    }

    @Test
    void oneByOneMazeContainsItsSingleNode() {
        GrowingTreeGenerator generator = new GrowingTreeGenerator(0.5);

        Maze.Node[][] board = generator.generateMaze(1, 1);

        assertEquals(1, board.length);
        assertEquals(1, board[0].length);
        assertEquals(0, board[0][0].getX());
        assertEquals(0, board[0][0].getY());
    }

    @Test
    void oneByTenMazeIsConnected() {
        GrowingTreeGenerator generator = new GrowingTreeGenerator(0.5);

        Maze.Node[][] board = generator.generateMaze(1, 10);

        assertTrue(MazeTestUtils.isConnected(board));
}
}