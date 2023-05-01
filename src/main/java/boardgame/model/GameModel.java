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
     Add Javadoc
     */
    public int getSquareAvailability(Position squarePosition){
            int[][] availableMovements = {
                    {1,0,1,0,1},
                    {1,1,1,1,1},
                    {1,0,0,0,1},
                    {0,0,0,0,0},
                    {0,0,0,0,0}
            };
            return availableMovements[squarePosition.row()][squarePosition.col()];
    }

    public List<Position> getRedPlayerStartingPositions(){
        return new ArrayList<>(
                Arrays.asList(new Position(0, 0), new Position(1, 0), new Position(2, 0))
        );
    }

    public List<Position> getBluePlayerStartingPositions(){
        return new ArrayList<>(
                Arrays.asList(new Position(0, 4), new Position(1, 4), new Position(2, 4))
        );
    }

    public List<Position> getStonesStartingPositions(){
        List<Position> redPlayerPositions = this.getRedPlayerStartingPositions();
        List<Position> bluePlayerPositions = this.getBluePlayerStartingPositions();
        List<Position> startingPositions = new ArrayList<>(redPlayerPositions);
        startingPositions.addAll(bluePlayerPositions);
        return startingPositions;
    }

    /**
     */
    public List<Position> getSelectablePositions(Position position){
        List<Position> selectablePositions = new ArrayList<>(
                Arrays.asList(new Position(position.row() + 1, position.col()),
                        new Position(position.row() , position.col() + 1),
                        new Position(position.row() , position.col() - 1),
                        new Position(position.row() - 1, position.col()))
        );
        selectablePositions.removeIf(pos -> pos.row() < 0 || pos.col() < 0 || pos.row() > 2 || pos.col() > 4);
        selectablePositions.removeIf(pos -> {
            int squareAvailability = this.getSquareAvailability(pos);
            return squareAvailability == 0;
        });
        return selectablePositions;
    }

    /**
     */
    public boolean isPlayerFinished(Position position){
        return position.row() == 7 && position.col() == 7;
    }

    /**
     */
    public boolean isGameSolved(){
        return gameIsSolved;
    }

    /**
     */
    public void setGameIsSolved(boolean IsGameSolved) {
        this.gameIsSolved = IsGameSolved;
    }
}
