package ISEMaster;

public class Predesessor {

    private Node[] prev = null;

    public Predesessor(Graph g, Node s) {
        prev = new Node[g.countNodes()];
        prev[s.getLabel()] = s;
    }

    public Node getPrevNode(Node x) {
        return prev[x.getLabel()];
    }

    public void setPrevNode(Node x, Node prevNode) {
        prev[x.getLabel()] = prevNode;
    }
}
