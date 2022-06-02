import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fileManager.*;
import nodeManager.*;

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
    private Canvas nodesArt = new Canvas(900, 900);

    private GraphicsContext gc;
    private double ovalWidth = 0;

    private Generator generator;
    private openNode newGraph;

    @FXML
    void btnDeleteClicked(ActionEvent event) {

    }

    @FXML
    void btnGenerateClicked(ActionEvent event) {

        int values[] = readFromArea();

        generator = new Generator();
        generator.generate(values[0], values[1], values[2], values[3]);
    }

    @FXML
    void btnOpenFromFileClicked(ActionEvent event) {

        savePopUp Popup = new savePopUp();
        String fileName = Popup.display();

        openPopUp openpop = new openPopUp();
        String newFiles = openpop.display();

        if (fileName != null) {

            newGraph = new openNode();
            fileName += ".txt";
            newGraph.readFile(fileName);
            double range = newGraph.getGraphRange()[1] - newGraph.getGraphRange()[0];

            draw(newGraph.getGraphSize()[0], newGraph.getGraphSize()[1], newGraph.getNodeList(), range);
        }
    }

    @FXML
    void btnRedrawClicked(ActionEvent event) {

        int values[] = readFromArea();
        generator = new Generator();
        generator.generate(values[0], values[1], values[2], values[3]);
        double range = generator.getGraphRange()[1] - generator.getGraphRange()[0];
        draw(generator.getGraphSize()[0], generator.getGraphSize()[1], generator.getNodeList(), range);

    }

    @FXML
    void btnSaveClicked(ActionEvent event) {

        savePopUp Popup = new savePopUp();
        String fileName = Popup.display();

        if (fileName != null) {

            saveNode saveGraph = new saveNode();
            fileName += ".txt";
            saveGraph.saveToFile(fileName, generator);

        }
    }

    @FXML
    void canvasMouseClicked(MouseEvent event) {

        double x = event.getX();
        double y = event.getY();

        double xposition = Math.floor(x / (ovalWidth * 1.2));
        double yposition = Math.floor(y / (ovalWidth * 1.2));

        // System.out.println("Rozmiar x:" + x);
        // System.out.println(xposition);

        // System.out.println("Rozmiar y:" + y);
        // System.out.println(yposition);

        changeNodeColor(xposition, yposition);

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

        gc = nodesArt.getGraphicsContext2D();

        generator = new Generator();
        generator.generate(15, 5, 0, 10);

        double range = generator.getGraphRange()[1] - generator.getGraphRange()[0];

        draw(generator.getGraphSize()[0], generator.getGraphSize()[1], generator.getNodeList(), range);

    }

    public void draw(int numberOfRows, int numberOfColumns, List<Node> nodeList, double range) {

        gc.clearRect(0, 0, nodesArt.getWidth(), nodesArt.getHeight());
        if (numberOfColumns < numberOfRows)
            ovalWidth = 900 / (numberOfRows * 1.2);
        else if (numberOfColumns >= numberOfRows)
            ovalWidth = 900 / (numberOfColumns * 1.2);

        gc.setFill(Color.GREY);

        for (int j = 0; j < numberOfColumns; j++) {
            for (int i = 0; i < numberOfRows; i++) {
                gc.fillOval(i * 1.2 * ovalWidth, j * 1.2 * ovalWidth, ovalWidth, ovalWidth);

                gc.setStroke(Color.BLUE);
                gc.setLineWidth(2);

                Node node = nodeList.get(numberOfRows * j + i);

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

    public void changeNodeColor(double i, double j) {
        gc.setFill(Color.BLUE);
        gc.fillOval(i * 1.2 * ovalWidth, j * 1.2 * ovalWidth, ovalWidth, ovalWidth);
    }

    public int[] readFromArea() {
        String edgeRange = EdgeTextField.getText();
        String gridSize = GridSizeTextField.getText();

        Pattern patternGrid = Pattern.compile("\\d+x\\d+");
        Matcher matcherGrid = patternGrid.matcher(gridSize.trim());
        Pattern patternEdge = Pattern.compile("\\d+-\\d+");
        Matcher matcherEdge = patternEdge.matcher(edgeRange.trim());

        if (matcherGrid.find() && matcherEdge.find()) {

            int rV[] = new int[4];
            gridSize = matcherGrid.group();

            String gridSizeValues[] = gridSize.split("x");
            edgeRange = matcherEdge.group();

            String edgeRangeValues[] = edgeRange.split("-");

            rV[0] = Integer.parseInt(gridSizeValues[0]);
            rV[1] = Integer.parseInt(gridSizeValues[1]);

            rV[2] = Integer.parseInt(edgeRangeValues[0]);
            rV[3] = Integer.parseInt(edgeRangeValues[1]);

            return rV;

        }

        else {
            int rV[] = new int[4];
            rV[0] = -1;
            return rV;
        }
    }

}
