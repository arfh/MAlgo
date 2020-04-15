package ISEMaster;

import java.util.ArrayList;
import java.util.TreeMap;

public class GroupHandler {

    private TreeMap<Integer, ArrayList<Node>> groupToNodes = new TreeMap<>();
    private TreeMap<Node, Integer> nodeToGroup = new TreeMap<>();

    public GroupHandler(ArrayList<Node> nodes) {
        int g = 0;
        for(Node n: nodes) {
            if(!groupToNodes.containsKey(g)) {
                groupToNodes.put(g, new ArrayList<Node>());
            }
            groupToNodes.get(g).add(n);
            nodeToGroup.put(n, g);
            g++;
        }
    }

    public void addNodeToGroup(Node n, int g) {
        groupToNodes.get(g).add(n);
        nodeToGroup.put(n, g);
    }

    public Integer getGroupId(Node n) {
        return nodeToGroup.get(n);
    }

    public void unionGroups(Node a, Node b) {
        int g1 = nodeToGroup.get(a);
        int g2 = nodeToGroup.get(b);
        if(g1 == g2) {
            return;
        }
        ArrayList<Node> nodes = groupToNodes.get(g1);
        if(groupToNodes.get(g2).size() < nodes.size()) {
            nodes = groupToNodes.get(g2);
            groupToNodes.remove(g2);
            changeGroupId(nodes, g1);
        } else {
            groupToNodes.remove(g1);
            changeGroupId(nodes, g2);
        }
    }

    private void changeGroupId(ArrayList<Node> nodes, int groupID) {
        ArrayList<Node> gNodes = groupToNodes.get(groupID);
        for (Node n: nodes) {
            nodeToGroup.put(n, groupID);
            gNodes.add(n);
        }
    }

    public String toString() {
        return groupToNodes.size() + " " + nodeToGroup.size();
    }
}
