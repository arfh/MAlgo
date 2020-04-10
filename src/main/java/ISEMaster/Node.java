package ISEMaster;

import java.util.ArrayList;


public class Node implements Comparable<Node>{

    private Integer label;
    private ArrayList<Edge> edges = new ArrayList<>();

    public Node(Integer label) {
        this.label = label;
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

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void addEdge(Edge e){
        edges.add(e);
    }
}
