package boardgame.controllers;

import boardgame.jdbi.Game;
import boardgame.jdbi.Persistence;
import boardgame.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.tinylog.Logger;

/**
 * Class for controlling the game.
 */
public class GameController {

    String playerName;
    private int playerSteps = 0;
    LocalTime startTime;
    LocalTime finishedTime;
    LocalDate date = LocalDate.now();
    DecimalFormat formatter = new DecimalFormat("0.00");

    private enum SelectionPhase {
        SELECT_FROM,
        SELECT_TO;

        private SelectionPhase alter() {
            return switch (this) {
                case SELECT_FROM -> SELECT_TO;
                case SELECT_TO -> SELECT_FROM;
            };
        }
    }

    private SelectionPhase selectionPhase = SelectionPhase.SELECT_FROM;
    private Position selected;
    List<Position> selectablePositions = new ArrayList<>();
    GameModel model = new GameModel();
    Position startingPosition = new Position(0,0);

    private final Player redPlayer = new Player(this.model.getRedPlayerStartingPositions(), PlayerColor.RED);
    private final Player bluePlayer = new Player(this.model.getBluePlayerStartingPositions(), PlayerColor.BLUE);

    @FXML
    private GridPane board;

    @FXML
    private Button resetButton;

    @FXML
    private Button giveUpButton;

    @FXML
    private Label playerNameLabel;

    @FXML
    private Label playerStepsLabel;

    @FXML
    private Label gameIsSolvedLabel;

    @FXML
    void initialize(){
        createBoard();
        selectablePositions.addAll(model.getStonesStartingPositions());
        startTime = LocalTime.now();
        Logger.debug("GAME STARTED AT : {}", startTime);
    }

    public void setPlayerName(String name){
        this.playerName = name;
        playerNameLabel.setText("Current Player: " +this.playerName);
    }

    private void createBoard(){
        for (int i = 0; i < board.getRowCount(); i++) {
            for (int j = 0; j < board.getColumnCount(); j++) {
                StackPane square;
                if (redPlayer.positions().contains(new Position(j,i))){
                    square = createBoardSquare("square-red-position");
                }
                else if(bluePlayer.positions().contains(new Position(j,i))){
                    square = createBoardSquare("square-blue-position");
                }else{
                    int squareAvailability = model.getSquareAvailability(new Position(j,i));
                    boolean isSquareAvailable = squareAvailability != 0;
                    square = isSquareAvailable ? createBoardSquare("square"): createBoardSquare("square-not-allowed");
                }
                board.add(square,i,j);
            }
        }
        redPlayer.positions().forEach(position -> addStoneAt(position,redPlayer.color()));
        bluePlayer.positions().forEach(position -> addStoneAt(position,PlayerColor.BLUE));
    }

    private StackPane createBoardSquare(String cssClass) {
        var square = new StackPane();
        square.getStyleClass().add(cssClass);
        square.setOnMouseClicked(this::handleMouseClick);
        return square;
    }

    private void addStoneAt(Position position, PlayerColor colorCode){
        var newStone = createStone(colorCode);
        GridPane.setHalignment(newStone, HPos.CENTER);
        GridPane.setValignment(newStone, VPos.CENTER);
        board.add(newStone,position.col(),position.row());
    }

    private void removeStone(){
        board.getChildren().removeIf(child -> child instanceof Circle);
    }

    private Circle createStone(PlayerColor colorCode){
        Circle stone = new Circle(10);
        Color color = colorCode == PlayerColor.RED ? PlayerColor.RED.getColorCode() : PlayerColor.BLUE.getColorCode();
        stone.setFill(color);
        return stone;
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        var square = (StackPane) event.getSource();
        var row = GridPane.getRowIndex(square);
        var col = GridPane.getColumnIndex(square);
        var position = new Position(row, col);
        Logger.debug("Clicked on square ({}, {})", position.row(), position.col());
        handleClickOnSquare(position);
    }

    private void handleClickOnSquare(Position position) {
        switch (selectionPhase) {
            case SELECT_FROM -> {
                if (isStoneNotMovable(position)) return; // Stone has no movable positions

                if (selectablePositions.contains(position)) {
                    selectPosition(position);
                    alterSelectionPhase();
                    resetButton.setDisable(true);
                }
            }
            case SELECT_TO -> {
                if (selectablePositions.contains(position)) {
                    if (model.isPlayerFinished(position)) {playerFinished();}
                    updatePlayerPositions(selected, position);
                    deselectSelectedPosition();
                    hideSelectablePositions();
                    selectionPhase = selectionPhase.alter();
                    clearAndAddSelectablePositionsAt(position);
                    if(model.isGameSolved()){Logger.debug("GAME SOLVED AT : {}", finishedTime);} else {playerSteps ++;}
                    Logger.debug("Stone moved to ({}, {}), Player Steps: {}", position.row(), position.col(), playerSteps);
                    playerStepsLabel.setText(String.valueOf(playerSteps));
                    resetButton.setDisable(false);
                }
            }
        }
    }

    boolean isStoneNotMovable(Position position){
        List<Position> movablePositions = model.getSelectablePositions(position);
        movablePositions.removeAll(redPlayer.positions());
        movablePositions.removeAll(bluePlayer.positions());
        return movablePositions.isEmpty();
    }

    void updatePlayerPositions(Position selectedPosition, Position newPosition) {
        if (redPlayer.positions().contains(selectedPosition)) {
            redPlayer.positions().remove(selectedPosition);
            redPlayer.positions().add(newPosition);
        }else {
            bluePlayer.positions().remove(selectedPosition);
            bluePlayer.positions().add(newPosition);
        }
        removeStone();
        redPlayer.positions().forEach(position -> addStoneAt(position,PlayerColor.RED));
        bluePlayer.positions().forEach(position -> addStoneAt(position,PlayerColor.BLUE));
    }

    private void alterSelectionPhase() {
        selectionPhase = selectionPhase.alter();
        hideSelectablePositions();
        setSelectablePositions();
        showSelectablePositions();
    }

    private void playerFinished(){
        playerSteps ++;
        giveUpButton.setText("Finished");
        model.setGameIsSolved(true);
        gameIsSolvedLabel.setText("Hurray! You solved the game.");
        finishedTime = LocalTime.now();
    }

    private void clearAndAddSelectablePositionsAt(Position position){
        selectablePositions.clear();
        selectablePositions.add(position);
        selectablePositions.addAll(redPlayer.positions());
        selectablePositions.addAll(bluePlayer.positions());
    }

    private void selectPosition(Position position) {
        selected = position;
        highlightSelectedPosition();
    }

    private void highlightSelectedPosition() {
        var square = getSquare(selected);
        square.getStyleClass().add("selected");
    }

    private void deselectSelectedPosition() {
        unHighlightSelectedPosition();
        selected = null;
    }

    private void unHighlightSelectedPosition() {
        var square = getSquare(selected);
        square.getStyleClass().remove("selected");
    }

    private void setSelectablePositions() {
        selectablePositions.clear();
        selectablePositions.addAll(model.getSelectablePositions(selected));
        selectablePositions.removeAll(redPlayer.positions());
        selectablePositions.removeAll(bluePlayer.positions());
    }

    private void showSelectablePositions() {
        for (var selectablePosition : selectablePositions) {
            var square = getSquare(selectablePosition);
            square.getChildren().removeAll();
            square.getStyleClass().add("selectable");
        }
    }

    private void hideSelectablePositions() {
        for (var selectablePosition : selectablePositions) {
            var square = getSquare(selectablePosition);
            square.getStyleClass().remove("selectable");
        }
    }

    private StackPane getSquare(Position position) {
        for (var child : board.getChildren()) {
            if (GridPane.getRowIndex(child) == position.row() && GridPane.getColumnIndex(child) == position.col()) {
                return (StackPane) child;
            }
        }
        Logger.error("Cannot get square on board (Square out of range)");
        throw new AssertionError();
    }

    /**
     * Resets the game back to starting position.
     */
    public void resetGame(){
        removeStone();
        clearAndAddSelectablePositionsAt(startingPosition);
        model.getRedPlayerStartingPositions().forEach(position -> addStoneAt(position,redPlayer.color()));
        model.getBluePlayerStartingPositions().forEach(position -> addStoneAt(position,PlayerColor.BLUE));
        resetTextLabels();
        model.setGameIsSolved(false);
        playerSteps = 0;
        Logger.info("GAME RESET");
    }

    private void resetTextLabels(){
        giveUpButton.setText("Give Up");
        gameIsSolvedLabel.setText("");
        playerStepsLabel.setText("0");
    }

    /**
     * Shows the HighScore table upon an {@link ActionEvent}.
     *
     * @param actionEvent event that triggers the action.
     */
    public void showHighScores(ActionEvent actionEvent) throws IOException {
        if (model.isGameSolved()){ Logger.info("Saving Player Data"); } else {
            finishedTime = LocalTime.now();
            Logger.debug("GIVE UP AT : {}", finishedTime);
        }
        float score = (float) playerSteps / (float) startTime.until(finishedTime, ChronoUnit.SECONDS) * 100;
        score = Float.parseFloat(formatter.format(score));
        float no_score = Float.parseFloat(formatter.format(0));
        Persistence.persistGame(
                Game.builder()
                        .duration((int) startTime.until(finishedTime, ChronoUnit.SECONDS))
                        .playerName(playerName)
                        .date(date)
                        .playerScore(model.isGameSolved() ? score:no_score)
                        .steps(playerSteps)
                        .outcome(model.isGameSolved() ? Game.Outcomes.SOLVED : Game.Outcomes.GIVEN_UP)
                        .build());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/highscores.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

}
