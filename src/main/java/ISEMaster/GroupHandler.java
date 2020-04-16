package ISEMaster;

import java.util.ArrayList;
import java.util.TreeMap;

public class GroupHandler {

    private ArrayList<ArrayList<Node>> groupToNodes = new ArrayList<>();
    private ArrayList<Integer> nodeToGroup = new ArrayList<>();

    public GroupHandler(ArrayList<Node> nodes) {
        int g = 0;
        for(int i = 0; i < nodes.size(); i++) {
            groupToNodes.add(new ArrayList<Node>());
            groupToNodes.get(i).add(nodes.get(i));
            nodeToGroup.add(-1);
        }
        for(Node n: nodes) {
            nodeToGroup.set(n.getLabel(), n.getLabel());
        }
    }

    public void addNodeToGroup(Node n, int g) {
        groupToNodes.get(g).add(n);
        nodeToGroup.set(n.getLabel(), g);
    }

    public Integer getGroupId(Node n) {
        return nodeToGroup.get(n.getLabel());
    }

    public void unionGroups(Node a, Node b) {
        int g1 = nodeToGroup.get(a.getLabel());
        int g2 = nodeToGroup.get(b.getLabel());
        if(g1 == g2) {
            return;
        }
        ArrayList<Node> nodes = groupToNodes.get(g1);
        if(groupToNodes.get(g2).size() < nodes.size()) {
            nodes = groupToNodes.get(g2);
            groupToNodes.set(g2, new ArrayList<Node>());
            changeGroupId(nodes, g1);
        } else {
            groupToNodes.set(g1, new ArrayList<Node>());
            changeGroupId(nodes, g2);
        }
    }

    private void changeGroupId(ArrayList<Node> nodes, int groupID) {
        ArrayList<Node> gNodes = groupToNodes.get(groupID);
        for (Node n: nodes) {
            nodeToGroup.set(n.getLabel(), groupID);
            gNodes.add(n);
        }
    }

    public String toString() {
        return groupToNodes.size() + " " + nodeToGroup.size();
    }
}
