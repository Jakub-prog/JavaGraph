package javagraph.algorithms;

import javagraph.nodeManager.GraphNode;
import java.util.Comparator;

public class NodeComparator implements Comparator<GraphNode> {

    @Override
    public int compare(GraphNode s1, GraphNode s2) {
        if (s1.distance * -1 < s2.distance * -1)
            return 1;
        else if (s1.distance * -1 >= s2.distance * -1)
            return -1;
        return 0;
    }

}
