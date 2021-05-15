package boardgame.jdbi;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class representing the result of a game played by a specific player.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Game {

    /**
     * Enum representing the available game outcomes.
     */
    public enum Outcomes{
        /**
         * The game was solved by the player.
         */
        SOLVED,
        /**
         * The player was not solved by the player.
         */
        GIVEN_UP
    }

    /**
     * The duration of the game in seconds.
     */
    private int duration;

    /**
     * The date the game was played.
     */
    private LocalDate date;

    /**
     * The name of the player.
     */
    private String playerName;

    /**
     * The score of the player.
     */
    private float playerScore;

    /**
     * The number of steps made by the player.
     */
    private int steps;

    /**
     * Indicates whether the player has solved the game.
     */
    private Outcomes outcome;
}
