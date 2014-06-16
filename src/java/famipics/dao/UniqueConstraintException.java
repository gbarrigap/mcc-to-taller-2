/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package famipics.dao;

/**
 *
 * @author guillermo
 */
public class UniqueConstraintException extends Exception {

    public UniqueConstraintException(String message) {
        super(message);
    }
    public UniqueConstraintException() {
        
    }
}
