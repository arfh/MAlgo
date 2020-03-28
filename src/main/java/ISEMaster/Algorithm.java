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
                    Node v = l1.peek();
                    visited.add(v);
                    ArrayList<Edge> edges = g.getEdges(v);
                    int contains = 0;
                    for (Edge e: edges) {
                        if(l2.contains(e.getEnd())) {
                            contains++;
                        } else {
                            e0.add(e);
                            l1.add(e.getEnd());
                            l2.add(e.getEnd());
                            break;
                        }
                    }
                    if(contains == edges.size()) {
                        l1.pop();
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
