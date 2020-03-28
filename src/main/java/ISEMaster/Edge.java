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

    public boolean equals(Object o) {
        if(o instanceof Edge) {
            return start.equals(((Edge) o).start) && end.equals(((Edge) o).end) && costs.equals(((Edge) o).costs);
        } else {
            return false;
        }
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

    public String toString() {
        return "[" + start.toString() + " - " + end.toString() + ", " + costs + "]";
    }
}
