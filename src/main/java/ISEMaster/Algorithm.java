package ISEMaster;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

public class Algorithm {

    public static ArrayList<Graph> getDepthFirstSearchTrees(Graph g) {
        ArrayList<Graph> trees = new ArrayList<>();
        boolean[] visited = new boolean[g.countNodes()];

        //Alle Knoten, die im Graph G existieren
        ArrayList<Node> knownNodes = g.getNodes();

        for (Node n: knownNodes) {

            if(! visited[n.getLabel()]) {
                visited[n.getLabel()] = true;
                Stack<Node> l1 = new Stack<>();
                ArrayList<Edge> e0 = new ArrayList<>();
                l1.add(n);
                while(!l1.empty()) {
                    Node v = l1.pop();
                    visited[v.getLabel()]=true;
                    ArrayList<Edge> edges = v.getEdges();
                    for (Edge e: edges) {
                        if(! visited[e.getTarget(v).getLabel()]) {
                            e0.add(e);
                            l1.add(e.getTarget(v));
                            visited[e.getTarget(v).getLabel()] = true;
                        }
                    }
                }
                trees.add(new Graph(e0));
            }
        }
        return trees;
    }

    public static Integer getRelatedComponents(Graph g) {
        return getDepthFirstSearchTrees(g).size();
    }

    public static Graph Kruskal(Graph g){
        ArrayList<Edge> edges = new ArrayList<>();
        boolean[] visited = new boolean[g.countNodes()];

        PriorityQueue<Edge> pq = new PriorityQueue<>();
        for(Node n : g.getNodes()){
            for(Edge e : n.getEdges()){
                pq.add(e);
            }
        }

        while(!pq.isEmpty()){
            Edge e = pq.poll();
            Node a = e.getA();
            Node b = e.getB();
            if(visited[a.getLabel()] && visited[b.getLabel()]){
               continue;
            }
            visited[a.getLabel()]=true;
            visited[b.getLabel()]=true;
            Node a1 = new Node(a.getLabel());
            Node b1 = new Node(b.getLabel());
            edges.add(new Edge(a1,b1,e.getCosts()));
        }

        return new Graph(edges);

    }

}
