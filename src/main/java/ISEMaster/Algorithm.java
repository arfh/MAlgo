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

    public static Graph kruskal(Graph g){
        GroupHandler gHandler = new GroupHandler(g.getNodes());
        ArrayList<Edge> edges = new ArrayList<>();
        // Notwendig, da sonst alle Kanten aus g auch im MST existieren.
        Node[] newNodes = new Node[g.countNodes()];
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
            int g1 = gHandler.getGroupId(a);
            int g2 = gHandler.getGroupId(b);
            if (g1 != g2) {
                gHandler.unionGroups(a, b);
                addNewNodesToTree(e, newNodes, edges);
            }
        }
        return new Graph(edges);
    }

    public static Graph prim(Graph g, Node s){
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        ArrayList<Edge> edges = new ArrayList<>();
        boolean[] visited = new boolean[g.countNodes()];
        // Notwendig, da sonst alle Kanten aus g auch im MST existieren.
        Node[] newNodes = new Node[g.countNodes()];

        // Sonst würde die Kante, die s als target hat, die Kante zu s nochmal nehmen,
        // obwohl s zu target schon drin ist...
        visited[s.getLabel()] = true;

        for(Edge e : s.getEdges()){
            pq.add(e);
        }

        //alternative: edges.size() < g.countNodes() - 1
        while(!pq.isEmpty() && edges.size() < g.countNodes()){
            Edge e = pq.poll();
            Node a = e.getA();
            Node b = e.getB();

            if(!visited[a.getLabel()] || !visited[b.getLabel()] ) {
                //Wenn aktueller Knoten a noch  nicht besucht --> nehme a
                //sonst --> nehme b
                Node actual = !visited[a.getLabel()] ? a : b;

                if(!visited[actual.getLabel()]){
                    for(Edge tmp: actual.getEdges()){
                        if(!visited[tmp.getTarget(actual).getLabel()]) {
                            pq.add(tmp);
                        }
                    }
                }
                visited[a.getLabel()] = true;
                visited[b.getLabel()] = true;
                addNewNodesToTree(e,newNodes,edges);
            }
        }

        return new Graph(edges);
    }

    private static void addEdgeToList(ArrayList<Edge> edges, Edge e) {
        edges.add(e);
    }

    //Erzeuge Knoten falls er noch nicht existiert & füge Knoten zu Baum hinzu
    private static void addNewNodesToTree(Edge e, Node[] newNodes, ArrayList<Edge> edges) {
        Node a1 = createNewNodeIfNotExists(e.getA(), newNodes);
        Node b1 = createNewNodeIfNotExists(e.getB(), newNodes);
        addEdgeToList(edges, new Edge(a1, b1, e.getCosts()));
    }

    private static Node createNewNodeIfNotExists(Node a, Node[] newNodes) {
        if(newNodes[a.getLabel()] == null) {
            newNodes[a.getLabel()] = new Node(a.getLabel());
        }
        return newNodes[a.getLabel()];
    }
}
