package ISEMaster;

public class Node implements Comparable<Node>{

    private Integer label;

    public Node(Integer label) {
        this.label = label;
    }

    @Override
    public int compareTo(Node o) {
        // o == this
        if(this.equals(o)) {
            return 0;
        }
        // o > this
        else if(label < o.label){
            return 1;
        }
        // o < this
        else {
            return -1;
        }
    }

    public boolean equals(Object obj) {
        if(obj instanceof  Node) {
            return ((Node) obj).label == this.label;
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
