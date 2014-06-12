package famipics.dao;

import famipics.dao.sqlite.UserDaoSqlite;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Fábrica para generar objetos de acceso a datos.
 *
 * @see biblioteca.dao.Dao
 */
public class DaoFactory {

    private static Connection getConnectionDao() {
        Connection connection = null;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:biblioteca.db");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DaoFactory.class.getName()).log(Level.SEVERE, null, ex);
        }

        return connection;
    }

    public static UserDao getUserDao() {
        return new UserDaoSqlite(DaoFactory.getConnectionDao());
    }
}
