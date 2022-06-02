package javagraph;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        // root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
        Scene scene = new Scene(loadFXML("MainScene"), 1015, 786);

        String css = this.getClass().getResource("aplication.css").toExternalForm();
        scene.getStylesheets().add(css);

        primaryStage.setTitle("GraphApp");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }
}