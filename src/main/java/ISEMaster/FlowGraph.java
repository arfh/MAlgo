package ISEMaster;

public class FlowGraph extends Graph {
    private double maxflow = 0;

    FlowGraph(Graph g){
        super(g);
    }

    public void increaseFlow(Double flow) {
        maxflow += flow;
    }

    public void checkIfResidualAndConstructIfNot(){
        for(Node n : this.nodes){
            for(Edge e : n.getEdges()){
                Node a = e.getA();
                Node b = e.getB();
                try{
                    super.getEdgeFromNodes(b,a);
                }catch(EdgeNotFoundException ex){
                    Edge tmp = new Edge(b, a);
                    tmp.setCapacity(e.getFlow());
                    b.addEdge(tmp);
                }

            }
        }
    }

    public double getMaxflow() {
        return maxflow;
    }
}
