package ISEMaster;

import java.util.*;

public class Algorithm {

    public static Route DBA(Graph g, Node s) {
        Route r = new Route();
        Graph mst = prim(g, s);
        ArrayList<Edge> dfs = getDepthFirstSearchTrees(mst).get(0); // 0, weil nur eine Zuskomp.

        boolean[] visited = new boolean[mst.countNodes()];

        Node lastVisited = null;
        for(Edge e: dfs) {
            Node a = e.getA();
            Node b = e.getB();

            if(!visited[a.getLabel()] && !visited[b.getLabel()]) {
                addEdgeToRoute(r, a, b, g);
                lastVisited = b;
            }
            else if(!visited[a.getLabel()]) {
                addEdgeToRoute(r, lastVisited, a, g);
                lastVisited = a;
            }
            else if(!visited[b.getLabel()]) {
                addEdgeToRoute(r, lastVisited, b, g);
                lastVisited = b;
            }
            visited[a.getLabel()] = true;
            visited[b.getLabel()] = true;
        }
        Node first = r.getFirstNode();
        Node last = r.getLastNode();

        addEdgeToRoute(r, last, first, g);
        return r;
    }

    public static Route NNA(Graph g, Node s) {
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        Route r = new Route();
        boolean[] visited = new boolean[g.countNodes()];

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
            addEdgeToRoute(r, actual, target, g);

            actual = target;
        }
        addEdgeToRoute(r, actual, s, g);
        return r;
    }

    public static DijkstraTree bellmanFord(Graph g, Node s) throws NegativCycleException{
        DijkstraTree tree = new DijkstraTree(g.countNodes(), s);
        ArrayList<Edge> edges = g.getAllEdges();
        for(int i = 1; i < g.countNodes(); i++) {
            for(Edge e: edges) {
                Node v = e.getA();
                Node w = e.getB();
                Double ce = e.getCosts();
                double cw = tree.getDist(w);
                double tmpc = tree.getDist(v) + ce;

                if(tmpc < cw) {
                    tree.setDist(w, tmpc);
                    tree.setPrev(w, v);
                }
            }
        }

        for(Edge e: edges) {
            double cv = tree.getDist(e.getA());
            double ce = e.getCosts();
            double cw = tree.getDist(e.getB());
            if((cv +ce) < cw) {
                throw new NegativCycleException();
            }
        }
        return tree;
    }

    public static Route bruteForceRoute(Graph g, final boolean bb) {
        Route cheapest = new Route();
        ArrayList<Node> unvisited = g.getNodes();
        Node s = unvisited.remove(0); // Startknoten

        for(int i = 0; i < unvisited.size(); i++) {
            Node n = unvisited.remove(0);
            Route r = addEdgeToRoute(new Route(), s, n, g);
            if(!bb || checkCheapestRoute(r, cheapest)) {
                cheapest = recursiveBruteForce(g, r, unvisited, cheapest, bb);
            }
            unvisited.add(n);
        }
        return cheapest;
    }

    public static DijkstraTree djikstra(Graph g, Node s) {
        DijkstraTree tree = new DijkstraTree(g.countNodes(), s);
        Visited v = new Visited(g.countNodes());
        PriorityQueue<DijkstraQueueEntry> queue = new PriorityQueue<>();
        queue.add(new DijkstraQueueEntry(s, 0.0));
        while(v.notAllVisited()) {
            Node min = queue.poll().getN();
            v.setVisited(min);
            for (Edge e : min.getEdges()) {
                Node target = e.getTarget(min);
                if (!v.isVisited(target)) {
                    double costs = (e.getCosts() + tree.getDist(min));
                    if (tree.getDist(target) > costs) {
                        tree.setPrev(target, min);
                        tree.setDist(target, costs);
                    }
                    queue.add(new DijkstraQueueEntry(target, tree.getDist(target)));
                }
            }
        }
        return tree;
    }

    public static FlowGraph EdmondsKarp(Graph g, Node s, Node t) {
        Node v;
        Node u;
        FlowGraph fg = new FlowGraph(g);
        fg.checkIfResidualAndConstructIfNot();
        ArrayList<Node> p = doBreadthFirstSearch(fg, s, t);
        while (!p.isEmpty()) {
            // Get Min Capacity from s-t
            double max_Path_Flow = Double.MAX_VALUE;
            for (int i = 0; i < p.size() - 1; i++) {
                Node a = p.get(i);
                Node b = p.get(i + 1);

                try {
                    Edge e = fg.getEdgeFromNodes(a, b);
                    max_Path_Flow = Math.min(e.getCapacity(), max_Path_Flow);
                } catch (Exception ex) {

                }
            }

            // Add MaxFlow to all Edges s-t
            for (int i = 0; i < p.size() - 1; i++) {
                Node a = p.get(i);
                Node b = p.get(i + 1);

                try {
                    // from s-t
                    Edge e = fg.getEdgeFromNodes(a, b);
                    e.increaseFlow(max_Path_Flow);
                    e.setCapacity(e.getCapacity() - max_Path_Flow);

                    // from t-s
                    Edge e_rev = fg.getEdgeFromNodes(b, a);
                    e_rev.setCapacity(e_rev.getCapacity() + max_Path_Flow);
                } catch (Exception ex) {

                }
            }
            fg.increaseFlow(max_Path_Flow);
            p = doBreadthFirstSearch(fg, s, t);
        }
        return fg;
    }

    public static ArrayList<Node> doBreadthFirstSearch(Graph g, Node s, Node t) {
        ArrayList<Node> res = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        Visited v = new Visited(g.countNodes());
        queue.add(s);
        v.setVisited(s);
        Predesessor pre = new Predesessor(g, s);

        while(!queue.isEmpty()) {
            Node actual = queue.poll();
            for(Edge e: actual.getEdges()) {
                Node target = e.getTarget(actual);
                if(v.isNotVisited(target) && e.getCapacity() > 0.0) {
                    v.setVisited(target);
                    queue.add(target);
                    pre.setPrevNode(target, actual);
                    if(target.equals(t)) {
                        Node tmp = target;
                        while(!tmp.equals(s)) {
                            res.add(0, tmp);
                            tmp = pre.getPrevNode(tmp);
                        }
                        res.add(0, s);
                        return res;
                    }
                }
            }
        }
        return res;
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

    private static Route addEdgeToRoute(Route r, Node a, Node b, Graph g) {
        try{
            Edge tmp = g.getEdgeFromNodes(a, b);
            r.addEdge(tmp);
        }catch(EdgeNotFoundException e){

        }
        return r;
    }

    private static void addNewNodesToTree(Edge e, Node[] newNodes, ArrayList<Edge> edges) {
        //Erzeuge Knoten falls er noch nicht existiert & füge Knoten zu Baum hinzu
        Node a1 = createNewNodeIfNotExists(e.getA(), newNodes);
        Node b1 = createNewNodeIfNotExists(e.getB(), newNodes);
        Edge newEdge = new Edge(a1, b1, e.getCosts());
        a1.addEdge(newEdge);
        b1.addEdge(new Edge(b1, a1, e.getCosts()));
        edges.add(newEdge);
    }

    private static boolean checkCheapestRoute(Route r, Route currentCheapest) {
        if(currentCheapest.countEdges() == 0) {
            return true;
        }
        return r.totalCosts() < currentCheapest.totalCosts();
    }

    private static Node createNewNodeIfNotExists(Node a, Node[] newNodes) {
        if(newNodes[a.getLabel()] == null) {
            Node newA = new Node(a.getLabel());
            newNodes[a.getLabel()] = newA;
        }
        return newNodes[a.getLabel()];
    }

    private static Route recursiveBruteForce(Graph g, Route r, ArrayList<Node> unvisited, Route cheapest, final boolean bb){
        if(unvisited.isEmpty()){
            addEdgeToRoute(r, r.getLastNode(), r.getFirstNode(), g);
            if(checkCheapestRoute(r, cheapest)) { // vergleicht aktuelle Route mit bisher günstigsterer und gibt Günstigere zurück
                return r;
            } else {
                return cheapest;
            }
        }else{
            for(int i = 0; i < unvisited.size(); i++) {
                Node n = unvisited.remove(0);
                Route newRoute = new Route(r);
                addEdgeToRoute(newRoute, r.getLastNode(), n, g);
                if(!bb || checkCheapestRoute(newRoute, cheapest)) { //cheapest ist bis Erstellung der ersten Route immer leer, daher ist checkCheapestRoute erstmal immer True
                    cheapest = recursiveBruteForce(g, newRoute, unvisited, cheapest, bb);
                }
                unvisited.add(n);
            }
            return cheapest;
        }
    }
}
