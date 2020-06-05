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
                if(!e.isResidualEdge()){
                    try{
                        Edge back = super.getEdgeFromNodes(b,a);
                        back.setCapacity(e.getFlow());
                    }catch(EdgeNotFoundException ex){
                        Edge tmp = new Edge(b, a);
                        tmp.setCapacity(e.getFlow());
                        b.addEdge(tmp);
                        tmp.setResidualEdge(true);
                    }
                }
            }
        }
    }

    public double getMaxflow() {
        return maxflow;
    }
}
