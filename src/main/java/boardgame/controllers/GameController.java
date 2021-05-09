package boardgame.controllers;

import boardgame.model.GameModel;
import boardgame.model.Position;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    private enum SelectionPhase {
        SELECT_FROM,
        SELECT_TO;

        public SelectionPhase alter() {
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

    @FXML
    private GridPane board;

    @FXML
    void initialize(){
        createBoard();
        selectablePositions.add(new Position(0,0));
    }

    private void createBoard(){
        for (int i = 0; i < board.getRowCount(); i++) {
            for (int j = 0; j < board.getColumnCount(); j++) {
                StackPane square;
                if (model.isDiagonalSquare(j,i)){ square = createSquare("square-diagonal"); }
                else if(i == 7 && j == 7){ square = createSquare("square-finish"); }
                else{ square = createSquare("square"); }
                board.add(square,i,j);
            }
        }
        addNewStone(new Position(0,0));
    }

    private StackPane createSquare(String cssClass) {
        var square = new StackPane();
        square.getStyleClass().add(cssClass);
        square.setOnMouseClicked(this::handleMouseClick);
        return square;
    }

    private void addNewStone(Position position){
        removeStone();
        var newStone = createStone();
        GridPane.setHalignment(newStone, HPos.CENTER);
        GridPane.setValignment(newStone, VPos.CENTER);
        board.add(newStone,position.col(),position.row());
    }

    private Circle createStone(){
        Circle stone = new Circle(10);
        stone.setFill(Color.rgb(0,0,0));
        return stone;
    }

    private void removeStone(){
        board.getChildren().removeIf(child -> child instanceof Circle);
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        var square = (StackPane) event.getSource();
        var row = GridPane.getRowIndex(square);
        var col = GridPane.getColumnIndex(square);
        var position = new Position(row, col);
        handleClickOnSquare(position);
    }

    private void handleClickOnSquare(Position position) {
        System.out.println(selectionPhase);
        switch (selectionPhase) {
            case SELECT_FROM -> {
                if (selectablePositions.contains(position)) {
                    selectPosition(position);
                    alterSelectionPhase();
                }
            }
            case SELECT_TO -> {
                if (selectablePositions.contains(position)) {
                    deselectSelectedPosition();
                    hideSelectablePositions();
                    selectionPhase = selectionPhase.alter();
                    addNewStone(position);
                }
            }
        }
    }

    private void alterSelectionPhase() {
        selectionPhase = selectionPhase.alter();
        hideSelectablePositions();
        setSelectablePositions();
        showSelectablePositions();
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
        selectablePositions.addAll(model.getSelectablePositions(selected,model.getSquareMovements(selected)));
    }

    private void showSelectablePositions() {
        for (var selectablePosition : selectablePositions) {
            var square = getSquare(selectablePosition);
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
        throw new AssertionError();
    }

}
