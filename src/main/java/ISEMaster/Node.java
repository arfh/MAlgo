package ISEMaster;

import java.util.ArrayList;


public class Node implements Comparable<Node>{

    private double balance;
    private ArrayList<Edge> edges = new ArrayList<>();
    private Integer label;
    private double isBalance = 0.0;

    public Node(Integer label) {
        this.label = label;
    }

    public Node(Node n){
        this.label = n.label;
    }

    public void addEdge(Edge e){
        if(e != null) {
            edges.add(e);
        }
    }

    @Override
    public int compareTo(Node o) {
        return this.label.compareTo(o.label);
    }

    public void decreaseIsBalance(Double x) {
        isBalance -= x;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof  Node) {
            return label.equals(((Node) obj).label);
        }
        return false;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Edge getEdge(Node target){
        for(Edge e: edges){
            if(e.getTarget(this).equals(target)){
                return e;
            }
        }
        return null;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public Integer getLabel() {
        return label;
    }

    public double getIsBalance() {
        return isBalance;
    }

    public void setIsBalance(double isBalance) {
        this.isBalance = isBalance;
    }

    public void increaseIsBalance(Double x) {
        isBalance += x;
    }

    public void removeEdge(Edge e){
        if(e != null){
            edges.remove(e);
        }
    }

    public String toString() {
        return label + " " + edges.toString();
    }
}
