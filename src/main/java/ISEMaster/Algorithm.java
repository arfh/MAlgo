package ISEMaster;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

public class Algorithm {

    public static Route DBA(Graph g) {
        Route r = new Route();
        Graph mst = kruskal(g);
        ArrayList<Edge> dfs = getDepthFirstSearchTrees(mst).get(0); // 0, weil nur eine Zuskomp.

        boolean[] visited = new boolean[mst.countNodes()];

        Node lastVisited = null;
        for(Edge e: dfs) {
            Node a = e.getA();
            Node b = e.getB();

            if(!visited[a.getLabel()] && !visited[b.getLabel()]) {
                visited[a.getLabel()] = true;
                visited[b.getLabel()] = true;

                lastVisited = b;
                r.addEdge(e);
            }
            else if(!visited[a.getLabel()]) {
                Edge edgeTmp = g.getEdgeFromNodes(lastVisited.getLabel(), a.getLabel());
                r.addEdge(new Edge(lastVisited, edgeTmp.getTarget(lastVisited), edgeTmp.getCosts()));
                lastVisited = a;
                visited[a.getLabel()] = true;
            }
            else if(!visited[b.getLabel()]) {
                Edge edgeTmp = g.getEdgeFromNodes(lastVisited.getLabel(), b.getLabel());
                r.addEdge(new Edge(lastVisited, edgeTmp.getTarget(lastVisited), edgeTmp.getCosts()));
                lastVisited = b;
                visited[b.getLabel()] = true;
            }
        }
        Node first = r.getFirstNode();
        Node last = r.getLastNode();

        Edge edgeTmp = g.getEdgeFromNodes(first.getLabel(), last.getLabel());
        r.addEdge(new Edge(last, first, edgeTmp.getCosts()));

        return r;
    }

    public static Route NNA(Graph g, Node s) {
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        Route r = new Route();
        boolean[] visited = new boolean[g.countNodes()];
        Node[] newNodes = new Node[g.countNodes()];

        visited[s.getLabel()] = true;

        Node actual = s;
        while(r.countEdges() < g.countNodes() - 1) {
            pq.clear();
            for(Edge e : actual.getEdges()) {
                pq.add(e);
            }

            Edge e = null;
            Node target = null;
            do {
                e = pq.poll();
                target = e.getTarget(actual);
            } while(visited[target.getLabel()]);

            visited[target.getLabel()] = true;
            r.addEdge(new Edge(actual, target, e.getCosts()));

            actual = target;
        }
        r.addEdge(new Edge(actual, s, actual.getEdge(s).getCosts()));
        return r;
    }

    public static Route bruteForceRoute(Graph g){
        Route r = null;
        Node s = g.getNodes().get(0);
        ArrayList<Edge> edges = s.getEdges();
        ArrayList<Route> routes = new ArrayList<>();
        for(Edge e : edges){
            Route newRoute = new Route();
            newRoute.addEdge(e);
            ArrayList<Node> unvisited = g.getNodes();
            unvisited.remove(s);
            unvisited.remove(e.getTarget(s));
            recursiveBruteForce(g, newRoute, unvisited, routes);
        }
        r = routes.get(0);
        for(Route tmp: routes) {
            if(r.totalCosts() > tmp.totalCosts()) {
                r = tmp;
            }
        }
        System.out.println(r.totalCosts());
        return r;
    }

    private static void recursiveBruteForce(Graph g, Route r, ArrayList<Node> unvisited, ArrayList<Route> routes){

        if(unvisited.isEmpty()){
            Node first = r.getFirstNode();
            Node last = r.getLastNode();
            Edge tmp = g.getEdgeFromNodes(first.getLabel(),last.getLabel());
            r.addEdge(new Edge(new Node(last), new Node(first), tmp.getCosts()));
            routes.add(r);
        }else{
            for(Node n : unvisited) {
                ArrayList<Node> newUnvisited = (ArrayList<Node>)unvisited.clone();
                newUnvisited.remove(n);
                Route newRoute = new Route(r);
                Node last = r.getLastNode();
                Edge tmp = g.getEdgeFromNodes(n.getLabel(),last.getLabel());
                newRoute.addEdge(new Edge(new Node(last), new Node(n), tmp.getCosts()));
                recursiveBruteForce(g,newRoute,newUnvisited, routes);
            }
        }
    }

    public static ArrayList<ArrayList<Edge>> getDepthFirstSearchTrees(Graph g) {
        ArrayList<ArrayList<Edge>> trees = new ArrayList<>();
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
                trees.add(e0);
            }
        }
        return trees;
    }

    public static Integer getRelatedComponents(Graph g) {
        return getDepthFirstSearchTrees(g).size();
    }

    public static Graph kruskal(Graph g) {
        GroupHandler gHandler = new GroupHandler(g.getNodes());
        ArrayList<Edge> edges = new ArrayList<>();
        // Notwendig, da sonst alle Kanten aus g auch im MST existieren.
        Node[] newNodes = new Node[g.countNodes()];
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        for(Node n : g.getNodes()) {
            for(Edge e : n.getEdges()) {
                pq.add(e);
            }
        }
        while(!pq.isEmpty()) {
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

    public static Graph prim(Graph g, Node s) {
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        ArrayList<Edge> edges = new ArrayList<>();
        boolean[] visited = new boolean[g.countNodes()];
        // Notwendig, da sonst alle Kanten aus g auch im MST existieren.
        Node[] newNodes = new Node[g.countNodes()];

        // Sonst würde die Kante, die s als target hat, die Kante zu s nochmal nehmen,
        // obwohl s zu target schon drin ist...
        visited[s.getLabel()] = true;

        for(Edge e : s.getEdges()) {
            pq.add(e);
        }
        //alternative: edges.size() < g.countNodes() - 1
        while(!pq.isEmpty()) {
            Edge e = pq.poll();
            Node a = e.getA();
            Node b = e.getB();

            if(!visited[a.getLabel()] || !visited[b.getLabel()] ) {
                //Wenn aktueller Knoten a noch  nicht besucht --> nehme a
                //sonst --> nehme b
                Node actual = !visited[a.getLabel()] ? a : b;
                for(Edge tmp: actual.getEdges()) {
                    if(!visited[tmp.getTarget(actual).getLabel()]) {
                        pq.add(tmp);
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

    private static void addNewNodesToTree(Edge e, Node[] newNodes, ArrayList<Edge> edges) {
        //Erzeuge Knoten falls er noch nicht existiert & füge Knoten zu Baum hinzu
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
