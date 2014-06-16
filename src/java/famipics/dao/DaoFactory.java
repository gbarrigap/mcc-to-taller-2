package famipics.dao;

import famipics.dao.jaxp.PicDaoJaxp;
import famipics.dao.jaxp.UserDaoJaxp;
import famipics.dao.postgresql.PicDaoPostgresql;
import famipics.dao.postgresql.UserDaoPostgresql;
import famipics.dao.sqlite.UserDaoSqlite;

/**
 * Fábrica para generar objetos de acceso a datos.
 */
public class DaoFactory {

    private enum Dbs {

        SQLITE,
        POSTGRESQL,
        JAXP
    };

    private static final Dbs db = Dbs.JAXP;

    public static UserDao getUserDao() throws RepositoryConnectionException {
        switch (db) {
            case POSTGRESQL:
                //return new UserDaoPostgresql(connection);
                return new UserDaoPostgresql();
            case SQLITE:
                return new UserDaoSqlite();
            case JAXP:
                return new UserDaoJaxp();
            default:
                throw new RepositoryConnectionException();
        }
    }

    public static PicDao getPicDao() throws RepositoryConnectionException {
        switch (db) {
            case POSTGRESQL:
                return new PicDaoPostgresql();
            //case SQLITE:
            //    return new PicDaoSqlite();
            case JAXP:
                return new PicDaoJaxp();
            default:
                throw new RepositoryConnectionException();
        }
    }
}
