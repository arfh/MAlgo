package ISEMaster;

public class Edge implements Comparable<Edge>{

    public static final Double DEF_COSTS = 1.0;
    private Node a;
    private Node b;
    private Double costs;

    public Edge(Node a, Node b) {
        this(a, b, DEF_COSTS);
    }

    public Edge(Node a, Node b, Double costs) {
        this.a = a;
        this.b = b;
        this.costs = costs;
        a.addEdge(this);
        b.addEdge(this);
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

    public Double getCosts() {
        return costs;
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

    @Override
    public int compareTo(Edge o) {
        return this.costs.compareTo(o.costs);
    }
}
