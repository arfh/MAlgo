package ISEMaster;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Graph {

    private TreeMap<Node, ArrayList<Edge>> map = new TreeMap<>();

    public static void main(String[] args) {
        Edge e = new Edge(new Node(1), new Node(2));
        Edge f = new Edge(new Node(2), new Node(3));
        Graph g = new Graph();

        g.addEdge(e);
        g.addEdge(e);
        g.addEdge(f);

        System.out.println(g.toString());

    }

    public void addEdge(Edge e) {
        // Füge Knoten zur Map hinzu, falls dieser noch nicht existiert.
        if(map.containsKey(e.getStart()) == false) {
            map.put(e.getStart(), new ArrayList<Edge>());
        }
        // Füge Kante zur Map hinzu
        map.get(e.getStart()).add(e);
    }

    public String toString() {
        StringBuilder strb = new StringBuilder();
        for (Map.Entry<Node, ArrayList<Edge>> entry: this.map.entrySet()) {
            strb.append(entry.getKey().toString() + " -> " + entry.getValue().toString());
            strb.append("\n");
        }

        return strb.toString();
    }
}
