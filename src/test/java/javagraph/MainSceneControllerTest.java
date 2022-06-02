package javagraph;

/**
 * 
 * @author Jakub Miętki
 */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.lang.reflect.InaccessibleObjectException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import javagraph.nodeManager.*;

public class MainSceneControllerTest {

    private MainSceneController MainScene;

    @BeforeEach
    void beforeEach() {
        MainScene = new MainSceneController();
    }

    @Test
    void drawNullgraph() {
        // given

        List<GraphNode> nodeList = null;
        int numberOfColumns = 10;
        int numberOfRows = 11;
        double range = 2;

        // when

        Throwable exception = assertThrows(
                InaccessibleObjectException.class, () -> {
                    MainScene.draw(numberOfRows, numberOfColumns, nodeList, range);
                });

        // then

        assertEquals("The graph does not exist", exception.getMessage());

    }

    @Test
    void drawRangeValues() {

        // given

        List<GraphNode> nodeList = new ArrayList<>();
        double weight[] = new double[4];
        int NodeNumber[] = { 1, 1, 1, 1 };
        GraphNode node = new GraphNode(weight, NodeNumber, false, 10);
        nodeList.add(node);

        int numberOfColumns = 10;
        int numberOfRows = 11;
        double range = 0;

        // when

        Throwable exception = assertThrows(
                InaccessibleObjectException.class, () -> {
                    MainScene.draw(numberOfRows, numberOfColumns, nodeList, range);
                });

        // then

        assertEquals("Bad range", exception.getMessage());
    }

    @Test
    void drawSizeValues() {

        // given

        List<GraphNode> nodeList = new ArrayList<>();
        double weight[] = new double[4];
        int NodeNumber[] = { 1, 1, 1, 1 };
        GraphNode node = new GraphNode(weight, NodeNumber, false, 10);
        nodeList.add(node);

        int numberOfColumns = 0;
        int numberOfRows = -11;
        double range = 2;

        // when

        Throwable exception = assertThrows(
                InaccessibleObjectException.class, () -> {
                    MainScene.draw(numberOfRows, numberOfColumns, nodeList, range);
                });

        // then

        assertEquals("Bad graph size", exception.getMessage());
    }

    @Test
    void readFromArea_stringOrEmptyValues() {

        // given
        String edgeRange = " ";
        String gridSize = " ";

        String edgeRange_string = "dasasd";
        String gridSize_string = "adsasd";

        String edgeRange_similarNum = "1 -2";
        String gridSize_similarNum = "0 x2";

        // when

        Throwable exception = assertThrows(
                IOException.class, () -> {
                    MainScene.readFromArea(edgeRange, gridSize);
                });

        Throwable exception_string = assertThrows(
                IOException.class, () -> {
                    MainScene.readFromArea(edgeRange_string, gridSize_string);
                });

        Throwable exception_similarNum = assertThrows(
                IOException.class, () -> {
                    MainScene.readFromArea(edgeRange_similarNum, gridSize_similarNum);
                });

        // then

        assertEquals("Please provide correct data", exception.getMessage());
        assertEquals("Please provide correct data", exception_string.getMessage());
        assertEquals("Please provide correct data", exception_similarNum.getMessage());

    }
}
