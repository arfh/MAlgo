package ISEMaster;

import java.util.ArrayList;

public class Route {
    private ArrayList<Edge> edges = new ArrayList<>();

    public void addEdge(Edge e){
        edges.add(e);
    }

    public int countEdges(){
        return edges.size();
    }

    public Node getFirstNode(){
        return edges.get(0).getA();
    }

    public Node getLastNode(){
        return edges.get(edges.size()-1).getB();
    }

    public String toString(){
        return edges.toString();
    }

    public double totalCosts(){
        double sum = 0.0;
        for(Edge e: edges){
            sum+=e.getCosts();
        }
        return sum;
    }
}
