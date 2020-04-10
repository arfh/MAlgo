package ISEMaster;

import java.util.ArrayList;
import java.util.Stack;
import java.util.TreeSet;

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

}
