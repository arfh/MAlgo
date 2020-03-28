package ISEMaster;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class AlgorithmTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/relatedComponentTest.csv", numLinesToSkip = 1)
    void getRelatedComponents(String filename, Integer components) {
        try {
            Graph g = new Graph(new File(filename));
            assertEquals(components, Algorithm.getRelatedComponents(g));
        } catch (FileNotFoundException e) {
        }
    }

    /*

Graph3.txt,4
Graph_gross.txt,222
Graph_ganzgross.txt,9560
Graph_ganzganzgross.txt,306
     */
}