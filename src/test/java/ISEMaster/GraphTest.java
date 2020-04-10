package ISEMaster;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;


class GraphTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/importTestAll.csv", numLinesToSkip = 1)
    public void testImport(String filename, Integer expNodes, Integer expEdges) {
        Graph g = GraphSupplier.getGraph(filename);
        assertEquals(expNodes, g.countNodes());
        assertEquals(expEdges, g.countEdges());
    }
}