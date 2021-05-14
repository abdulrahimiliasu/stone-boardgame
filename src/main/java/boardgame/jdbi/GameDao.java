package boardgame.jdbi;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;


@RegisterBeanMapper(Game.class)
public interface GameDao {

    @SqlUpdate("""
        CREATE TABLE game (
                              id SERIAL PRIMARY KEY,
                              duration INTEGER ,
                              date DATE ,
                              player_name VARCHAR NOT NULL ,
                              player_score FLOAT ,
                              steps INTEGER NOT NULL,
                              outcome VARCHAR NOT NULL
        )
        """
    )
    void createTable();

    @SqlUpdate("INSERT INTO game(duration, date, player_name, player_score, steps, outcome) VALUES (:duration, :date, :playerName, :playerScore, :steps, :outcome)")
    int insert(@BindBean Game game);

    @SqlQuery("SELECT * FROM game WHERE id = :id")
    Optional<Game> findById(@Bind("id") int id);

    @SqlUpdate("DELETE FROM game WHERE id = :id")
    void delete(@BindBean Game game);

    @SqlQuery("SELECT * FROM game ORDER BY player_score DESC limit 10")
    List<Game> getTopTenGames();

}
