package ISEMaster;

public class Edge implements Comparable<Edge>{

    public static final Double DEF_COSTS = 1.0;
    private Node a;
    private Node b;
    private Double capacity = 0.0;
    private Double costs;
    private Double flow = 0.0;

    public Edge(Node a, Node b) {
        this(a, b, DEF_COSTS);
    }

    public Edge(Node a, Node b, Double costs) {
        this.a = a;
        this.b = b;
        this.costs = costs;
        this.capacity = costs;
        //a.addEdge(this);
        //b.addEdge(this);
    }

    public void increaseFlow(Double x) {
        this.flow += x;
    }

    @Override
    public int compareTo(Edge o) {
        return this.costs.compareTo(o.costs);
    }

    public boolean equals(Object o) {
        if(o instanceof Edge) {
            return a.equals(((Edge) o).a) && b.equals(((Edge) o).b) && costs.equals(((Edge) o).costs);
        } else {
            return false;
        }
    }

    public Node getA() {
        return a;
    }

    public Node getB() {
        return b;
    }

    public Double getCapacity() {
        return capacity;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }

    public Double getCosts() {
        return costs;
    }

    public Double getFlow() {
        return flow;
    }

    public void setFlow(Double flow) {
        this.flow = flow;
    }

    public Node getTarget(Node n) {
        if(a.equals(n)) {
            return b;
        } else if(b.equals(n)) {
            return a;
        } else {
            return null;
        }
    }

    public String toString() {
        return "[" + a.getLabel() + " - " + b.getLabel() + ", " + costs + "]";
    }
}
