package ISEMaster;

public class Edge {

    private Double costs = 1.0;
    private Node end;
    private Node start;

    public Edge(Node start, Node end) {
        this.start = start;
        this.end = end;
    }

    public Edge(Node start, Node end, Double costs) {
        this.start = start;
        this.end = end;
        this.costs = costs;
    }

    public Double getCosts() {
        return costs;
    }


    public Node getEnd() {
        return end;
    }

    public Node getStart() {
        return start;
    }
}
