package ISEMaster;

import java.lang.reflect.Array;
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

    public static FlowGraph EdmondsKarp(Graph g, Node s, Node t) {
        FlowGraph fg = new FlowGraph(g);
        fg.checkIfResidualAndConstructIfNot();
        ArrayList<Node> p = doBreadthFirstSearch(fg, s, t, null);
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
                    e_rev.decreaseFlow(max_Path_Flow);
                    e_rev.setCapacity(e_rev.getCapacity() + max_Path_Flow);
                } catch (Exception ex) {

                }
            }
            fg.increaseFlow(max_Path_Flow);
            p = doBreadthFirstSearch(fg, s, t, null);
        }
        return fg;
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

    public static PreviousStructure bellmanFord(Graph g, Node s) {
        PreviousStructure tree = new PreviousStructure(g.countNodes(), s);
        ArrayList<Edge> edges = g.getAllEdges();
        for(int i = 1; i < g.countNodes(); i++) {
            for(Edge e: edges) {
                if(e.getCapacity() > 0.0) {
                    Node v = e.getA();
                    Node w = e.getB();
                    Double ce = e.getCosts();
                    double cw = tree.getDist(w);
                    double tmpc = tree.getDist(v) + ce;

                    if (tmpc < cw) {
                        tree.setDist(w, tmpc);
                        tree.setPrev(w, v);
                    }
                }
            }
        }

        for(Edge e: edges) {
            if(e.getCapacity() > 0.0) {
                double cv = tree.getDist(e.getA());
                double ce = e.getCosts();
                double cw = tree.getDist(e.getB());
                if ((cv + ce) < cw) {
                    tree.setToNegaticeCyle();
                    tree.constructNegativCycle(e.getA(), g);
                    return tree;
                }
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

    public static double cycleCanceling (FlowGraph g) throws NoBFlowPossibleException {
        List<Node> sources = g.getSources();
        List<Node> targets = g.getTargets();

        //Schritt 1
        Node superS = g.addNode();
        for(Node source: sources) {
            Edge e = new Edge(superS, source, 0.0, source.getBalance());
            superS.addEdge(e);
            superS.setBalance(superS.getBalance() + source.getBalance());
            //source.setBalance(0.0);
        }

        Node superT = g.addNode();
        for(Node target: targets) {
            Double cap = target.getBalance();
            if(cap < 0) {
                cap = cap * (-1.0);
            }
            Edge e = new Edge(target, superT, 0.0, cap);
            target.addEdge(e);

            superT.setBalance(superT.getBalance() + target.getBalance());
            //target.setBalance(0.0);
        }

        // Schritt 1
        g = EdmondsKarp(g, superS, superT);

        if(g.getMaxflow() == superS.getBalance() && -g.getMaxflow() == superT.getBalance()){
            boolean ready = false;
            Visited v = new Visited(g.countNodes());
            while(!ready) {
                for(Node n: g.getNodes()) {
                    v.setVisited(n);
                    // Schritt 3
                    PreviousStructure prev = bellmanFord(g, n);

                    ArrayList<Edge> negativeCycle = prev.getNegativeCycle();

                    if (v.allVisited()) {
                        ready = true;
                        break;
                    }

                    // Schritt 4
                    Double minCapacity = prev.getMinNegativCylcleCapacity();

                    for(Edge e: negativeCycle) {
                        e.decreaseCapacity(minCapacity);
                        e.increaseFlow(minCapacity);
                        try {
                            Edge rev = g.getEdgeFromNodes(e.getB(), e.getA());
                            rev.increaseCapacity(minCapacity);
                            rev.decreaseFlow(minCapacity);
                        } catch (EdgeNotFoundException edgeNotFoundException) {
                        }
                    }
                }
            }
            return calculateMinMaxCosts(g);
        }
        else{
            throw new NoBFlowPossibleException();
        }




    }

    public static PreviousStructure djikstra(Graph g, Node s) {
        PreviousStructure tree = new PreviousStructure(g.countNodes(), s);
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

    public static ArrayList<Node> doBreadthFirstSearch(Graph g, Node s, Node t, Visited v) {
        ArrayList<Node> res = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        if(v == null){
            v = new Visited(g.countNodes());
        }
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

    public static double ssp(FlowGraph g) throws NoCostMinimalFlowException {
        g.checkIfResidualAndConstructIfNot();
        for(Edge e: g.getAllEdges()) {
            if(!e.isResidualEdge() && e.getCosts() < 0) { // Der Fluss ist stdandardmäßig eh 0
                e.setFlow(e.getCapacity());
                e.setCapacity(0.0);

                try{
                    Edge rev = g.getEdgeFromNodes(e.getB(),e.getA());
                    rev.setCapacity(e.getFlow());
                }catch(EdgeNotFoundException ex){

                }
            }
            Node a = e.getA();
            a.increaseIsBalance(e.getFlow());
            Node b = e.getB();
            b.decreaseIsBalance(e.getFlow());
        }
        System.out.println("");
        int iteration = 0;
        while(true) {
            Node s = getSNode(g);
            Node t = getTNode(g, s);
            if(s == null || t == null) {
                if(isCostMinimal(g)) {
                    return calculateMinMaxCosts(g);
                }
                throw new NoCostMinimalFlowException();
            }

            PreviousStructure prev = bellmanFord(g, s);
            ArrayList<Node> p = prev.getPath(s, t);

            double gamma = Math.min(s.getBalance()-s.getIsBalance(), t.getIsBalance() - t.getBalance());
            for(int i = 0; i < p.size()-1; i++) {
                Node a = p.get(i);
                Node b = p.get(i+1);

                try {
                    Edge e = g.getEdgeFromNodes(a, b);
                    gamma = Math.min(gamma, e.getCapacity());
                } catch (EdgeNotFoundException edgeNotFoundException) {}

            }

            for(int i = 0; i < p.size()-1; i++) {
                Node a = p.get(i);
                Node b = p.get(i+1);

                try {
                    Edge e = g.getEdgeFromNodes(a, b);
                    Edge rev = g.getEdgeFromNodes(b, a);

                    e.increaseFlow(gamma);
                    e.decreaseCapacity(gamma);
                    rev.decreaseFlow(gamma);
                    rev.increaseCapacity(gamma);
                } catch (EdgeNotFoundException ex) {
                    ex.printStackTrace();
                    return 0.0;
                }
            }
            s.increaseIsBalance(gamma);
            t.decreaseIsBalance(gamma);
            iteration++;
        }
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

    private static double calculateMinMaxCosts(Graph g) {
        double costs = 0.0;
        for(Edge e: g.getAllEdges()) {
            if(!e.isResidualEdge()) {
                costs += (e.getFlow()*e.getCosts());
            }
        }
        return costs;
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

    private static Node getSNode(FlowGraph g) {
        Node s = null;
        for(Node n: g.getNodes()) {
            if((n.getBalance()-n.getIsBalance()) > 0) {
                s=n;
                break;
            }
        }
        return s;
    }

    private static Node getTNode(FlowGraph g, Node s) {
        if(s == null)
            return null;

        Visited v = new Visited(g.countNodes());
        doBreadthFirstSearch(g, s, null, v);
        for(int i=0; i<v.getVarray().length; i++){
            Node n = g.getNodes().get(i);
            if(v.isVisited(n) && (n.getBalance() - n.getIsBalance()) < 0){
                return n;
            }
        }

        return null;
    }

    private static boolean isCostMinimal(FlowGraph g) {
        for(Node n: g.getNodes()) {
            if(n.getBalance() != n.getIsBalance()) {
                return false;
            }
        }
        return true;
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
