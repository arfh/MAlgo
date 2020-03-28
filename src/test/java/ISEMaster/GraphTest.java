package ISEMaster;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;


class GraphTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/importTestAll.csv", numLinesToSkip = 1)
    public void testImport(String filename, Integer expNodes, Integer expEdges) {
        try {
            Graph g = new Graph(new File(filename));
            assertEquals(expNodes, g.countNodes());
            assertEquals(expEdges, g.countEdges());
        } catch (FileNotFoundException ex) {
            fail(ex.toString());
        }
    }
}