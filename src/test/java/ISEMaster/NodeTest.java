package ISEMaster;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    @Test
    void compareTo() {

        Node a = new Node(1);
        Node b = new Node(2);

        assertEquals(0, a.compareTo(a));
        assertEquals(-1, a.compareTo(b));
        assertEquals(1, b.compareTo(a));
    }

    @Test
    void equals() {
        Node a = new Node(1);
        Node b = new Node(2);

        assertEquals(a, a);
        assertNotEquals(a, b);
    }
}