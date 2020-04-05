package ISEMaster;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Graph {

    private TreeMap<Node, ArrayList<Edge>> map = new TreeMap<>();

    public Graph(ArrayList<Edge> edges) {
        for (Edge e: edges) {
            this.addEdge(e);
        }
    }

    public Graph(File f) throws FileNotFoundException{
        if(f.exists() == false) {
            throw  new FileNotFoundException("File " + f.getPath() + " not found");
        }
        FileReader fr;
        try {
            fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String line;

            boolean firstLine = true;
            while((line = br.readLine()) != null){
                // Initialisieren von allen Knoten in der Map (=> Knoten ohne Kanten würden sonst nicht hinzugefügt werden)
                if(firstLine == true) {
                    Integer x = Integer.parseInt(line);
                    for(int i = 0; i < x; i++) {
                        map.put(Node.getNode(i), new ArrayList<Edge>());
                    }
                    firstLine = false;
                }
                // Hinzufügen aller Kanten in den Graphen
                else if(firstLine == false) {
                    String[] items = line.split("\\t");
                    if(items.length >= 2) {
                        int n1 = Integer.parseInt(items[0]);
                        int n2 = Integer.parseInt(items[1]);
                        Double costs = Edge.DEF_COSTS;
                        if(items.length >= 3) {
                            costs = Double.parseDouble(items[2]);
                        }
                        Edge e = new Edge(Node.getNode(n1), Node.getNode(n2), costs);
                        this.addEdge(e);
                    }
                }
            }
            br.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public void addEdge(Edge e) {
        // Füge Knoten zur Map hinzu, falls dieser noch nicht existiert.
        if(map.containsKey(e.getA()) == false) {
            map.put(e.getA(), new ArrayList<Edge>());
        }
        if(map.containsKey(e.getB()) == false) {
            map.put(e.getB(), new ArrayList<Edge>());
        }
        // Füge Kante zur Map hinzu
        map.get(e.getA()).add(e);
        map.get(e.getB()).add(e);
    }

    public boolean containsNode(Node n) {
        return map.containsKey(n);
    }

    public Integer countEdges() {
        Integer edges = 0;
        for (Map.Entry<Node, ArrayList<Edge>> entry: this.map.entrySet()) {
            edges = edges + entry.getValue().size();
        }
        return edges /2;
    }

    public Integer countNodes() {
        return map.size();
    }

    public ArrayList<Edge> getEdges(Node n) {
        if(containsNode(n) == false) {
            return new ArrayList<>();
        } else {
            return map.get(n);
        }
    }

    public ArrayList<Node> getNodes() {
        return new ArrayList<>(map.keySet());
    }

    public String toString() {
        StringBuilder strb = new StringBuilder();
        for (Map.Entry<Node, ArrayList<Edge>> entry : this.map.entrySet()) {
            strb.append(entry.getKey().toString() + " -> " + entry.getValue().toString());
            strb.append("\n");
        }

        return strb.toString();
    }
}
