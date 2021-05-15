package boardgame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;

/**
 * Class for representing the entry point of the Application.
 */
public class StoneBoardGameApplication extends Application {

    /**
     * Starts the Application.
     *
     * @param primaryStage the primary stage of the JavaFX Application.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/launch.fxml"));
        primaryStage.setTitle("Stone Board Game");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        Logger.info("Starting application...");
        primaryStage.show();
    }
}
