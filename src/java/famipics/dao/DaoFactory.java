package famipics.dao;

import famipics.dao.postgresql.PicDaoPostgresql;
import famipics.dao.postgresql.UserDaoPostgresql;
import famipics.dao.sqlite.UserDaoSqlite;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Fábrica para generar objetos de acceso a datos.
 */
public class DaoFactory {

    private enum Dbs {

        SQLITE,
        POSTGRESQL
    };

    private static final Dbs db = Dbs.POSTGRESQL;

    /*private static Connection getConnectionDao() throws Exception {
     switch (db) {
     case SQLITE:
     return getSqliteConnectionDao();
     case POSTGRESQL:
     return getPostgresqlConnectionDao();
     default:
     throw new Exception("DaoFactory.java@getConnectionDao");
     }
     }*/

    /*private static Connection getSqliteConnectionDao() {
     return null;
     }*/
    private static Connection getConnection() throws SQLException, ClassNotFoundException {
        try {
            Class.forName("org.postgresql.Driver");

            String url = "jdbc:postgresql://localhost:5433/famipics";
            Properties props = new Properties();
            props.setProperty("user", "famipics");
            props.setProperty("password", "famipics");
            props.setProperty("ssl", "true");

            return DriverManager.getConnection(url, props);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DaoFactory.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    public static UserDao getUserDao() throws RepositoryConnectionException {
        //try (Connection connection = DaoFactory.getConnection()) {
            //if (db == Dbs.POSTGRESQL) {
            //return new UserDaoPostgresql(DaoFactory.getConnection());
            //} else {
            //    return new UserDaoSqlite(DaoFactory.getConnection());
            //}

            /*try {
             switch (db) {
             case POSTGRESQL:
             return new UserDaoPostgresql(DaoFactory.getConnectionDao());
             default:
             return new UserDaoSqlite(DaoFactory.getConnectionDao());
             }
             } catch (Exception ex) {
             Logger.getLogger(DaoFactory.class.getName()).log(Level.SEVERE, null, ex);
             }*/
            switch (db) {
                case POSTGRESQL:
                    //return new UserDaoPostgresql(connection);
                    return new UserDaoPostgresql();
                case SQLITE:
                    //return new UserDaoSqlite(connection);
                default:
                    throw new RepositoryConnectionException();
            }
//        } catch (SQLException | ClassNotFoundException ex) {
//            Logger.getLogger(DaoFactory.class.getName()).log(Level.SEVERE, null, ex);
//            throw new RepositoryConnectionException();
//        }
    }

    public static PicDao getPicDao() throws RepositoryConnectionException {
        switch (db) {
            case POSTGRESQL:
                return new PicDaoPostgresql();
            default:
                throw new RepositoryConnectionException();
        }
    }
}
