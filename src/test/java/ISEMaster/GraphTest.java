package ISEMaster;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;


class GraphTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/importTest.csv", numLinesToSkip = 1)
    public void testImport(String filename, Integer expNodes, Integer expEdges) {
        try {
            Graph g = new Graph(new File(filename));
            assertEquals(expNodes, g.countNodes());
            assertEquals(expEdges, g.countEdges());
        } catch (FileNotFoundException ex) {
            fail(ex.toString());
        }
    }

    @Test
    void tmp() {
        TreeSet<Node> set = new TreeSet<>();
        Node a = new Node(1);
        Node b = new Node(2);

        assertFalse(set.contains(a));
        assertFalse(set.contains(b));

        assertTrue(set.add(a));

        assertTrue(set.contains(a));
        assertFalse(set.contains(b));

        assertTrue(set.add(b));

        assertTrue(set.contains(a));
        assertTrue(set.contains(b));

        assertFalse(set.add(a));
    }
}