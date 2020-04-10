package ISEMaster;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class Graph {
    ArrayList<Node> nodes = new ArrayList<>();

    public Graph(ArrayList<Edge> edges) {

    }

    public Graph(File f) throws FileNotFoundException{
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
                // Initialisieren von allen Knoten in der Map (=> Knoten ohne Kanten würden sonst nicht hinzugefügt werden)

                // Hinzufügen aller Kanten in den Graphen
                String[] items = line.split("\\t");
                if(items.length >= 2) {
                    int n1 = Integer.parseInt(items[0]);
                    int n2 = Integer.parseInt(items[1]);
                    Double costs = Edge.DEF_COSTS;
                    if(items.length >= 3) {
                        costs = Double.parseDouble(items[2]);
                    }

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

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public String toString() {
        StringBuilder strb = new StringBuilder();
        for (Node entry : nodes) {
            strb.append(entry.getLabel().toString());
            strb.append("\n");
        }

        return strb.toString();
    }
}
