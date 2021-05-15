package boardgame.jdbi;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

/**
 * DAO class for the {@link Game} entity.
 */
@RegisterBeanMapper(Game.class)
public interface GameDao {

    /**
     * Inserts {@link Game} instance into the database.
     *
     * @param game {@link Game} instance to be inserted.
     * @return {@code 1} if insertion was successful, {@code 0} if there was an error.
     */
    @SqlUpdate("INSERT INTO game(duration, date, player_name, player_score, steps, outcome) VALUES (:duration, :date, :playerName, :playerScore, :steps, :outcome)")
    int insert(@BindBean Game game);

    /**
     * Returns the list of 10 best results with respect to the score
     * for solving the game.
     *
     * @return the list of 10 best results with respect to the score
     * for solving the game.
     */
    @SqlQuery("SELECT * FROM game ORDER BY player_score DESC limit 10")
    List<Game> getTopTenGames();

}
