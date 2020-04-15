package ISEMaster;

import java.util.ArrayList;
import java.util.TreeMap;

public class GroupHandler {

    private TreeMap<Integer, ArrayList<Node>> groupToNodes = new TreeMap<>();
    private TreeMap<Node, Integer> nodeToGroup = new TreeMap<>();

    public GroupHandler(ArrayList<Node> nodes) {
        System.out.println(nodes.size());
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

    public void unionGroups(int g1, int g2) {
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

    public void unionGroupsByNodes(Node a, Node b) {
        unionGroups(nodeToGroup.get(a), nodeToGroup.get(b));
    }

    private void changeGroupId(ArrayList<Node> nodes, int groupID) {
        for (Node n: nodes) {
            nodeToGroup.put(n, groupID);
        }
    }
}
