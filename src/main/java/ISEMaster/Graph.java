package ISEMaster;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class Graph {
    protected boolean directed = false;
    protected ArrayList<Node> nodes = new ArrayList<>();
    protected double totalCosts = 0.0;

    public Graph() {

    }

    public Graph (Graph g){
        this.nodes = g.nodes;
    }

    public Graph(File f) throws FileNotFoundException {
        this(f, false);
    }

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

    public Graph(File f, boolean directed) throws FileNotFoundException {
        this.directed = directed;
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
                    Node a = nodes.get(n1);
                    Node b = nodes.get(n2);
                    Edge e = new Edge(a, b, costs);
                    a.addEdge(e);
                    if(directed == false) {
                        e = new Edge(b, a, costs);
                        b.addEdge(e);
                    }
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
        if(!directed) {
            edges = edges / 2;
        }
        return edges;
    }

    public Integer countNodes() {
        return nodes.size();
    }

    public ArrayList<Edge> getAllEdges(){
        ArrayList<Edge> res = new ArrayList<>();
        for(Node n: nodes) {
            res.addAll(n.getEdges());
        }
        return res;
    }

    public Edge getEdgeFromNodes(Node a, Node b) throws EdgeNotFoundException {
        for(Edge e: nodes.get(a.getLabel()).getEdges()) {
            if(e.getTarget(a).equals(b)) {
                return e;
            }
        }
        throw new EdgeNotFoundException();
    }

    public ArrayList<Node> getNodes() {
        return (ArrayList<Node>)nodes.clone();
    }

    public double getTotalCosts() {
        return totalCosts;
    }

    public void setDirected(boolean directed) {
        this.directed = directed;
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
