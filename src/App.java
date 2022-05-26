import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {

        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
            Scene scene = new Scene(root);

            String css = this.getClass().getResource("aplication.css").toExternalForm();
            scene.getStylesheets().add(css);

            Generator generator = new Generator();
            generator.generate(4, 7, "test.txt", false, 10, 20);

            primaryStage.setTitle("CipherApp");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
