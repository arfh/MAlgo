package ISEMaster;

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
            long start = System.nanoTime();
            assertEquals(components, Algorithm.getRelatedComponents(g));
            long end = System.nanoTime();
            System.out.println((end-start)/1000.0/1000.0);
        } catch (FileNotFoundException e) {
        }
    }
}