import boardgame.model.GameModel;
import boardgame.model.Position;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class GameModelTest {

    GameModel model = new GameModel();

    @Test
    void testSquareMovements(){
        assertEquals(model.getSquareMovements(new Position(0,0)),4);
        assertEquals(model.getSquareMovements(new Position(0,7)),3);
        assertEquals(model.getSquareMovements(new Position(7,0)),1);
        assertEquals(model.getSquareMovements(new Position(7,7)),0);
        assertEquals(model.getSquareMovements(new Position(6,7)),3);
        assertEquals(model.getSquareMovements(new Position(5,3)),4);
        assertEquals(model.getSquareMovements(new Position(3,4)),4);
        assertEquals(model.getSquareMovements(new Position(2,5)),2);
        assertEquals(model.getSquareMovements(new Position(1,1)),5);
        assertEquals(model.getSquareMovements(new Position(0,2)),2);
        assertEquals(model.getSquareMovements(new Position(4,3)),4);
    }

    @Test
    void testIsDiagonalPosition(){
        assertTrue(model.isDiagonalSquare(0,2));
        assertTrue(model.isDiagonalSquare(1,7));
        assertTrue(model.isDiagonalSquare(7,1));
        assertTrue(model.isDiagonalSquare(5,1));
        assertTrue(model.isDiagonalSquare(7,6));
    }

    @Test
    void testSelectablePositions(){
        assertEquals(model.getSelectablePositions(new Position(0,0),4),new ArrayList<>(Arrays.asList(
                new Position(4,0),
                new Position(0,4))));

        assertEquals(model.getSelectablePositions(new Position(5,0),2), new ArrayList<>(Arrays.asList(
                new Position(7, 0),
                new Position(5, 2),
                new Position(3, 0))));

        assertEquals(model.getSelectablePositions(new Position(5,7),2),new ArrayList<>(Arrays.asList(
                new Position(3,5),
                new Position(7,5))));

        assertEquals(model.getSelectablePositions(new Position(6,6),6),new ArrayList<>(Arrays.asList(
                new Position(6,0),
                new Position(0,6))));
    }


    @Test
    void testIsPlayerFinished(){
        assertFalse(model.isPlayerFinished(new Position(6,6)));
        assertFalse(model.isPlayerFinished(new Position(4,4)));
        assertFalse(model.isPlayerFinished(new Position(3,3)));
        assertFalse(model.isPlayerFinished(new Position(4,4)));
        assertFalse(model.isPlayerFinished(new Position(5,5)));
        assertFalse(model.isPlayerFinished(new Position(1,1)));
        assertFalse(model.isPlayerFinished(new Position(2,2)));
        assertFalse(model.isPlayerFinished(new Position(0,0)));

        assertTrue(model.isPlayerFinished(new Position(7,7)));
    }

    @Test
    void testIsGameSolved(){
        assertFalse(model.isGameSolved());
        model.setGameIsSolved(true);
        assertTrue(model.isGameSolved());
    }
}
