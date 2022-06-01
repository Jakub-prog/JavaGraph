package fileManager;

import nodeManager.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class saveNode {

    private List<Node> nodesArr;

    public void saveToFile(String FileName, Generator generator) {

        nodesArr = generator.getNodeList();

        try {

            FileWriter fileWriter = new FileWriter(FileName);
            String data = "";
            int a = generator.getGraphSize()[0];
            int b = generator.getGraphSize()[1];

            fileWriter.write(a + " " + b + "\n");

            for (int i = 0; i < a; i++) {
                for (int j = 0; j < b; j++) {
                    data = "";
                    Node node = nodesArr.get(a * i + j);

                    // lewa
                    if (j % b != 0) {
                        data += String.format("\t%3d :%.16f", (i * b + j - 1), node.getNodeWeight(0));
                    }
                    // prawa
                    if ((j + 1) % b != 0) {
                        data += String.format("\t%3d :%.16f", (i * b + j + 1), node.getNodeWeight(1));
                    }
                    // gora
                    if (i != 0) {
                        data += String.format("\t%3d :%.16f", ((i - 1) * b + j), node.getNodeWeight(2));
                    }
                    // dol
                    if ((i + 1) != a) {
                        data += String.format("\t%3d :%.16f", ((i + 1) * b + j), node.getNodeWeight(3));
                    }
                    data += "\n";
                    fileWriter.write(data);
                }
            }

            fileWriter.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

}
