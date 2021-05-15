package boardgame.jdbi;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.Slf4JSqlLogger;
import org.jdbi.v3.postgres.PostgresPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.tinylog.Logger;

/**
 * Class for Persisting Game result into a database.
 */
public class Persistence {

    /**
     * {@see org.jdbi.v3.core.Jdbi} instance for establishing connection to a remote SQL database.
     */
    public static Jdbi jdbi = Jdbi.create(
            "jdbc:postgresql://queenie.db.elephantsql.com:5432/",
            "teytjfip",
            "cohPceXish-l2QtXpEXz2Ek7_Z0vcZjg"
    )
            .installPlugin(new SqlObjectPlugin())
            .installPlugin(new PostgresPlugin())
            .setSqlLogger(new Slf4JSqlLogger());

    /**
     * Persist {@link Game} instance into the database using a {@see org.jdbi.v3.core.Jdbi} Object.
     *
     * @param game {@link Game} instance to be persisted into the database.
     */
    public static void persistGame(Game game){
        Logger.debug("Database Connection Established");
        jdbi.withExtension(GameDao.class, dao -> dao.insert(game));
        Logger.info("Game was saved Successfully");
    }

}
