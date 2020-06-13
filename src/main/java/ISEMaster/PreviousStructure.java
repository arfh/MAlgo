package ISEMaster;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


public class PreviousStructure {
    public static final int IS_NEGATIVE_CYCLE = 1;
    public static final int IS_TREE = 0;

    private double[] dist;
    private Node[] prev;
    private int status = IS_TREE;
    private ArrayList<Node> negativeCycle = null;

    private double minCapacity = Double.POSITIVE_INFINITY;

    public double getMinCapacity(){
        return minCapacity;
    }


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

    public ArrayList<Node> getNegativeCycle(Node t, Graph g) {
        if(negativeCycle != null){
            return negativeCycle;
        }
        ArrayList<Node> cycle = new ArrayList<>();
        if(isNegativeCycle()) {
            Visited v = new Visited(prev.length);
            ArrayList<Node> q = new ArrayList();
            q.add(t);
            Node cycleBegin = null;
            while(!q.isEmpty()) {
                Node tmp = q.remove(0);
                if(v.isVisited(tmp)) {
                    cycleBegin = tmp;
                    break;
                }
                q.add(getPrev(tmp));
                v.setVisited(tmp);
            }

            q.clear();
            q.add(getPrev(cycleBegin));
            cycle.add(0, cycleBegin);
            while(!q.isEmpty()) {
                Node tmp = q.remove(0);
                if(tmp.equals(cycleBegin)) {
                    cycle.add(0, cycleBegin);
                    break;
                } else {
                    cycle.add(0, tmp);
                    q.add(getPrev(tmp));
                }
            }

            for(int i = 0; i < cycle.size() -1; i++) {
                Node a = cycle.get(i);
                Node b = cycle.get(i+1);

                Edge e = null;
                try {
                    e = g.getEdgeFromNodes(a, b);
                    minCapacity = Math.min(minCapacity, e.getCapacity());
                } catch (EdgeNotFoundException ex) {

                }
            }

        }
        negativeCycle = cycle;
        return cycle;
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
