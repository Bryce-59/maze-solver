package com.bryce_59.maze.create;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class MazeNodeTest {

    @Test
    void constructorStoresCoordinates() {
        Maze.Node node = new Maze.Node(4, 7);

        assertEquals(4, node.getX());
        assertEquals(7, node.getY());
        assertEquals("(4,7)", node.toString());
    }

    @Test
    void newNodeHasNoEdges() {
        Maze.Node node = new Maze.Node(0, 0);

        assertTrue(node.getEdgeSet().isEmpty());
    }

    @Test
    void addEdgeAddsEdge() {
        Maze.Node first = new Maze.Node(0, 0);
        Maze.Node second = new Maze.Node(1, 0);

        first.addEdge(second);

        assertTrue(first.hasEdge(second));
        assertEquals(1, first.getEdgeSet().size());
    }

    @Test
    void duplicateEdgeIsIgnored() {
        Maze.Node first = new Maze.Node(0, 0);
        Maze.Node second = new Maze.Node(1, 0);

        first.addEdge(second);
        first.addEdge(second);

        assertEquals(1, first.getEdgeSet().size());
    }

    @Test
    void nullEdgeIsRejected() {
        Maze.Node node = new Maze.Node(0, 0);

        assertThrows(
            NullPointerException.class,
            () -> node.addEdge(null)
        );

        assertThrows(
            NullPointerException.class,
            () -> node.hasEdge(null)
        );
    }

    @Test
    void depthDefaultsToZero() {
        Maze.Node node = new Maze.Node(0, 0);

        assertEquals(0, node.getDepth());
    }
}