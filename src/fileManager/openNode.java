package fileManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import nodeManager.*;

public class openNode {

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

    public void readFile(String fileName) {
        try {
            File myObj = new File(fileName);
            Scanner fileScanner = new Scanner(myObj);
            int lineNumber = 0;
            Pattern pattern = Pattern.compile("[\t\s :]");
            Pattern filepattern = Pattern.compile(" \n");

            if (fileScanner.hasNextInt()) {
                graphSize[0] = fileScanner.nextInt();
                // System.out.println(graphSize[0]);
            }
            if (fileScanner.hasNextInt()) {
                graphSize[1] = fileScanner.nextInt();
                // System.out.println(graphSize[1]);
            }

            fileScanner.useDelimiter(filepattern);
            fileScanner.nextLine();

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                Scanner lineScanner = new Scanner(line);
                lineScanner.useDelimiter(pattern);

                double weight[] = new double[4];
                int NodeNumber[] = { -1, -1, -1, -1 };
                int i = 0;

                while (lineScanner.hasNext()) {

                    if (lineScanner.hasNextInt()) {
                        int data = lineScanner.nextInt();
                        i = readFromStream(data, lineNumber, graphSize[0]);
                        if (i != -1)
                            NodeNumber[i] = data;

                    }

                    if (lineScanner.hasNextDouble()) {
                        double Doubledata = lineScanner.nextDouble();
                        weight[i] = Doubledata;

                    }

                    if (lineScanner.hasNext())
                        lineScanner.next();

                }
                nodesArr.add(new Node(weight, NodeNumber, false));

                lineNumber++;
                lineScanner.close();
            }

            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private int readFromStream(int actNumber, int lineNumber, int colNumber) {
        // System.out.println(actNumber + " " + lineNumber + " " + colNumber);

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

}
