package ISEMaster;

public class Visited {

    private boolean[] v = null;
    private int counter = 0;

    public Visited(int size) {
        v = new boolean[size];
    }

    public boolean allVisited() {
        return v.length == counter;
    }

    public boolean isVisited(Node n) {
        return v[n.getLabel()];
    }

    public boolean isNotVisited(Node n) {
        return !isVisited(n);
    }

    public void setVisited(Node n) {
        if(n.getLabel() < v.length) {
            if(v[n.getLabel()] == false) {
                counter++;
                v[n.getLabel()] = true;
            }
        }
    }
}