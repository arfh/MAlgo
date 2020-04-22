package ISEMaster;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class Graph {
    private ArrayList<Node> nodes = new ArrayList<>();

    private double totalCosts = 0.0;

    public Graph(ArrayList<Edge> edges) {
        for (Edge e : edges) {
            totalCosts += e.getCosts();
            Node a = e.getA();
            Node b = e.getB();
            while (a.getLabel() >= nodes.size()) {
                nodes.add(null);
            }
            nodes.set(a.getLabel(), a);
            while (b.getLabel() >= nodes.size()) {
                nodes.add(null);
            }
            nodes.set(b.getLabel(), b);
        }
    }

    public Graph(File f) throws FileNotFoundException {
        if(!f.exists()) {
            throw  new FileNotFoundException("File " + f.getPath() + " not found");
        }
        FileReader fr;
        try {
            fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();

            int x = Integer.parseInt(line);
            for(int i = 0; i < x; i++) {
                nodes.add(new Node(i));
            }

            while((line = br.readLine()) != null){
                // Hinzufügen aller Kanten in den Graphen
                String[] items = line.split("\\t");
                if(items.length >= 2) {
                    int n1 = Integer.parseInt(items[0]);
                    int n2 = Integer.parseInt(items[1]);
                    Double costs = Edge.DEF_COSTS;
                    if(items.length >= 3) {
                        costs = Double.parseDouble(items[2]);
                    }
                    totalCosts += costs;
                    Edge e = new Edge(nodes.get(n1), nodes.get(n2), costs);
                }
            }
            br.close();
        }
        catch(Exception e) {
        }
    }

    public boolean containsNode(Node n) {
        if(nodes.size() > n.getLabel()){
            return nodes.get(n.getLabel()).equals(n);
        }
        return false;
    }

    public Integer countEdges() {
        int edges = 0;
        for (Node entry: nodes) {
            edges = edges + entry.getEdges().size();
        }
        return edges /2;
    }

    public Integer countNodes() {
        return nodes.size();
    }

    public Edge getEdgeFromNodes(int a, int b) {
        for(Edge e: getEdgesFromNode(a)) {
            if(e.getTarget(nodes.get(a)).equals(nodes.get(b))) {
                return e;
            }
        }
        return null;
    }

    public ArrayList<Edge> getEdgesFromNode(int n){
        return nodes.get(n).getEdges();
    }

    public ArrayList<Node> getNodes() {
        return (ArrayList<Node>)nodes.clone();
    }

    public double getTotalCosts() {
        return totalCosts;
    }

    public String toString() {
        StringBuilder strb = new StringBuilder();
        for (Node entry : nodes) {
            if(!(entry == null)) {
                strb.append(entry.toString());
                strb.append("\n");
            }
        }
        return strb.toString();
    }
}
