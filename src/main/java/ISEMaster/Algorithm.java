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
        ArrayList<Edge> edges = new ArrayList<>();
        boolean[] visited = new boolean[g.countNodes()];
        int[] group = new int[g.countNodes()];
        Node[] newNodes = new Node[g.countNodes()];
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        for(Node n : g.getNodes()){
            for(Edge e : n.getEdges()){
                pq.add(e);
            }
        }
        int x = 1;
        while(!pq.isEmpty()){
            Edge e = pq.poll();
            Node a = e.getA();
            Node b = e.getB();
            if(visited[a.getLabel()] && visited[b.getLabel()]){
                if (group[a.getLabel()] != group[b.getLabel()]) {
                    int ag = group[a.getLabel()];
                    int bg = group[b.getLabel()];

                    for(int i = 0; i < group.length; i++) {
                        if (group[i] == bg) {
                            group[i] = ag;
                        }
                    }
                    addNewNodesToTree(e, newNodes, edges);
                }
            } else {
                if(visited[a.getLabel()]) {
                    group[b.getLabel()] = group[a.getLabel()];
                } else if(visited[b.getLabel()]) {
                    group[a.getLabel()] = group[b.getLabel()];
                } else {
                    group[a.getLabel()] = x;
                    group[b.getLabel()] = x;
                    x++;
                }

                visited[a.getLabel()] = true;
                visited[b.getLabel()] = true;

                addNewNodesToTree(e, newNodes, edges);
            }
        }
        return new Graph(edges);

    }

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

    private static void addEdgeToList(ArrayList<Edge> edges, Edge e) {
        edges.add(e);
    }



    public static Graph prim(Graph g, Node s){
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        ArrayList<Edge> edges = new ArrayList<>();
        boolean[] visited = new boolean[g.countNodes()];
        Node[] newNodes = new Node[g.countNodes()];

        for(Edge e : s.getEdges()){
            pq.add(e);

        }

        while(edges.size() < g.countNodes() - 1 && !pq.isEmpty()){
            Edge e = pq.poll();
            Node a = e.getA();
            Node b = e.getB();

            if(visited[a.getLabel()]){
                s = a;
            }
            else{
                s = b;
            }

            s = e.getTarget(s);
            for(Edge tmp: s.getEdges()){
                if(!visited[tmp.getTarget(s).getLabel()]) {
                        pq.add(tmp);
                }
            }

            if(visited[a.getLabel()] && visited[b.getLabel()]){
                continue;
            }
            else{
                visited[a.getLabel()] = true;
                visited[b.getLabel()] = true;

                addNewNodesToTree(e,newNodes,edges);
            }

        }

        return new Graph(edges);
    }
}
