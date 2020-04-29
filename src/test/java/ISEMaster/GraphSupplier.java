package ISEMaster;

import java.io.File;
import java.util.TreeMap;

public class GraphSupplier {
    private static TreeMap<String, Graph> map = new TreeMap<>();

    public static Graph getGraph(String filename) {
        return getGraph(filename, false);
    }

    public static Graph getGraph(String filename, boolean directed) {
        try {
            return new Graph(new File(filename), directed);
        } catch (Exception e) {
            return null;
        }
        /*
        if(!map.containsKey(filename)) {
            try {
                map.put(filename, new Graph(new File(filename)));
            } catch (Exception e){
                return null;
            }
        }
        return map.get(filename);
         */
    }
}
