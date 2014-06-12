/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package famipics.dao.sqlite;

import famipics.dao.UserDao;
import famipics.domain.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author guillermo
 */
public class UserDaoSqlite implements UserDao {

    private final Connection connection;

    public UserDaoSqlite(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(User user) {
        try {
            Statement statement = this.connection.createStatement();

            // Insert the user.
            String insertCmd = String.format("INSERT INTO users (email, display_name, password, last_login, admin) VALUES ('%s', '%s', '%s', NULL, FALSE))", user.getEmail(), user.getDisplayName(), user.getPassword());
            statement.executeUpdate(insertCmd);

            // Get the user id.
            String getLastKeyQuery = "SELECT max(uid) AS uid FROM users";
            ResultSet rs = statement.executeQuery(getLastKeyQuery);
            while (rs.next()) {
                user.setUid(rs.getInt("uid"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoSqlite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public User retrieve(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(User t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(User t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<User> retrieveAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User authenticate(String email, String password) {
        try {
            String query = String.format("SELECT uid, display_name AS count FROM users WHERE email = '%s' AND password = '%s'", email, password);
            Statement statement = this.connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                User user = new User();
                user.setUid(rs.getInt("uid"));
                user.setEmail(email);
                user.setPassword(password);
                user.setDisplayName(rs.getString("dispay_name"));
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoSqlite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
