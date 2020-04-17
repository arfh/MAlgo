package ISEMaster;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


    class GraphTest {

    @Test
    public void testEdgeConstructor() {
        Node a = new Node(1);
        Node b = new Node(2);
        ArrayList<Edge> edges = new ArrayList<>();
        edges.add(new Edge(a, b));
        Graph g = new Graph(edges);
        System.out.println(g);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/importTestAll.csv", numLinesToSkip = 1)
    public void testImport(String filename, Integer expNodes, Integer expEdges) {
        Graph g = GraphSupplier.getGraph(filename);
        assertEquals(expNodes, g.countNodes());
        assertEquals(expEdges, g.countEdges());
    }
}