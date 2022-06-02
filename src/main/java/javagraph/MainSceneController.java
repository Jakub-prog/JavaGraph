package javagraph;

/**
 * 
 * @author Jakub Miętki
 */

import java.io.IOException;
import java.lang.reflect.InaccessibleObjectException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javagraph.algorithms.DjikstraAlgorithm;
import javagraph.fileManager.*;
import javagraph.nodeManager.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class MainSceneController implements Initializable {

    private static final double CANVAS_SIZE = 700;

    @FXML
    private Label EdgeLabel;

    @FXML
    private TextField EdgeTextField;

    @FXML
    private Label GridLabel;

    @FXML
    private TextField GridSizeTextField;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnGenerate;

    @FXML
    private Button btnOpenFromFile;

    @FXML
    private Button btnRedraw;

    @FXML
    private Button btnSave;

    @FXML
    private Button down;

    @FXML
    private Button left;

    @FXML
    private Button right;

    @FXML
    private Button up;

    @FXML
    private Button zoomIn;

    @FXML
    private Button zoomOut;

    @FXML
    private TextField MessageTextField;

    @FXML
    private Canvas nodesArt = new Canvas(900, 900);

    private GraphicsContext gc;
    private double ovalWidth = 0;

    private Generator generator;
    private openNode newGraph;

    @FXML
    void btnDeleteClicked(ActionEvent event) {
        gc.clearRect(0, 0, nodesArt.getWidth(), nodesArt.getHeight());
        setMessage("Wyczyszczono");

    }

    @FXML
    void btnGenerateClicked(ActionEvent event) {

        try {
            int values[] = readFromArea(EdgeTextField.getText(), GridSizeTextField.getText());

            generator = new Generator();
            generator.generate(values[0], values[1], values[2], values[3]);
            setMessage("");

        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    }

    @FXML
    void btnOpenFromFileClicked(ActionEvent event) {

        try {

            openPopUp Popup = new openPopUp();
            String fileName = Popup.display();

            if (fileName != null) {

                newGraph = new openNode();
                fileName += ".txt";
                newGraph.readFile(fileName);
                double range = newGraph.getGraphRange()[1] - newGraph.getGraphRange()[0];

                draw(newGraph.getGraphSize()[0], newGraph.getGraphSize()[1], newGraph.getNodeList(), range);

            }
            setMessage("");

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    @FXML
    void btnRedrawClicked(ActionEvent event) {

        try {
            int values[] = readFromArea(EdgeTextField.getText(), GridSizeTextField.getText());
            generator = new Generator();
            generator.generate(values[0], values[1], values[2], values[3]);
            double range = generator.getGraphRange()[1] - generator.getGraphRange()[0];
            draw(generator.getGraphSize()[0], generator.getGraphSize()[1], generator.getNodeList(), range);
            setMessage("");

        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    }

    @FXML
    void btnSaveClicked(ActionEvent event) {

        try {
            savePopUp Popup = new savePopUp();
            String fileName = Popup.display();

            if (fileName != null) {

                saveNode saveGraph = new saveNode();
                fileName += ".txt";
                saveGraph.saveToFile(fileName, generator);

            }
            setMessage("");

        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    }

    @FXML
    void canvasMouseClicked(MouseEvent event) {

        double x = event.getX();
        double y = event.getY();

        double xposition = Math.floor(x / (ovalWidth * 1.2));
        double yposition = Math.floor(y / (ovalWidth * 1.2));

        int start = (int) (yposition * generator.getGraphSize()[0] + xposition);
        System.out.println(start);

        DjikstraAlgorithm dj = new DjikstraAlgorithm();

        double[] djPath = dj.dj(start, generator);

        drawDj(djPath, generator.getGraphSize()[0], generator.getGraphSize()[1], dj.getMaxWeight());

    }

    @FXML
    void zoomInCanvas(ActionEvent event) {
        System.out.println("Started zoom");
    }

    @FXML
    void zoomOutCanvas(ActionEvent event) {
        System.out.println("Ended zoom");
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        try {
            gc = nodesArt.getGraphicsContext2D();

            generator = new Generator();
            generator.generate(10, 5, 0, 10);

            double range = generator.getGraphRange()[1] - generator.getGraphRange()[0];

            draw(generator.getGraphSize()[0], generator.getGraphSize()[1], generator.getNodeList(), range);

            setMessage("");
        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    }

    public void setMessage(String s) {
        MessageTextField.setText(s);
    }

    public void drawDj(double[] dj, int i, int j, double max) {

        for (int a = 0; a < i; a++) {
            for (int b = 0; b < j; b++) {
                Color c = Color.hsb(255 - (255 / max) * dj[i * b + a], 1.0, 1.0);
                changeNodeColor(a, b, c);
                // System.out.println(dj[i * b + j] + " data " + (255 - (255 / max) * dj[i * b +
                // j]));
            }

        }
    }

    public void draw(int numberOfRows, int numberOfColumns, List<GraphNode> nodeList, double range)
            throws InaccessibleObjectException {

        if (nodeList == null)
            throw new InaccessibleObjectException("The graph does not exist");

        if (numberOfRows < 0 || numberOfColumns < 0 || (numberOfRows == 0 && numberOfColumns == 0))
            throw new InaccessibleObjectException("Bad graph size");

        if (range <= 0)
            throw new InaccessibleObjectException("Bad range");

        gc.clearRect(0, 0, nodesArt.getWidth(), nodesArt.getHeight());
        if (numberOfColumns < numberOfRows)
            ovalWidth = CANVAS_SIZE / (numberOfRows * 1.2);
        else if (numberOfColumns >= numberOfRows)
            ovalWidth = CANVAS_SIZE / (numberOfColumns * 1.2);

        gc.setFill(Color.GREY);

        for (int j = 0; j < numberOfColumns; j++) {
            for (int i = 0; i < numberOfRows; i++) {
                gc.fillOval(i * 1.2 * ovalWidth, j * 1.2 * ovalWidth, ovalWidth, ovalWidth);

                gc.setStroke(Color.BLUE);
                gc.setLineWidth(2);

                GraphNode node = nodeList.get(numberOfRows * j + i);

                // System.out.println("data: " + node.getNodeWeight(1) + " " +
                // node.getNodeWeight(3));

                if (i < numberOfRows - 1) {

                    Color c = Color.hsb(255 - (255 / range) * node.getNodeWeight(1), 1.0, 1.0);

                    gc.setStroke(c);
                    gc.strokeLine(i * 1.2 * ovalWidth + ovalWidth, j * 1.2 * ovalWidth + ovalWidth *
                            0.5,
                            (i + 1) * 1.2 * ovalWidth,
                            j * 1.2 * ovalWidth + ovalWidth * 0.5);
                }

                if (j < numberOfColumns - 1) {
                    Color c = Color.hsb(255 - (255 / range) * node.getNodeWeight(3), 1.0, 1.0);

                    gc.setStroke(c);
                    gc.strokeLine(i * 1.2 * ovalWidth + ovalWidth * 0.5, j * 1.2 * ovalWidth +
                            ovalWidth,
                            i * 1.2 * ovalWidth + ovalWidth * 0.5,
                            (j + 1) * 1.2 * ovalWidth + ovalWidth);
                }

            }
        }
    }

    public void changeNodeColor(double i, double j, Color c) {
        gc.setFill(c);
        gc.fillOval(i * 1.2 * ovalWidth, j * 1.2 * ovalWidth, ovalWidth, ovalWidth);
    }

    /*
     * Reading values (graph range and graph size) from
     * text fields, after button action:
     * generate, redraw
     */

    public int[] readFromArea(String edgeRange, String gridSize) throws IOException {

        Pattern patternGrid = Pattern.compile("\\d+x\\d+");
        Matcher matcherGrid = patternGrid.matcher(gridSize.trim());
        Pattern patternEdge = Pattern.compile("\\d+-\\d+");
        Matcher matcherEdge = patternEdge.matcher(edgeRange.trim());

        if (matcherGrid.find() && matcherEdge.find()) {

            int rV[] = { -1, -1, -1, -1 };
            gridSize = matcherGrid.group();

            String gridSizeValues[] = gridSize.split("x");
            edgeRange = matcherEdge.group();

            String edgeRangeValues[] = edgeRange.split("-");

            rV[0] = Integer.parseInt(gridSizeValues[0]);
            rV[1] = Integer.parseInt(gridSizeValues[1]);

            rV[2] = Integer.parseInt(edgeRangeValues[0]);
            rV[3] = Integer.parseInt(edgeRangeValues[1]);

            System.out.println(rV[0] + " data " + rV[1]);

            return rV;

        }

        else {
            int rV[] = new int[4];
            rV[0] = -1;
            throw new IOException("Please provide correct data");
        }
    }

}
