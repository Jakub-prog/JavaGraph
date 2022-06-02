package javagraph.nodeManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator {

    private List<GraphNode> nodesArr = new ArrayList<>();

    private int[] graphSize = new int[2];
    private double[] graphRange = new double[2];

    public List<GraphNode> getNodeList() {
        return nodesArr;
    }

    public int[] getGraphSize() {
        return graphSize;
    }

    public double[] getGraphRange() {
        return graphRange;
    }

    public void setNodeList(List<GraphNode> l) {
        nodesArr = l;
    }

    public void setGraphSize(int[] i) {
        graphSize = i;
    }

    public void setGraphRange(double[] d) {
        graphRange = d;
    }

    public void generate(int a, int b, double range_a, double range_b) throws IOException {

        if (a < 0 || b < 0 || (a == 0 && b == 0))
            throw new IOException("Bad graph size");

        if ((range_a == 0 && range_b == 0) || (range_a < 0 || range_b < 0) || range_a >= range_b)
            throw new IOException("Bad graph weights");

        graphSize[0] = a;
        graphSize[1] = b;
        graphRange[0] = range_a;
        graphRange[1] = range_b;
        Random rand = new Random();
        double range = range_b - range_a;
        int id = 0;

        for (int i = 0; i < b; i++) {
            for (int j = 0; j < a; j++) {
                double weight[] = new double[4];
                int NodeNumber[] = { -1, -1, -1, -1 };

                // lewa
                if (j != 0) {
                    weight[0] = rand.nextDouble() * range + range_a;
                    NodeNumber[0] = i * a + j - 1;
                }
                // prawa
                if ((j + 1) != a) {
                    weight[1] = rand.nextDouble() * range + range_a;
                    NodeNumber[1] = i * a + j + 1;
                }
                // gora
                if (i != 0) {
                    weight[2] = rand.nextDouble() * range + range_a;
                    NodeNumber[2] = (i - 1) * a + j;
                }
                // dol
                if ((i + 1) != b) {
                    weight[3] = rand.nextDouble() * range + range_a;
                    NodeNumber[3] = (i + 1) * a + j;
                }

                nodesArr.add(new GraphNode(weight, NodeNumber, false, id));
                id++;
            }
        }

    }

}
