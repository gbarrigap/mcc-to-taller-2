/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package famipics.dao.postgresql;

import famipics.dao.UniqueConstraintException;
import famipics.dao.InvalidLoginException;
import famipics.dao.RecordNotFoundException;
import famipics.dao.RepositoryConnectionException;
import famipics.dao.UserDao;
import famipics.domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author guillermo
 */
public class UserDaoPostgresql implements UserDao {
    
    private final String authenticateQuery = "SELECT uid, display_name FROM users WHERE email = '%s' AND password = '%s'";
    private final String createStatement = "";
    private final String updateStatement = "UPDATE users SET email = '%s', display_name = '%s', is_admin = %s, remember_token = '%s' WHERE uid = %d";
    private final String setRememberTokenStatement = "UPDATE users SET remember_token = '%s' WHERE uid = %d";
    private final String findByRememberTokenQuery = "SELECT uid, email, display_name, is_admin FROM users WHERE remember_token = '%s'";

    /*private final Connection connection;

     public UserDaoPostgresql(Connection connection) {
     this.connection = connection;
     }*/
    @Override
    public User authenticate(String email, String password) throws InvalidLoginException {
        /*try {
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
         Logger.getLogger(UserDaoPostgresql.class.getName()).log(Level.SEVERE, null, ex);
         }*/

        String query = String.format("SELECT uid, display_name, is_admin FROM users WHERE email = '%s' AND password = '%s'", email, password);
        try (
                Connection connection = PgsqlFactory.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(query);
        ) {
            while (rs.next()) {
                User user = new User();
                user.setUid(rs.getInt("uid"));
                user.setEmail(email);
                user.setPassword(password);
                user.setDisplayName(rs.getString("display_name"));
                user.setAdmin(rs.getBoolean("is_admin"));
                return user;
            }
            throw new InvalidLoginException();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserDaoPostgresql.class.getName()).log(Level.SEVERE, null, ex);
            throw new InvalidLoginException();
        }
    }

    @Override
    public void create(User t) throws UniqueConstraintException {
        String statement = String.format("INSERT INTO users (email, password, display_name) VALUES ('%s', '%s', '%s')", t.getEmail(), t.getPassword(), t.getDisplayName());
        try (
                Connection connection = PgsqlFactory.getConnection();
                PreparedStatement st = connection.prepareStatement(statement);
        ) {
            st.executeUpdate();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserDaoPostgresql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public User retrieve(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(User user) {
        String statement = String.format(this.updateStatement, user.getEmail());
        try (
                Connection connection = PgsqlFactory.getConnection();
                PreparedStatement st = connection.prepareStatement(statement);
        ) {
            st.executeUpdate();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserDaoPostgresql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(User t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<User> retrieveAll() {
        List<User> users = new ArrayList<>();
        String query = "SELECT uid, email, password, display_name FROM users";

        try (
                Connection connection = PgsqlFactory.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(query);
        ) {
            while (rs.next()) {
                User u = new User();
                u.setUid(rs.getInt("uid"));
                u.setEmail(rs.getString("email"));
                u.setDisplayName(rs.getString("display_name"));
                users.add(u);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserDaoPostgresql.class.getName()).log(Level.SEVERE, null, ex);
        }

        return users;
    }

    @Override
    public User findByRememberToken(String token) throws RecordNotFoundException {
        try (
                Connection connection = PgsqlFactory.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(String.format(this.findByRememberTokenQuery, token));
        ) {
            while (rs.next()) {
                User user = new User();
                user.setUid(rs.getInt("uid"));
                user.setEmail(rs.getString("email"));
                user.setDisplayName(rs.getString("display_name"));
                user.setAdmin(rs.getBoolean("is_admin"));
                return user;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserDaoPostgresql.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        throw new RecordNotFoundException();
    }

    @Override
    public void setRememberToken(User user) throws RecordNotFoundException {
        String statement = String.format(this.setRememberTokenStatement, user.getRememberToken(), user.getUid());
        try (
                Connection connection = PgsqlFactory.getConnection();
                PreparedStatement st = connection.prepareStatement(statement);
        ) {
            if (st.executeUpdate() != 1) {
                throw new RecordNotFoundException();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserDaoPostgresql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public User findByEmail(String email) throws RecordNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLastLogin(User user) throws RepositoryConnectionException, RecordNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
