package ISEMaster;

import java.util.ArrayList;
import java.util.Stack;
import java.util.TreeSet;

public class Algorithm {

    public static ArrayList<Graph> getDepthFirstSearchTrees(Graph g) {
        ArrayList<Graph> trees = new ArrayList<>();
        TreeSet<Node> visited = new TreeSet<>();

        //Alle Knoten, die im Graph G existieren
        ArrayList<Node> knownNodes = g.getNodes();

        for (Node n: knownNodes) {

            if(visited.add(n) == false) {
                continue;
            } else {
                Stack<Node> l1 = new Stack<>();
                TreeSet<Node> l2 = new TreeSet<>();
                ArrayList<Edge> e0 = new ArrayList<>();
                l1.add(n);
                l2.add(n);
                while(l1.empty() == false) {
                    Node v = l1.pop();
                    visited.add(v);
                    ArrayList<Edge> edges = g.getEdges(v);
                    for (Edge e: edges) {
                        if(!l2.contains(e.getTarget(v))) {
                            e0.add(e);
                            l1.add(e.getTarget(v));
                            l2.add(e.getTarget(v));
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
