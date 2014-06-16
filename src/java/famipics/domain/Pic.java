/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package famipics.domain;

import famipics.dao.DaoFactory;
import famipics.dao.RecordNotFoundException;
import famipics.dao.RepositoryConnectionException;
import famipics.dao.UniqueConstraintException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author guillermo
 */
public class Pic {

    private int pid;
    private User user;
    private int uid;
    private String filename;
    private String comment;
    private Date uploadedOn;
    private Date takenOn;
    private Date modifiedOn;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTeaser() {
        try {
            return this.comment.substring(0, 30);
        } catch (StringIndexOutOfBoundsException ex) {
            if (null == this.comment || this.comment.isEmpty()) {
                return "Not commented yet";
            } else {
                return this.comment;
            }
        }
    }

    public Date getUploadedOn() {
        return uploadedOn;
    }

    public void setUploadedOn(Date uploadedOn) {
        this.uploadedOn = uploadedOn;
    }

    public Date getTakenOn() {
        return takenOn;
    }

    public void setTakenOn(Date takenOn) {
        this.takenOn = takenOn;
    }

    public Date getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public void persist() throws RepositoryConnectionException, UniqueConstraintException {
        DaoFactory.getPicDao().create(this);
    }

    public static String getSecureFilenamePrefix() {
        return Long.toString(new Date().getTime());
    }

    public List<Pic> getAll() {
        try {
            return DaoFactory.getPicDao().retrieveAll();
        } catch (RepositoryConnectionException ex) {
            Logger.getLogger(Pic.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<>();
        }
    }

    public static Pic retrieve(int pid) throws RecordNotFoundException, RepositoryConnectionException {
        try {
            return DaoFactory.getPicDao().retrieve(pid);
        } catch (RecordNotFoundException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            throw new RecordNotFoundException();
        } catch (RepositoryConnectionException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
}
