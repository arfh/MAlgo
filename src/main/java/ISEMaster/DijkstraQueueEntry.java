package ISEMaster;

public class DijkstraQueueEntry implements Comparable<DijkstraQueueEntry> {

    private Double costs;
    private Node n;

    public DijkstraQueueEntry(Node n, Double c) {
        this.n = n;
        this.costs = c;
    }

    @Override
    public int compareTo(DijkstraQueueEntry o) {
        return costs.compareTo(o.costs);
    }

    public Node getN() {
        return n;
    }
}
