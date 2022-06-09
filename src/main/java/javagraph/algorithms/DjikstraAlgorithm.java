package javagraph.algorithms;

import javagraph.nodeManager.Generator;
import javagraph.nodeManager.GraphNode;

import java.util.Arrays;
import java.util.PriorityQueue;

public class DjikstraAlgorithm {

    private static final int INFI = 99999999;
    private double MaxWeight = 0;
    public double[] distances;
    public int[] list;

    public double getMaxWeight() {
        return MaxWeight;
    }

    public void dj(int starting_node, Generator graph) {

        int nodesAmount = graph.getGraphSize()[0] * graph.getGraphSize()[1];
        distances = new double[graph.getGraphSize()[0] * graph.getGraphSize()[1]];
        list = new int[nodesAmount];

        Arrays.fill(distances, Integer.MAX_VALUE);

        PriorityQueue<GraphNode> que = new PriorityQueue<GraphNode>(nodesAmount, new NodeComparator());

        for (int i = 0; i < nodesAmount; i++) {

            distances[i] = -1 * INFI;
            graph.getNodeList().get(i).distance = -1 * INFI;

            list[i] = -1;

            if (i == starting_node) {
                distances[i] = 0;
                que.add(graph.getNodeList().get(i));
            }
        }

        while (!que.isEmpty()) {
            GraphNode node = que.remove(); // zwracam node z najmniejszym distance (w)

            if (node.visited)
                continue;

            for (int i = 0; i < 4; i++) {
                if (node.getNodeNum(i) == -1 || node.visited) {
                    continue;
                }

                double newDist = node.getNodeWeight(i) + -1.0 * distances[node.Id];

                if (newDist < -1.0 * distances[node.getNodeNum(i)]) {
                    distances[node.getNodeNum(i)] = newDist * -1;
                    list[node.getNodeNum(i)] = node.Id;
                }

                que.add(graph.getNodeList().get(node.getNodeNum(i)));
            }

            node.visited = true; // zwrocony node oznaczony jako odwiedzony
        }

        for (double d : distances) {
            if (d * -1.0 > MaxWeight) {
                MaxWeight = d * -1.0;
            }
        }
    }
}
