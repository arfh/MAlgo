package ISEMaster;

import java.util.ArrayList;

public class Route {
    private ArrayList<Edge> edges = new ArrayList<>();

    public void addEdge(Edge e){
        edges.add(e);
    }

    public double totalCosts(){
        double sum = 0.0;
        for(Edge e: edges){
            sum+=e.getCosts();
        }
        return sum;
    }

    public String toString(){
        return edges.toString();
    }

    public int countEdges(){
        return edges.size();
    }
}
