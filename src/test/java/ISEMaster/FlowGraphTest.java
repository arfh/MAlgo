package ISEMaster;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlowGraphTest {


    @ParameterizedTest
    @CsvFileSource(resources = "/FlowGraph.csv", numLinesToSkip = 1)
    void testImport(String filename, int nodecount, int edgecount) {
        File f = new File(filename);
        try {
            FlowGraph g = new FlowGraph(f);
            assertEquals(nodecount, g.countNodes());
            assertEquals(edgecount, g.countEdges());
        } catch(Exception e) {
        }

    }

}
