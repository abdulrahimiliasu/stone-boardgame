package boardgame.model;

import java.time.LocalDateTime;

public class Game {

    public enum Outcomes{
        SOLVED, GIVEN_UP
    }

    private LocalDateTime start;
    private LocalDateTime finished;
    private Player player;
    private int moves;
    private Outcomes gamerOutcome;
}
