package ISEMaster;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


class GraphTest {

    @Test
    public void testImport() {
        String filepath = "./G_1_2.txt";
        try {
            Graph g = new Graph(new File(filepath));
        } catch (FileNotFoundException ex) {
            fail(ex.toString());
        }
    }
}