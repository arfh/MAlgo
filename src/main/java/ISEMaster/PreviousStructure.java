package ISEMaster;

import java.util.ArrayList;
import java.util.Arrays;

public class PreviousStructure {
    public static final int IS_NEGATIVE_CYCLE = 1;
    public static final int IS_TREE = 0;

    private double[] dist;
    private Node[] prev;
    private int status = IS_TREE;

    public PreviousStructure(int nodeCount, Node start) {
        dist = new double[nodeCount];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);

        prev = new Node[nodeCount];


        setDist(start, 0.0);
        setPrev(start, start);
    }

    public double getDist(Node n) {
        return dist[n.getLabel()];
    }

    public Node getMinDist(ArrayList<Node> unvisisted) throws ListIsEmptyException{
        if(unvisisted.isEmpty()) {
            throw new ListIsEmptyException();
        }
        int pos = 0;
        Node min = unvisisted.get(0);
        for(int i = 0; i < unvisisted.size(); i++) {
            Node n = unvisisted.get(i);
            if(getDist(min) > getDist(n)) {
                min = n;
                pos = i;
            }
        }
        unvisisted.remove(pos);
        return min;
    }

    public Node getPrev(Node n) {
        return prev[n.getLabel()];
    }

    public boolean isNegativeCycle() {
        return status == IS_NEGATIVE_CYCLE;
    }

    public void setDist(Node n, double c) {
        dist[n.getLabel()] = c;
    }

    public void setPrev(Node n, Node prevNode) {
        prev[n.getLabel()] = prevNode;
    }

    public void setToNegaticeCyle() {
        status = IS_NEGATIVE_CYCLE;
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
