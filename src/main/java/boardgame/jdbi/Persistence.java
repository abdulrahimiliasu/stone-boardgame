package boardgame.jdbi;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.Slf4JSqlLogger;
import org.jdbi.v3.postgres.PostgresPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.tinylog.Logger;

public class Persistence {

    public static Jdbi jdbi = Jdbi.create(
            "jdbc:postgresql://queenie.db.elephantsql.com:5432/",
            "teytjfip",
            "cohPceXish-l2QtXpEXz2Ek7_Z0vcZjg"
    )
            .installPlugin(new SqlObjectPlugin())
            .installPlugin(new PostgresPlugin())
            .setSqlLogger(new Slf4JSqlLogger());

    public static void persistGame(Game game){
        Logger.debug("Database Connection Established");
        jdbi.withExtension(GameDao.class, dao -> dao.insert(game));
        Logger.info("Game was saved Successfully");
    }

}
