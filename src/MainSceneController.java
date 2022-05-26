import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.Component;
import java.awt.MouseInfo;
import java.awt.Point;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ZoomEvent;
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

    @FXML
    void btnDeleteClicked(ActionEvent event) {

    }

    @FXML
    void btnGenerateClicked(ActionEvent event) {
        gc.setFill(Color.AQUA);
        gc.fillRect(10, 10, 100, 100);
    }

    @FXML
    void btnOpenFromFileClicked(ActionEvent event) {

    }

    @FXML
    void btnRedrawClicked(ActionEvent event) {

        String edgeRange = EdgeTextField.getText();
        String gridSize = GridSizeTextField.getText();

        System.out.println("err");

        Pattern patternGrid = Pattern.compile("\\d+x\\d+");
        Matcher matcherGrid = patternGrid.matcher(gridSize.trim());
        System.out.println(gridSize.trim());
        Pattern patternEdge = Pattern.compile("\\d+-\\d+");
        Matcher matcherEdge = patternEdge.matcher(edgeRange.trim());

        if (matcherGrid.find() && matcherEdge.find()) {

            gridSize = matcherGrid.group();

            String gridSizeValues[] = gridSize.split("x");
            edgeRange = matcherEdge.group();

            String edgeRangeValues[] = edgeRange.split("-");

            System.out.println(matcherGrid.group());
            System.out.println(gridSizeValues[0] + " " + gridSizeValues[1]);
            System.out.println(edgeRangeValues[0] + " " + edgeRangeValues[1]);

            int a = Integer.parseInt(gridSizeValues[0]);
            int b = Integer.parseInt(gridSizeValues[1]);

            draw(a, b);

        } else {
            System.out.println("Bad values");
        }

    }

    @FXML
    void btnSaveClicked(ActionEvent event) {

    }

    @FXML
    void canvasMouseClicked(MouseEvent event) {

        // System.out.println(event.getX() - nodesArt.getLayoutX());
        System.out.println(event.getY());
        System.out.println(event.getX());

        // nodesArt.getLayoutY()

    }

    @FXML
    void zoomInCanvas(ZoomEvent event) {
        System.out.println("Started zoom");
    }

    @FXML
    void zoomOutCanvas(ZoomEvent event) {
        System.out.println("Ended zoom");
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        gc = nodesArt.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        System.out.println("color set to black");

        int numberOfRows = 76;
        int numberOfColumns = 75;

        draw(numberOfColumns, numberOfRows);

    }

    public void draw(int numberOfColumns, int numberOfRows) {
        gc.clearRect(0, 0, nodesArt.getWidth(), nodesArt.getHeight());
        double ovalWidth = 0;
        if (numberOfColumns < numberOfRows)
            ovalWidth = 900 / (numberOfRows + numberOfRows - 1);
        else if (numberOfColumns >= numberOfRows)
            ovalWidth = 900 / (numberOfColumns + numberOfColumns - 1);

        System.out.println("oval width: " + ovalWidth);

        for (int j = 0; j < numberOfColumns; j++) {
            for (int i = 0; i < numberOfRows; i++) {
                gc.fillOval(i * 2 * ovalWidth, j * 2 * ovalWidth, ovalWidth, ovalWidth);
                gc.setStroke(Color.BLUE);
                gc.setLineWidth(2);

                // System.out.println((i * 2 * ovalWidth + ovalWidth) + " " + (j * 2 * ovalWidth
                // + ovalWidth * 0.5 - 2));

                if (i < numberOfRows - 1)
                    gc.strokeLine(i * 2 * ovalWidth + ovalWidth, j * 2 * ovalWidth + ovalWidth * 0.5,
                            (i + 1) * 2 * ovalWidth,
                            j * 2 * ovalWidth + ovalWidth * 0.5);

                gc.setStroke(Color.BLUE);
                if (j < numberOfColumns - 1)
                    gc.strokeLine(i * 2 * ovalWidth + ovalWidth * 0.5, j * 2 * ovalWidth + ovalWidth,
                            i * 2 * ovalWidth + ovalWidth * 0.5,
                            (j + 1) * 2 * ovalWidth + ovalWidth);
            }
        }
        System.out.println("draw rectangle");
    }

}
