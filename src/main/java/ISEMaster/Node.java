package ISEMaster;

public class Node implements Comparable<Node>{

    private Integer label;

    public Node(Integer label) {
        this.label = label;
    }

    @Override
    public int compareTo(Node o) {
        return this.label.compareTo(o.label);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof  Node) {
            if(((Node) obj).getLabel() == this.label) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public Integer getLabel() {
        return label;
    }

    public String toString() {
        return "" + label;
    }
}
