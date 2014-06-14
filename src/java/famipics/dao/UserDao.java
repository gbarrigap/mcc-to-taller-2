/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package famipics.dao;

import famipics.domain.User;

/**
 *
 * @author guillermo
 */
public interface UserDao extends Dao<User> {
    
    /**
     * Autenticate the given email/password pair.
     * 
     * Autenticate the given email/password pair against the system's
     * repository, initializing its attributes. On success, constructs and
     * returns an authenticated user; on failure, returns <code>null</code>.
     * 
     * @param email
     * @param password
     * @return An autenticated user or <code>null</code>.
     * @throws famipics.dao.InvalidLoginException
     */
    public User authenticate(String email, String password) throws InvalidLoginException;
}
