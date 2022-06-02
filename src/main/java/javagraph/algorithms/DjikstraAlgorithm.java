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

            // printf("dodaje %g z elementu: %d\t %d \n ", dist[i].w, dist[i].e, i);
        }

        while (!que.isEmpty()) {
            GraphNode node = que.remove(); // zwracam node z najmniejszym distance (w)
            // System.out.println(node.Id + " " + "data");

            if (node.visited)
                continue;

            for (int i = 0; i < 4; i++) {
                if (node.getNodeNum(i) == -1 || node.visited) {
                    // printf("do node: %d istnieje: %d\t %d \n",
                    // nodes[nodes[node.e].nodeNumber[i]].visited, nodes[node.e].nodeNumber[i], i);
                    continue;
                }
                // printf("do node: %d istnieje: %d\t %d \n", node.e,
                // nodes[node.e].nodeNumber[i], i);

                double newDist = node.getNodeWeight(i) + -1 * distances[node.Id];

                // printf("New dist: %g Old: %g \n", newDist,
                // -1*dist[nodes[node.e].nodeNumber[i]].w);

                if (newDist < -1 * distances[node.getNodeNum(i)]) {
                    distances[node.getNodeNum(i)] = newDist * -1;
                    list[node.getNodeNum(i)] = node.Id; // dodaje potomka, aby potem wyswietlic scieżke
                }

                if (newDist > MaxWeight) {
                    MaxWeight = newDist;
                }

                que.add(graph.getNodeList().get(node.getNodeNum(i)));
            }

            node.visited = true; // zwrocony node oznaczony jako odwiedzony

            // printf("Wyjęto %g z elementu: %d\n", -1*node.w, node.e);
        }

        System.out.println("Visited nodes: " + Arrays.toString(list));

        // while (true) {
        // double shortestDistance = Integer.MAX_VALUE;
        // int shortestIndex = -1;
        // for (int i = 0; i < graph.getGraphSize()[0] * graph.getGraphSize()[1]; i++) {
        // if (distances[i] < shortestDistance && !visited[i]) {
        // shortestDistance = distances[i];
        // shortestIndex = i;
        // }
        // }

        // // System.out.println("Visiting node " + shortestIndex + " with current
        // distance
        // // " + shortestDistance);

        // if (shortestIndex == -1) {
        // // System.out.println("Visited nodes: " + Arrays.toString(visited));
        // // System.out.println("Currently lowest distances: " +
        // // Arrays.toString(distances));
        // return distances;
        // }

        // for (int i = 0; i < 4; i++) {
        // int current = graph.getNodeList().get(shortestIndex).getNodeNum(i);

        // // System.out.println("data " + current);

        // if (current != -1 && distances[current] > distances[shortestIndex]
        // + graph.getNodeList().get(shortestIndex).getNodeWeight(i)) {

        // distances[current] = distances[shortestIndex]
        // + graph.getNodeList().get(shortestIndex).getNodeWeight(i);

        // if (distances[current] > MaxWeight) {
        // MaxWeight = distances[current];
        // }

        // // System.out.println("Updating distance of node "
        // // + convertNeighbourToIndex(shortestIndex, current, graph.getGraphSize()[0])
        // +
        // // " to "
        // // + distances[convertNeighbourToIndex(shortestIndex, current,
        // // graph.getGraphSize()[0])]);
        // }
        // }
        // visited[shortestIndex] = true;
        // // System.out.println("Visited nodes: " + Arrays.toString(visited));
        // // System.out.println("Currently lowest distances: " +
        // // Arrays.toString(distances));

        // }

    }

}
