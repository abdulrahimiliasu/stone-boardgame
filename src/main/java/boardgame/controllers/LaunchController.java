package boardgame.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

import org.tinylog.Logger;

/**
 * Class for controlling launch of the application.
 */
public class LaunchController {

    @FXML
    private TextField playerNameTextfield;

    @FXML
    private Label errorLabel;

    /**
     * Starts the game upon an action event.
     *
     * @param actionEvent event that triggers the actions.
     */
    public void startGame(ActionEvent actionEvent) throws IOException {
        if (playerNameTextfield.getText().isEmpty()) {
            errorLabel.setText("*player name cannot be empty!");
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/game.fxml"));
            Parent root = fxmlLoader.load();
            fxmlLoader.<GameController>getController().setPlayerName(playerNameTextfield.getText());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            Logger.info("Player name is set to {}", playerNameTextfield.getText());
        }

    }

}
