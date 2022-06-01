package nodeManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator {

    private List<Node> nodesArr = new ArrayList<>();

    private int[] graphSize = new int[2];
    private double[] graphRange = new double[2];

    public List<Node> getNodeList() {
        return nodesArr;
    }

    public int[] getGraphSize() {
        return graphSize;
    }

    public double[] getGraphRange() {
        return graphRange;
    }

    public void generate(int a, int b, double range_a, double range_b) {
        graphSize[0] = a;
        graphSize[1] = b;
        graphRange[0] = range_a;
        graphRange[1] = range_b;
        Random rand = new Random();
        double range = range_b - range_a;

        for (int i = 0; i < a; i++) {
            for (int j = 0; j < b; j++) {
                double weight[] = new double[4];
                int NodeNumber[] = { -1, -1, -1, -1 };

                // lewa
                if (j % b != 0) {
                    weight[0] = rand.nextDouble(range) + range_a;
                    NodeNumber[0] = i * b + j - 1;
                }
                // prawa
                if ((j + 1) % b != 0) {
                    weight[1] = rand.nextDouble(range) + range_a;
                    NodeNumber[1] = i * b + j + 1;
                }
                // gora
                if (i != 0) {
                    weight[2] = rand.nextDouble(range) + range_a;
                    NodeNumber[2] = (i - 1) * b + j;
                }
                // dol
                if ((i + 1) != a) {
                    weight[3] = rand.nextDouble(range) + range_a;
                    NodeNumber[3] = (i + 1) * b + j;
                }

                nodesArr.add(new Node(weight, NodeNumber, false));
            }
        }

    }

}
