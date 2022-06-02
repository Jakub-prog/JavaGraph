package fileManager;

import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import javafx.stage.*;

public class openPopUp extends PopUp {
    private String saveInfo = null;

    @Override
    public String display() {
        Stage popupwindow = new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Open graph");

        Label label1 = new Label("Name of file [.txt]:");

        TextField textField = new TextField("example");
        textField.setText("example");
        textField.setCenterShape(true);
        textField.setPrefColumnCount(20);

        Button button1 = new Button("Open");

        button1.setOnAction(e -> {
            saveInfo = textField.getText();
            popupwindow.close();
        });

        VBox layout = new VBox(20);

        layout.getChildren().addAll(label1, textField, button1);
        layout.setAlignment(Pos.TOP_CENTER);

        Scene scene1 = new Scene(layout, 300, 250);

        popupwindow.setScene(scene1);
        popupwindow.showAndWait();

        return saveInfo;
    }

}
