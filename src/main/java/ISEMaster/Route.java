package ISEMaster;

import java.util.ArrayList;

public class Route {
    private double costs = 0.0;
    private ArrayList<Edge> edges = new ArrayList<>();

    public Route(Route r){
        for(Edge e : r.edges){
            edges.add(e);
            costs = costs + e.getCosts();
        }
    }

    public Route(){}

    public void addEdge(Edge e){
        edges.add(e);
        costs += e.getCosts();
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
        return costs;
    }
}
