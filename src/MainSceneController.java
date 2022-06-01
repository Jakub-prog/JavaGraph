import java.io.FilenameFilter;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fileManager.savePopUp;
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

    private Generator generator = new Generator();

    @FXML
    void btnDeleteClicked(ActionEvent event) {

    }

    @FXML
    void btnGenerateClicked(ActionEvent event) {

        int values[] = readFromArea();

        generator.generate(values[0], values[1], "test.txt", false, values[2], values[3]);
    }

    @FXML
    void btnOpenFromFileClicked(ActionEvent event) {

    }

    @FXML
    void btnRedrawClicked(ActionEvent event) {

        int values[] = readFromArea();
        generator.generate(values[0], values[1], "test.txt", false, values[2], values[3]);
        draw(values[0], values[1]);

    }

    @FXML
    void btnSaveClicked(ActionEvent event) {

        savePopUp Popup = new savePopUp();
        String fileName = Popup.display();

        if (fileName != null) {
            System.out.println("hiiiiiiiiiii");
        } else {

        }
    }

    @FXML
    void canvasMouseClicked(MouseEvent event) {

        double x = event.getX();
        double y = event.getY();

        long xposition = Math.round(x / (ovalWidth * 2));
        long yposition = Math.round(y / (ovalWidth * 2));

        System.out.println("Rozmiar x:");
        System.out.println(xposition);

        System.out.println("Rozmiar y:");
        System.out.println(yposition);

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
        gc.setFill(Color.WHITE);
        System.out.println("color set to black");

        generator.generate(5, 5, "test.txt", false, 0, 10);

        draw(5, 5);

    }

    public void draw(int numberOfColumns, int numberOfRows) {
        gc.clearRect(0, 0, nodesArt.getWidth(), nodesArt.getHeight());
        if (numberOfColumns < numberOfRows)
            ovalWidth = 900 / (numberOfRows + numberOfRows - 1);
        else if (numberOfColumns >= numberOfRows)
            ovalWidth = 900 / (numberOfColumns + numberOfColumns - 1);

        System.out.println("oval width: " + ovalWidth);

        List<Node> nodeList = generator.getNodeList();
        double range = generator.getGraphRange()[1] - generator.getGraphRange()[0];

        for (int j = 0; j < numberOfColumns; j++) {
            for (int i = 0; i < numberOfRows; i++) {
                gc.fillOval(i * 2 * ovalWidth, j * 2 * ovalWidth, ovalWidth, ovalWidth);

                gc.setStroke(Color.BLUE);
                gc.setLineWidth(2);

                Node node = nodeList.get(numberOfColumns * j + i);

                // hue = 270, saturation & value = 1.0. inplicit alpha of 1.0
                // Color c = Color.hsb(270,1.0,1.0,1.0); //hue = 270, saturation & value = 1.0,
                // explicit alpha of 1.0

                // System((i * 2 * ovalWidth + ovalWidth) + " " + (j * 2 * ovalWidth
                // + ovalWidth * 0.5 - 2));

                if (i < numberOfRows - 1) {

                    Color c = Color.hsb((360 / range) * node.getNodeWeight(1), 1.0, 1.0);
                    gc.setStroke(c);
                    gc.strokeLine(i * 2 * ovalWidth + ovalWidth, j * 2 * ovalWidth + ovalWidth * 0.5,
                            (i + 1) * 2 * ovalWidth,
                            j * 2 * ovalWidth + ovalWidth * 0.5);
                }

                if (j < numberOfColumns - 1) {
                    Color c = Color.hsb((360 / range) * node.getNodeWeight(2), 1.0, 1.0);
                    gc.setStroke(c);
                    gc.strokeLine(i * 2 * ovalWidth + ovalWidth * 0.5, j * 2 * ovalWidth + ovalWidth,
                            i * 2 * ovalWidth + ovalWidth * 0.5,
                            (j + 1) * 2 * ovalWidth + ovalWidth);
                }

            }
        }
    }

    public int[] readFromArea() {
        String edgeRange = EdgeTextField.getText();
        String gridSize = GridSizeTextField.getText();

        Pattern patternGrid = Pattern.compile("\\d+x\\d+");
        Matcher matcherGrid = patternGrid.matcher(gridSize.trim());
        System.out.println(gridSize.trim());
        Pattern patternEdge = Pattern.compile("\\d+-\\d+");
        Matcher matcherEdge = patternEdge.matcher(edgeRange.trim());

        if (matcherGrid.find() && matcherEdge.find()) {

            int rV[] = new int[4];
            gridSize = matcherGrid.group();

            String gridSizeValues[] = gridSize.split("x");
            edgeRange = matcherEdge.group();

            String edgeRangeValues[] = edgeRange.split("-");

            System.out.println(matcherGrid.group());
            System.out.println(gridSizeValues[0] + " " + gridSizeValues[1]);
            System.out.println(edgeRangeValues[0] + " " + edgeRangeValues[1]);

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
