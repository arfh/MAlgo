package ISEMaster;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class FlowGraph extends Graph {
    private double maxflow = 0;
    private ArrayList<Node> sources = new ArrayList<>();
    private ArrayList<Node> targets = new ArrayList<>();
    private double totalMinMaxFlowCosts = 0.0;

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
                Double balance = Double.parseDouble(line);
                n.setBalance(Double.parseDouble(line));
                if(balance > 0.0) {
                    sources.add(n);
                } else if(balance < 0.0) {
                    targets.add(n);
                }
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
        if(g instanceof FlowGraph) {
            sources = ((FlowGraph) g).sources;
            targets = ((FlowGraph) g).targets;
            maxflow = ((FlowGraph) g).maxflow;
        }
    }

    public void checkIfResidualAndConstructIfNot(){
        for(Edge e: getAllEdges()) {
            try {
                getEdgeFromNodes(e.getB(), e.getA());
            } catch (EdgeNotFoundException ex) {
                Edge rev = new Edge(e.getB(), e.getA(), -e.getCosts());
                rev.setCapacity(0.0);
                rev.setResidualEdge(true);
                e.getB().addEdge(rev);
            }
        }
        /*
        for(Node n : this.nodes){
            for(Edge e : n.getEdges()){
                Node a = e.getA();
                Node b = e.getB();
                if(!e.isResidualEdge()){
                    try{
                        Edge back = super.getEdgeFromNodes(b,a);
                        back.setCapacity(e.getFlow());
                    }catch(EdgeNotFoundException ex){
                        Edge tmp = new Edge(b, a, -e.getCosts(), e.getFlow());
                        //tmp.setCapacity(e.getFlow());
                        b.addEdge(tmp);
                        tmp.setResidualEdge(true);
                    }
                }
            }
        }
        */
    }

    public double getMaxflow() {
        return maxflow;
    }

    public ArrayList<Node> getSources() {
        return sources;
    }

    public ArrayList<Node> getTargets() {
        return targets;
    }

    public double getTotalMinMaxFlowCosts() {
        return totalMinMaxFlowCosts;
    }

    public void increaseTotalMinMaxFlowCosts(Double x) {
        totalMinMaxFlowCosts += x;
    }

    public void increaseTotalCosts(Double costs) {
        totalCosts += costs;
    }

    public void increaseFlow(Double flow) {
        maxflow += flow;
    }

}
