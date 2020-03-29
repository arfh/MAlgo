package ISEMaster;

import java.util.TreeMap;

public class Node implements Comparable<Node>{

    private static TreeMap<Integer, Node> nodes = new TreeMap<>();
    private Integer label;

    public Node(Integer label) {
        this.label = label;
    }

    public static Node getNode(int label) {
        Node n = null;
        if(nodes.containsKey(label) == false) {
            n = new Node(label);
            nodes.put(label, n);
        } else {
            n = nodes.get(label);
        }
        return n;
    }

    @Override
    public int compareTo(Node o) {
        return this.label.compareTo(o.label);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof  Node) {
            return label.equals(((Node) obj).label);
        }
        return false;
    }

    public Integer getLabel() {
        return label;
    }

    public String toString() {
        return "" + label;
    }
}
