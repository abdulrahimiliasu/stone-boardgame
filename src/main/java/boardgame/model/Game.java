package boardgame.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Game {

    public enum Outcomes{
        SOLVED, GIVEN_UP
    }

    private int id;
    private int duration;
    private LocalDate date;
    private String playerName;
    private float playerScore;
    private int steps;
    private Outcomes outcome;
}
