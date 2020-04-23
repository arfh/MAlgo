package ISEMaster;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;


import static org.junit.jupiter.api.Assertions.*;

class AlgorithmTest {

    private static void testMST(Graph t, int expNodes, double expCosts) {
        assertEquals(expNodes, t.countNodes());
        assertEquals(expNodes-1, t.countEdges());
        assertTrue(t.getTotalCosts() < expCosts);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/MST.csv", numLinesToSkip = 1)
    public void testKruskal(String filename, Double costs){
        Graph g = GraphSupplier.getGraph(filename);
        long start = System.nanoTime();
        Graph t = Algorithm.kruskal(g);
        long end = System.nanoTime();
        System.out.println((end-start)/1000.0/1000.0);
        System.out.println(t.getTotalCosts());
        testMST(t, g.countNodes(), costs);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/MST.csv", numLinesToSkip = 1)
    public void testPrim(String filename, Double costs){
        Graph g = GraphSupplier.getGraph(filename);
        long start = System.nanoTime();
        Graph t = Algorithm.prim(g, g.getNodes().get(1));
        long end = System.nanoTime();
        System.out.println((end-start)/1000.0/1000.0);
        System.out.println(t.getTotalCosts());
        testMST(t, g.countNodes(), costs);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/relatedComponentTest.csv", numLinesToSkip = 1)
    void getRelatedComponents(String filename, Integer components) {
        Graph g = GraphSupplier.getGraph(filename);
        long start = System.nanoTime();
        assertEquals(components, Algorithm.getRelatedComponents(g));
        long end = System.nanoTime();
        System.out.println((end-start)/1000.0/1000.0);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/NNA_DBA.csv", numLinesToSkip = 1)
    void testDBA(String filename, double costs){
        Graph g = GraphSupplier.getGraph(filename);
        long start = System.nanoTime();
        Route r = Algorithm.DBA(g);
        long end = System.nanoTime();
        System.out.println((end-start)/1000.0/1000.0);
        assertEquals(g.getNodes().size() , r.countEdges());
        if(costs > 0) {
            assertTrue(r.totalCosts() <= 2*costs);
        }

        System.out.println(r);
        System.out.println(r.totalCosts());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/NNA_DBA.csv", numLinesToSkip = 1)
    void testNNA(String filename, double costs){
        Graph g = GraphSupplier.getGraph(filename);
        long start = System.nanoTime();
        Route r = Algorithm.NNA(g, g.getNodes().get(0));
        long end = System.nanoTime();
        System.out.println((end-start)/1000.0/1000.0);
        assertEquals(g.getNodes().size() , r.countEdges());

        System.out.println(r);
        System.out.println(r.totalCosts());
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/bb.csv", numLinesToSkip = 1)
    void testBruteForceRoute(String filename, double costs){
        Graph g = GraphSupplier.getGraph(filename);
        long start = System.nanoTime();
        Route r = Algorithm.bruteForceRoute(g);
        long end = System.nanoTime();
        System.out.println("Time: " + (end-start)/1000.0/1000.0);
        System.out.println(r);
        System.out.println(r.totalCosts());
        assertEquals(g.getNodes().size() , r.countEdges());
        assertTrue(r.totalCosts() <= costs);
    }

}

