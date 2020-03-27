package ISEMaster;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Graph {

    private TreeMap<Node, ArrayList<Edge>> map = new TreeMap<>();

    public void addEdge(Edge e) {
        // Füge Knoten zur Map hinzu, falls dieser noch nicht existiert.
        if(map.containsKey(e.getStart()) == false) {
            map.put(e.getStart(), new ArrayList<Edge>());
        }
        // Füge Kante zur Map hinzu
        map.get(e.getStart()).add(e);
    }

    public boolean containsNode(Node n) {
        return map.containsKey(n);
    }

    public ArrayList<Edge> getEdges(Node n) {
        if(containsNode(n) == false) {
            return null;
        } else {
            return map.get(n);
        }
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
