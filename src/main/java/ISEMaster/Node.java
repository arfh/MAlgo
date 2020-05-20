package ISEMaster;

import java.util.ArrayList;


public class Node implements Comparable<Node>{

    private ArrayList<Edge> edges = new ArrayList<>();
    private Integer label;

    public Node(Integer label) {
        this.label = label;
    }

    public Node(Node n){
        this.label = n.label;
    }

    public void addEdge(Edge e){
        if(e != null) {
            edges.add(e);
        }
    }

    public void removeEdge(Edge e){
        if(e != null){
            edges.remove(e);
        }
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

    public Edge getEdge(Node target){
        for(Edge e: edges){
            if(e.getTarget(this).equals(target)){
                return e;
            }
        }
        return null;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public Integer getLabel() {
        return label;
    }

    public String toString() {
        return label + " " + edges.toString();
    }
}
