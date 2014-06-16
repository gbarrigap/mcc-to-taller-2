/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package famipics.dao.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author guillermo
 */
final class SqliteFactory {
    
    static synchronized Connection getConnection() throws SQLException, ClassNotFoundException {
        try {
            Class.forName("org.sqlite.JDBC");
            
            Connection conn = DriverManager.getConnection("jdbc:sqlite:/tmp/famipics.db");
            String foo = "";
            return conn;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SqliteFactory.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } catch (Exception ex) {
            Logger.getLogger("GUAAAAJAJALSDFJASKLFSF" + SqliteFactory.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
    
}
