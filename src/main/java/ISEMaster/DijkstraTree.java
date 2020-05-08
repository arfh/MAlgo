package ISEMaster;

import java.util.Arrays;

public class DijkstraTree {
    private double[] dist;
    private Node[] prev;

    public DijkstraTree(int nodeCount, Node start) {
        dist = new double[nodeCount];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);

        prev = new Node[nodeCount];


        setDist(start, 0.0);
        setPrev(start, start);
    }

    public void setDist(Node n, double c) {
        dist[n.getLabel()] = c;
    }

    public double getDist(Node n) {
        return dist[n.getLabel()];
    }

    public void setPrev(Node n, Node prevNode) {
        prev[n.getLabel()] = prevNode;
    }

    public Node getPrev(Node n) {
        return prev[n.getLabel()];
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < dist.length; i++) {
            if(prev[i] != null) {
                str.append(prev[i].getLabel() + " -> " + i + ": " + dist[i] + "\n");
            }
        }
        return str.toString();
    }
}
