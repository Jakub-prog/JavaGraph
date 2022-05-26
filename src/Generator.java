import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator {

    public void generate(int a, int b, String FileName, boolean FileSave, double range_a, double range_b) {
        Random rand = new Random();
        double range = range_b - range_a;

        try {

            FileWriter fileWriter = new FileWriter(FileName);
            List<Node> nodesArr = new ArrayList<>();
            String data = "";

            fileWriter.write(a + " " + b + "\n");

            for (int i = 0; i < a; i++) {
                for (int j = 0; j < b; j++) {
                    data = "";
                    double weight[] = new double[4];
                    int NodeNumber[] = { -1, -1, -1, -1 };

                    // lewa
                    if (j % b != 0) {
                        weight[0] = rand.nextDouble(range) + range;
                        data += String.format("\t%3d :%.16f", (i * b + j - 1), weight[0]);
                        NodeNumber[0] = i * b + j - 1;
                    }
                    // prawa
                    if ((j + 1) % b != 0) {
                        weight[1] = rand.nextDouble(range) + range;
                        data += String.format("\t%3d :%.16f", (i * b + j - 1), weight[1]);

                        NodeNumber[1] = i * b + j + 1;
                    }
                    // gora
                    if (i % a != 0) {
                        weight[2] = rand.nextDouble(range) + range;
                        data += String.format("\t%3d :%.16f", (i * b + j - 1), weight[2]);

                        NodeNumber[2] = (i - 1) * b + j;
                    }
                    // dol
                    if ((i + 1) % a != 0) {
                        weight[3] = rand.nextDouble(range) + range;
                        data += String.format("\t%3d :%.16f", ((i + 1) * b + j), weight[3]);
                        NodeNumber[3] = (i + 1) * b + j;
                    }

                    nodesArr.add(new Node(weight, NodeNumber, false));
                    // System.out.println(NodeNumber[0] + " " + NodeNumber[1] + " " + NodeNumber[2]
                    // + " " + NodeNumber[3]);
                    data += "\n";
                    fileWriter.write(data);
                }
            }

            fileWriter.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // System.out.println(data);
        // for (Node node : nodesArr) {
        // System.out.println(node.getNodeNum(0) + " " + node.getNodeNum(1) + " " +
        // node.getNodeNum(2) + " "
        // + node.getNodeNum(3));

    }

}
