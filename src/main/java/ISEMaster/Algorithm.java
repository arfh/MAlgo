package ISEMaster;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.concurrent.ScheduledExecutorService;

public class Algorithm {
    static int x = 0;

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
                Edge edgeTmp = g.getEdgeFromNodes(lastVisited, a);
                r.addEdge(edgeTmp);
                lastVisited = a;
                visited[a.getLabel()] = true;
            }
            else if(!visited[b.getLabel()]) {
                Edge edgeTmp = g.getEdgeFromNodes(lastVisited, b);
                r.addEdge(edgeTmp);
                lastVisited = b;
                visited[b.getLabel()] = true;
            }
        }
        Node first = r.getFirstNode();
        Node last = r.getLastNode();

        Edge edgeTmp = g.getEdgeFromNodes(first, last);
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

    public static Route bruteForceRoute(Graph g) {
        x = 0;
        Route cheapest = new Route();
        ArrayList<Node> unvisited = g.getNodes();
        Node s = unvisited.remove(0);
        Node n = null;
        Route r = null;

        for(int i = 0; i < unvisited.size(); i++) {
            n = unvisited.remove(0);
            r = addEdgeToRoute(new Route(), s, n, g);
            cheapest = recursiveBruteForce(g, r, unvisited, cheapest);
            unvisited.add(n);
        }
        return cheapest;
    }

    private static Route recursiveBruteForce(Graph g, Route r, ArrayList<Node> unvisited, Route cheapest){
        if(unvisited.isEmpty()){
            addEdgeToRoute(r, r.getLastNode(), r.getFirstNode(), g);
            x++;
            if(checkCheapestRoute(r, cheapest)) {
                return r;
            } else {
                return cheapest;
            }
        }else{
            for(int i = 0; i < unvisited.size(); i++) {
                Node n = unvisited.remove(0);
                Route newRoute = new Route(r);
                addEdgeToRoute(newRoute, r.getLastNode(), n, g);
                cheapest = recursiveBruteForce(g, newRoute, unvisited, cheapest);
                unvisited.add(n);
            }
            return cheapest;
        }
    }

    private static boolean checkCheapestRoute(Route r, Route currentCheapest) {
        if(currentCheapest.countEdges() == 0) {
            return true;
        }
        return r.totalCosts() < currentCheapest.totalCosts();
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

    private static void addNewNodesToTree(Edge e, Node[] newNodes, ArrayList<Edge> edges) {
        //Erzeuge Knoten falls er noch nicht existiert & füge Knoten zu Baum hinzu
        Node a1 = createNewNodeIfNotExists(e.getA(), newNodes);
        Node b1 = createNewNodeIfNotExists(e.getB(), newNodes);
        Edge newEdge = new Edge(a1, b1, e.getCosts());
        a1.addEdge(newEdge);
        b1.addEdge(newEdge);
        edges.add(newEdge);
    }

    private static Node createNewNodeIfNotExists(Node a, Node[] newNodes) {
        if(newNodes[a.getLabel()] == null) {
            Node newA = new Node(a.getLabel());
            newNodes[a.getLabel()] = newA;
        }
        return newNodes[a.getLabel()];
    }

    private static Route addEdgeToRoute(Route r, Node a, Node b, Graph g) {
        Edge tmp = g.getEdgeFromNodes(a, b);
        //r.addEdge(new Edge(new Node(a), new Node(b), tmp.getCosts()));
        r.addEdge(tmp);
        return r;
    }
}
