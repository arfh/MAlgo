package ISEMaster;

public class FlowGraph extends Graph {
    private double maxflow = 0;
    private double totalcosts = 0;

    FlowGraph(Graph g){
        super(g);
    }

    public void checkIfResidualAndConstructIfNot(){
        for(Node n : this.nodes){
            for(Edge e : n.getEdges()){
                Node a = e.getA();
                Node b = e.getB();

                try{
                    super.getEdgeFromNodes(b,a);
                }catch(EdgeNotFoundException ex){
                    Edge tmp = new Edge(b, a, e.getCosts());
                    b.addEdge(tmp);
                }

            }
        }
    }

    public void removeEdgesWithZeroCapacity(){
        for(Node n: nodes){
            for(Edge e: n.getEdges()){
                if(e.getCosts() <= 0){
                    n.removeEdge(e);
                }
            }
        }
    }
}
