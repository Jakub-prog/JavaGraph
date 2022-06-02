package javagraph.fileManager;

/**
 * 
 * @author Jakub Miętki
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import javagraph.nodeManager.*;

public class openNode {

    private static final double MAX_RANGE = 99999999;

    private List<GraphNode> nodesArr = new ArrayList<>();

    private int[] graphSize = new int[2];
    private double[] graphRange = { MAX_RANGE, 0 };

    public List<GraphNode> getNodeList() {
        return nodesArr;
    }

    public int[] getGraphSize() {
        return graphSize;
    }

    public double[] getGraphRange() {
        return graphRange;
    }

    /*
     * Reading files
     */
    public void readFile(String fileName) throws IOException {
        try {
            File myObj = new File(fileName);
            Scanner fileScanner = new Scanner(myObj);
            int lineNumber = 0;
            Pattern pattern = Pattern.compile("[\t :]");
            Pattern filepattern = Pattern.compile(" \n");

            if (fileScanner.hasNextInt()) {
                graphSize[0] = fileScanner.nextInt();
                if (graphSize[0] < 0) {
                    fileScanner.close();
                    throw new IOException("Bad file");

                }
            }
            if (fileScanner.hasNextInt()) {
                graphSize[1] = fileScanner.nextInt();
                if (graphSize[0] < 0) {
                    fileScanner.close();
                    throw new IOException("Bad file");

                }
            }

            fileScanner.useDelimiter(filepattern);
            fileScanner.nextLine();
            boolean doubleFlag = false;

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

                        if (data < 0 || doubleFlag || data > graphSize[0] * graphSize[1]) {
                            lineScanner.close();
                            fileScanner.close();
                            throw new IOException("Bad file format");
                        }
                        i = readFromStream(data, lineNumber, graphSize[0]);
                        doubleFlag = true;

                        if (i != -1)
                            NodeNumber[i] = data;

                    } else if (lineScanner.hasNextDouble()) {
                        double Doubledata = lineScanner.nextDouble();

                        if (Doubledata < 0 || !doubleFlag || Doubledata > MAX_RANGE) {
                            lineScanner.close();
                            fileScanner.close();
                            throw new IOException("Bad file format");

                        }

                        doubleFlag = false;

                        if (i != -1) {
                            weight[i] = Doubledata;
                            if (Doubledata > graphRange[1]) {
                                graphRange[1] = Doubledata;

                            }
                            if (Doubledata <= graphRange[0]) {
                                graphRange[0] = Doubledata;
                            }
                        }

                    } else if (lineScanner.hasNext()) {
                        lineScanner.next();

                    }

                }
                nodesArr.add(new GraphNode(weight, NodeNumber, false, lineNumber));

                lineNumber++;
                lineScanner.close();

            }

            fileScanner.close();

            if (lineNumber != graphSize[0] * graphSize[1]) {
                throw new IOException("Bad file format");
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            throw new IOException(e.getMessage());
        }
    }

    /*
     * This function return index of Node list
     */
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

}
