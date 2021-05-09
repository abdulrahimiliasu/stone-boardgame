package boardgame.model;

import java.util.ArrayList;
import java.util.List;

public class GameModel {

    public GameModel(){}

    public int getSquareMovements(Position squarePosition){
            int[][] movements = {
                    {4,2,2,4,4,3,4,3},
                    {3,5,3,4,2,3,5,2},
                    {4,3,2,5,2,2,5,2},
                    {7,1,4,4,4,2,2,3},
                    {3,2,2,4,2,5,2,5},
                    {2,3,2,4,4,2,5,1},
                    {6,2,2,3,2,5,6,3},
                    {1,2,5,4,4,2,1,0}
            };
            return movements[squarePosition.row()][squarePosition.col()];
    }

    public boolean isDiagonalSquare(int i, int j){
        List<Position> diagonalSquares = new ArrayList<>();
        diagonalSquares.add(new Position(0,2));
        diagonalSquares.add(new Position(0,5));
        diagonalSquares.add(new Position(0,7));
        diagonalSquares.add(new Position(1,7));
        diagonalSquares.add(new Position(2,3));
        diagonalSquares.add(new Position(4,0));
        diagonalSquares.add(new Position(5,1));
        diagonalSquares.add(new Position(5,7));
        diagonalSquares.add(new Position(6,3));
        diagonalSquares.add(new Position(7,1));
        diagonalSquares.add(new Position(7,6));

        Position position = new Position(i,j);
        return diagonalSquares.contains(position);
    }

    public List<Position> getSelectablePositions(Position position, int movement){
        List<Position> selectablePositions = new ArrayList<>();

        int col;
        if (position.col() + movement <= 7){ col = position.col() + movement; }
        else{ col = position.col() - movement; }
        if (col >= 0)
            selectablePositions.add(new Position(position.row(), col));

        int row;
        if (position.row() + movement <= 7){ row = position.row() + movement; }
        else{ row = position.row() - movement; }
        if (row >= 0)
            selectablePositions.add(new Position(row, position.col()));

        return selectablePositions;
    }

    public static void main(String[] args) {
        GameModel model = new GameModel();
        Position pos = new Position(3,3);
        System.out.println(model.getSelectablePositions(pos,3));
    }

}
