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
            addNodeToGroup(n,g);
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

    public ArrayList<Node> getNodesToGroup(int groupID) {
        return groupToNodes.get(groupID);
    }

    public void unionGroups(Node a, Node b) {
        int g1 = getGroupId(a);
        int g2 = getGroupId(b);
        /*if(g1 == g2) { überflüssig? da schon im Algorithmus selbst geprüft
            return;
        }*/
        //speichere Nodes einer Gruppe ab und überprüfe ob Nodelist von g1 größer als g2
        //falls ja: füge Nodes der Gruppe g2 zur größeren Gruppe g1 hinzu und lösche g2
        //falls nicht: füge Nodes der Gruppe g1 zur Gruppe g2 hinzu und lösche g1
        ArrayList<Node> nodes = getNodesToGroup(g1);
        if(getNodesToGroup(g2).size() < nodes.size()) {
            nodes = getNodesToGroup(g2);
            groupToNodes.remove(g2);
            changeGroupId(nodes, g1);
        } else {
            groupToNodes.remove(g1);
            changeGroupId(nodes, g2);
        }
    }

    private void changeGroupId(ArrayList<Node> nodes, int groupID) {
        for (Node n: nodes) {
            addNodeToGroup(n, groupID);
        }
    }

    public String toString() {
        return groupToNodes.size() + " " + nodeToGroup.size();
    }
}
