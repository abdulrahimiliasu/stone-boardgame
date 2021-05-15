package boardgame.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for representing the behaviour of the game.
 */
public class GameModel {

    /**
     * Public Constructor of the class.
     */
    public GameModel(){}

    /**
     * Keeps track of the game state.
     */
    private boolean gameIsSolved;

    /**
     * Returns the possible movement of each square on the board.
     *
     * @param squarePosition {@link Position} instance of the square.
     * @return number of movements available at the {@link Position} passed in.
     */
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

    /**
     * Checks if square has a diagonal movement only.
     *
     * @param i row value of the square.
     * @param j column value of the square.
     * @return {@code true} if the row,column value is a diagonal movement, {@code false} otherwise.
     */
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

    /**
     * Returns the selectable positions (Possible movements) of a square.
     *
     * @param position {@link Position} instance of the square.
     * @param movement number of possible movement af the square.
     * @return {@link List} of possible selectable positions.
     */
    public List<Position> getSelectablePositions(Position position, int movement){
        if (isDiagonalSquare(position.row(), position.col()))
            return getDiagonalPositions(position,movement);
        return getVerticalHorizontalPositions(position, movement);
    }

    /**
     * Returns the possible diagonal movements of a square.
     *
     * @param position {@link Position} instance of the square.
     * @param movement number of possible movement of the square.
     * @return {@link List} of possible selectable positions.
     */
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

    /**
     * Returns the possible vertical and horizontal movements of a square.
     *
     * @param position {@link Position} instance of the square.
     * @param movement number of possible movement of the square.
     * @return {@link List} of possible selectable positions.
     */
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

    /**
     * Checks whether the player has finished the game.
     *
     * @param position {@link Position} instance to check upon.
     * @return {@code true} if the player has reached the goal position, {@code false} otherwise.
     */
    public boolean isPlayerFinished(Position position){
        return position.row() == 7 && position.col() == 7;
    }

    /**
     * Checks whether the player has solved the game.
     *
     * @return {@code true} if the player has solved the game, {@code false} otherwise.
     */
    public boolean isGameSolved(){
        return gameIsSolved;
    }

    /**
     * Sets the game state value.
     *
     * @param IsGameSolved {@code true} if game is solved, {@code false} otherwise.
     */
    public void setGameIsSolved(boolean IsGameSolved) {
        this.gameIsSolved = IsGameSolved;
    }
}
