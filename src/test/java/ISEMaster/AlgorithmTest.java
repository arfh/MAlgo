package ISEMaster;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;


import static org.junit.jupiter.api.Assertions.*;

class AlgorithmTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/relatedComponentTest.csv", numLinesToSkip = 1)
    void getRelatedComponents(String filename, Integer components) {
        Graph g = GraphSupplier.getGraph(filename);
        long start = System.nanoTime();
        assertEquals(components, Algorithm.getRelatedComponents(g));
        long end = System.nanoTime();
        System.out.println((end-start)/1000.0/1000.0);
    }

    @Test
    public void Test(){
        Graph g = GraphSupplier.getGraph("G_1_2.txt");
        Graph t = Algorithm.Kruskal(g);
        assertEquals(g.countNodes(),t.countNodes());
        assertEquals(g.countNodes()-1,t.countEdges());
    }
}

