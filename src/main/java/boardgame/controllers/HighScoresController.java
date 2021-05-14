package boardgame.controllers;

import boardgame.model.Game;
import boardgame.model.GameDao;
import boardgame.model.Persistence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.tinylog.Logger;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class HighScoresController {

    @FXML
    private TableView<Game> topTenTable;

    @FXML
    private TableColumn<Game, String> player;

    @FXML
    private TableColumn<Game, Integer> steps;

    @FXML
    private TableColumn<Game, Integer> duration;

    @FXML
    private TableColumn<Game, Float> score;

    @FXML
    private TableColumn<Game, LocalDate> date;


    List<Game> topTenHighscores;

    public void showMainMenu(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/launch.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
        Logger.info("Clicked on Main Menu button");
        Logger.debug("Restarting Application");
    }

    @FXML
    void initialize(){
        Logger.debug("Accessing Database...");
        topTenHighscores = Persistence.jdbi.withExtension(GameDao.class, GameDao::getTopTenGames);
        Logger.debug("Data Retrieved Successfully");
        observableResult.addAll(topTenHighscores);
        player.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        steps.setCellValueFactory(new PropertyValueFactory<>("steps"));
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        score.setCellValueFactory(new PropertyValueFactory<>("playerScore"));

        topTenTable.setItems(observableResult);
    }

    ObservableList<Game> observableResult = FXCollections.observableArrayList();

}
