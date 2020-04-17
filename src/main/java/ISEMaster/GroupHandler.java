package ISEMaster;

import java.util.ArrayList;

public class GroupHandler {

    private ArrayList<ArrayList<Node>> groupToNodes = new ArrayList<>();
    private ArrayList<Integer> nodeToGroup = new ArrayList<>();

    public GroupHandler(ArrayList<Node> nodes) {
        for(int i = 0; i < nodes.size(); i++) {
            groupToNodes.add(new ArrayList<Node>());
            groupToNodes.get(i).add(nodes.get(i));
            nodeToGroup.add(i);
        }
    }

    public void addNodeToGroup(Node n, int g) {
        groupToNodes.get(g).add(n);
        nodeToGroup.set(n.getLabel(), g);
    }

    public Integer getGroupId(Node n) {
        return nodeToGroup.get(n.getLabel());
    }

    public ArrayList<Node> getNodesToGroup(int groupID) {
        return groupToNodes.get(groupID);
    }

    public String toString() {
        return groupToNodes.size() + " " + nodeToGroup.size();
    }

    public void unionGroups(Node a, Node b) {
        int g1 = getGroupId(a);
        int g2 = getGroupId(b);
        if(g1 == g2) {
            //return;
        }
        //speichere Nodes einer Gruppe ab und überprüfe ob Nodelist von g1 größer als g2
        //falls ja: füge Nodes der Gruppe g2 zur größeren Gruppe g1 hinzu und lösche g2
        //falls nicht: füge Nodes der Gruppe g1 zur Gruppe g2 hinzu und lösche g1
        ArrayList<Node> nodes = getNodesToGroup(g1);
        if(groupToNodes.get(g2).size() < nodes.size()) {
            nodes = getNodesToGroup(g2);
            changeGroupId(nodes, g1, g2);
        } else {
            changeGroupId(nodes, g2, g1);
        }
    }

    private void changeGroupId(ArrayList<Node> nodes, int groupID, int oldGroup) {
        groupToNodes.set(oldGroup, new ArrayList<Node>());
        for (Node n: nodes) {
            addNodeToGroup(n, groupID);
        }
    }
}
