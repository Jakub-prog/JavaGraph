package javagraph.algorithms;
import javagraph.nodeManager.Generator;

import java.util.Arrays;

public class DjikstraAlgorithm {

    private int readFromStream(int actNumber, int lineNumber, int colNumber) {

        if (actNumber - lineNumber == colNumber)
            return 3; // dol
        if (actNumber - lineNumber == -colNumber)
            return 2; // gora
        if (actNumber - lineNumber == 1)
            return 1; // prawo
        if (actNumber - lineNumber == -1)
            return 0; // lewo

        return -1;

    }

    private int convertNeighbourToIndex(int current, int neighbour, int size) {
        int x = readFromStream(neighbour, current, size);
        if (x == 3) {
            neighbour = current + size;
        }
        if (x == 2) {
            neighbour = current - size;
        }
        if (x == 1) {
            neighbour = current + 1;
        }
        if (x == 0) {
            neighbour = current -1;
        }
        return neighbour;
    }

    public double[] dj (int start, Generator graph) {

        double[] distances = new double[graph.getGraphSize()[0]*graph.getGraphSize()[1]];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[start] = 0;
        boolean[] visited = new boolean[graph.getGraphSize()[0]*graph.getGraphSize()[1]];

        while (true) {
            double shortestDistance = Integer.MAX_VALUE;
            int shortestIndex = -1;
            for (int i = 0; i < graph.getGraphSize()[0]*graph.getGraphSize()[1]; i++) {
                if (distances[i] < shortestDistance && !visited[i]) {
                    shortestDistance = distances[i];
                    shortestIndex = i;
                }
            }

            System.out.println("Visiting node " + shortestIndex + " with current distance " + shortestDistance);

            if (shortestIndex == -1) {
                return distances;
            }

            for (int i = 0; i < 4; i++) {
                if (graph.getNodeList().get(shortestIndex).getNodeNum(i) != -1 && distances[i] > distances[shortestIndex] + graph.getNodeList().get(shortestIndex).getNodeWeight(i)) {
                    distances[convertNeighbourToIndex(shortestIndex, i, graph.getGraphSize()[0])] = distances[shortestIndex] + graph.getNodeList().get(shortestIndex).getNodeWeight(i);
                    System.out.println("Updating distance of node " + convertNeighbourToIndex(shortestIndex, i, graph.getGraphSize()[0]) + " to " + distances[convertNeighbourToIndex(shortestIndex, i, graph.getGraphSize()[0])]);
                }
            }
            visited[shortestIndex] = true;
            System.out.println("Visited nodes: " + Arrays.toString(visited));
            System.out.println("Currently lowest distances: " + Arrays.toString(distances));

        }
    }


}






