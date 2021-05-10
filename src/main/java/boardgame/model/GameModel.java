package boardgame.model;

import java.util.ArrayList;
import java.util.Arrays;
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
        List<Position> diagonalSquares = new ArrayList<>(
                Arrays.asList(new Position(0,2),new Position(0,5),new Position(0,7),
                            new Position(1,7),new Position(2,3),new Position(4,0),
                            new Position(5,1),new Position(5,7),new Position(6,3),
                            new Position(7,1),new Position(7,6))
        );

        Position position = new Position(i,j);
        return diagonalSquares.contains(position);
    }

    public List<Position> getSelectablePositions(Position position, int movement){
        if (isDiagonalSquare(position.row(), position.col()))
            return getDiagonalPositions(position,movement);
        return getVerticalHorizontalPositions(position, movement);
    }

    private List<Position> getDiagonalPositions(Position position, int movement){
        List<Position> diagonalPositions = new ArrayList<>(
                Arrays.asList(new Position(position.row() + movement, position.col() + movement ),
                        new Position(position.row() - movement, position.col() - movement ),
                        new Position(position.row() + movement, position.col() - movement ),
                        new Position(position.row() - movement, position.col() + movement ))
        );
        diagonalPositions.removeIf(pos -> pos.row() < 0 || pos.col() < 0 || pos.row() > 7 || pos.col() > 7);
        return diagonalPositions;
    }

    private List<Position> getVerticalHorizontalPositions(Position position, int movement){
        List<Position> verticalHorizontalPositions = new ArrayList<>(
                Arrays.asList(new Position(position.row() + movement, position.col()),
                        new Position(position.row() , position.col() + movement),
                        new Position(position.row() , position.col() - movement),
                        new Position(position.row() - movement, position.col()))
        );
        verticalHorizontalPositions.removeIf(pos -> pos.row() < 0 || pos.col() < 0 || pos.row() > 7 || pos.col() > 7);
        return verticalHorizontalPositions;
    }

}
