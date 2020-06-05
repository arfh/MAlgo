package ISEMaster;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class FlowGraph extends Graph {
    private double maxflow = 0;

    public FlowGraph(File f) throws FileNotFoundException {
        if(!f.exists()) {
            throw  new FileNotFoundException("File " + f.getPath() + " not found");
        }
        directed = true;
        FileReader fr;
        try {
            fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();

            int x = Integer.parseInt(line);
            for(int i = 0; i < x; i++) {
                nodes.add(new Node(i));
            }

            for(Node n: nodes) {
                line = br.readLine();
                n.setBalance(Double.parseDouble(line));
            }

            while((line = br.readLine()) != null){
                // Hinzufügen aller Kanten in den Graphen
                String[] items = line.split("\\t");
                if(items.length >= 4) {
                    int n1 = Integer.parseInt(items[0]);
                    int n2 = Integer.parseInt(items[1]);
                    Double costs = Double.parseDouble(items[2]);
                    totalCosts += costs;

                    Double capacity = Double.parseDouble(items[3]);

                    Node a = nodes.get(n1);
                    Node b = nodes.get(n2);

                    Edge e = new Edge(a, b, costs, capacity);
                    a.addEdge(e);
                }
            }
            br.close();
        }
        catch(Exception e) {
        }
    }

    FlowGraph(Graph g){
        super(g);
    }

    public void increaseFlow(Double flow) {
        maxflow += flow;
    }

    public void checkIfResidualAndConstructIfNot(){
        for(Node n : this.nodes){
            for(Edge e : n.getEdges()){
                Node a = e.getA();
                Node b = e.getB();
                if(!e.isResidualEdge()){
                    try{
                        Edge back = super.getEdgeFromNodes(b,a);
                        back.setCapacity(e.getFlow());
                    }catch(EdgeNotFoundException ex){
                        Edge tmp = new Edge(b, a);
                        tmp.setCapacity(e.getFlow());
                        b.addEdge(tmp);
                        tmp.setResidualEdge(true);
                    }
                }
            }
        }
    }

    public double getMaxflow() {
        return maxflow;
    }
}
