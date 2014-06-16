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
    private String uploadedOn;
    private String takenOn;
    private String modifiedOn;

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

    public String getUploadedOn() {
        return uploadedOn;
    }

    public void setUploadedOn(String uploadedOn) {
        this.uploadedOn = uploadedOn;
    }

    public String getTakenOn() {
        return takenOn;
    }

    public void setTakenOn(String takenOn) {
        this.takenOn = takenOn;
    }

    public String getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(String modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public void persist() throws RepositoryConnectionException, UniqueConstraintException, RecordNotFoundException {
        if (this.pid > 0) {
            try {
                DaoFactory.getPicDao().update(this);
            } catch (RecordNotFoundException ex) {
                Logger.getLogger(Pic.class.getName()).log(Level.SEVERE, null, ex);
                throw ex;
            }
        } else {
            DaoFactory.getPicDao().create(this);
        }
    }
    
    public void destroy() throws RepositoryConnectionException, RecordNotFoundException {
        try {
            DaoFactory.getPicDao().delete(this);
        } catch (RepositoryConnectionException | RecordNotFoundException ex) {
            Logger.getLogger(Pic.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
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
    
    public Pic find(int pid) throws RepositoryConnectionException, RecordNotFoundException {
        try {
            return DaoFactory.getPicDao().retrieve(pid);
        } catch (RepositoryConnectionException | RecordNotFoundException ex) {
            Logger.getLogger(Pic.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
    
    public Pic find(String pid) throws RepositoryConnectionException, RecordNotFoundException {
        try {
            return this.find(Integer.parseInt(pid));
        } catch (RepositoryConnectionException | RecordNotFoundException ex) {
            Logger.getLogger(Pic.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
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
