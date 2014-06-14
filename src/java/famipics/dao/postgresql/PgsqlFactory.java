/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package famipics.dao.postgresql;

import famipics.dao.DaoFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author guillermo
 */
final class PgsqlFactory {

    static Connection getConnection() throws SQLException, ClassNotFoundException {
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
}
