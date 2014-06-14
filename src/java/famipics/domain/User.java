/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package famipics.domain;

import famipics.dao.DaoFactory;
import famipics.dao.RepositoryConnectionException;
import famipics.dao.UniqueConstraintException;
import famipics.dao.InvalidLoginException;
import famipics.dao.RecordNotFoundException;
import famipics.dao.UserDao;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author guillermo
 */
public class User {

    private int uid;
    private String email;
    private String displayName;
    private String password;
    private String lastLogin;
    private boolean admin;
    private List<Pic> pics;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public List<Pic> getPics() {
        return pics;
    }

    public void setPics(List<Pic> pics) {
        this.pics = pics;
    }

    /**
     * Get all the users from the database.
     *
     * @return A list of existent users.
     */
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            User u = new User();
            u.setEmail(String.format("email%d@example.com", i));
            u.setDisplayName(String.format("User %d", i));
            users.add(u);
        }
        return users;
    }

    public static User authenticate(String email, String password) throws InvalidLoginException, RepositoryConnectionException {
        try {
            return DaoFactory.getUserDao().authenticate(email, password);
        } catch (InvalidLoginException | RepositoryConnectionException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    public static User retrieve(int uid) throws RecordNotFoundException, RepositoryConnectionException {
        try {
            return DaoFactory.getUserDao().retrieve(uid);
        } catch (RecordNotFoundException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            throw new RecordNotFoundException();
        } catch (RepositoryConnectionException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    public boolean authorize() {
        return true;
    }

    public void persist() throws UniqueConstraintException {
        UserDao dao = null;
        try {
            dao = DaoFactory.getUserDao();
            DaoFactory.getUserDao().create(this);
        } catch (Exception ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
