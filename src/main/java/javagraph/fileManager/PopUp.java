package javagraph.fileManager;

/**
 * 
 * @author Jakub Miętki
 */

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import javafx.stage.*;

public abstract class PopUp {

    private String saveInfo = null;
    private String popUpTitle = null;
    private String popUpButton = null;

    public String getFileName() {
        return saveInfo;
    }

    public String setFileName() {
        return saveInfo;
    }

    public void setPopUpButton(String popUpButton) {
        this.popUpButton = popUpButton;
    }

    public void setPopUpTitle(String popUpTitle) {
        this.popUpTitle = popUpTitle;
    }

    /*
     * Displays small window with text area to parse file name
     */
    public String display() throws IOException {
        Stage popupwindow = new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle(popUpTitle);

        Label label1 = new Label("Name of file [.txt]:");

        TextField textField = new TextField("example");
        textField.setText("example");
        textField.setCenterShape(true);
        textField.setPrefColumnCount(20);

        Button button1 = new Button(popUpButton);

        button1.setOnAction(e -> {
            saveInfo = textField.getText();
            if (isValidFileName(saveInfo)) {
                popupwindow.close();
            } else {
                label1.setText("Bad file name!");
                textField.setText("again");

            }

        });

        VBox layout = new VBox(20);

        layout.getChildren().addAll(label1, textField, button1);
        layout.setAlignment(Pos.TOP_CENTER);

        Scene scene1 = new Scene(layout, 300, 250);

        popupwindow.setScene(scene1);
        popupwindow.showAndWait();

        return saveInfo;
    }

    public boolean isValidFileName(String s) {

        String regex = "^[A-Za-z]\\w{5,29}$";

        Pattern p = Pattern.compile(regex);

        if (s == null) {
            return false;
        }
        Matcher m = p.matcher(s);

        return m.matches();
    }

}