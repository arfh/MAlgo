package ISEMaster;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


public class PreviousStructure {
    public static final int IS_NEGATIVE_CYCLE = 1;
    public static final int IS_TREE = 0;

    private double[] dist;
    private double minNegativCylcleCapacity = Double.POSITIVE_INFINITY;
    private ArrayList<Edge> negativeCycle = new ArrayList<>();
    private Node[] prev;
    private int status = IS_TREE;
    private double totalNegativeCycleCosts = 0.0;

    public PreviousStructure(int nodeCount, Node start) {
        dist = new double[nodeCount];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);

        prev = new Node[nodeCount];

        setDist(start, 0.0);
        setPrev(start, start);
    }

    public void constructNegativCycle(Node currentNode, Graph g) {
        for(int i = 0; i < g.countNodes(); i++) {
            currentNode = prev[currentNode.getLabel()];
        }

        negativeCycle = new ArrayList<>();
        if(isNegativeCycle()) {
            Node start = currentNode;
            while(getPrev(currentNode).equals(start) == false) {
                try {
                    Edge e = g.getEdgeFromNodes(getPrev(currentNode), currentNode);
                    negativeCycle.add(e);
                    totalNegativeCycleCosts += e.getCosts();
                    minNegativCylcleCapacity = Math.min(minNegativCylcleCapacity, e.getCapacity());
                    currentNode = getPrev(currentNode);
                } catch(EdgeNotFoundException ex) {}
            }
            try {
                Edge e = g.getEdgeFromNodes(getPrev(currentNode), currentNode);
                negativeCycle.add(e);
                totalNegativeCycleCosts += e.getCosts();
                minNegativCylcleCapacity = Math.min(minNegativCylcleCapacity, e.getCapacity());
            } catch (EdgeNotFoundException edgeNotFoundException) {
            }
        }
    }

    public double getDist(Node n) {
        return dist[n.getLabel()];
    }

    public double getMinNegativCylcleCapacity(){
        return minNegativCylcleCapacity;
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

    public ArrayList<Edge> getNegativeCycle() {
        return negativeCycle;
    }

    public Node getPrev(Node n) {
        return prev[n.getLabel()];
    }

    public double getTotalNegativeCycleCosts() {
        return totalNegativeCycleCosts;
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

    public ArrayList<Node> getPath(Node s, Node t) {
        ArrayList<Node> p = new ArrayList<>();

        Node n = t;
        while(n.equals(s) == false) {
            p.add(0, n);
            n = prev[n.getLabel()];
        }
        p.add(0, s);
        return p;
    }
}
