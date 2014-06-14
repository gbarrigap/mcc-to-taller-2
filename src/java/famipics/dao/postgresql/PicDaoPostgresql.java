/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package famipics.dao.postgresql;

import famipics.dao.UniqueConstraintException;
import famipics.dao.PicDao;
import famipics.dao.RecordNotFoundException;
import famipics.dao.RepositoryConnectionException;
import famipics.domain.Pic;
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
public class PicDaoPostgresql implements PicDao {
    
    private final String createStatement = "INSERT INTO pics (uid, filename, comment) VALUES (%d, '%s', '%s')";
    private final String retrieveQuery = "";
    private final String updateStatement = "";
    private final String deleteStatement = "";
    private final String retrieveAllQuery = "SELECT pid, filename, comment FROM pics";

    @Override
    public void create(Pic pic) throws RepositoryConnectionException, UniqueConstraintException {
        try (
                Connection connection = PgsqlFactory.getConnection();
                PreparedStatement st = connection.prepareStatement(String.format(this.createStatement, pic.getUser().getUid(), pic.getFilename(), pic.getComment()));
        ) {
            //String statement = String.format(this.createStatement, pic.getUser().getUid(), pic.getFilename(), pic.getComment());
            st.executeUpdate();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(PicDaoPostgresql.class.getName()).log(Level.SEVERE, null, ex);
            throw new RepositoryConnectionException();
        }
    }

    @Override
    public Pic retrieve(int id) throws RecordNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Pic t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Pic t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Pic> retrieveAll() {
        List<Pic> pics = new ArrayList<>();
        try (
                Connection connection = PgsqlFactory.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(this.retrieveAllQuery);
        ) {
            while (rs.next()) {
                Pic pic = new Pic();
                pic.setPid(rs.getInt("pid"));
                pic.setFilename(rs.getString("filename"));
                pic.setComment(rs.getString("comment"));
                pics.add(pic);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(PicDaoPostgresql.class.getName()).log(Level.SEVERE, null, ex);
        }

        return pics;
    }

}
