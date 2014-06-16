/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package famipics.servlet.tests;

import famipics.dao.DaoFactory;
import famipics.dao.UniqueConstraintException;
import famipics.dao.UserDao;
import famipics.dao.postgresql.UserDaoPostgresql;
import famipics.domain.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author guillermo
 */
@WebServlet(name = "TestDb", urlPatterns = {"/TestDb"})
public class TestDb extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        /*UserDao dao = null;
         try (PrintWriter out = response.getWriter()) {
         dao = DaoFactory.getUserDao();

         List<User> users = DaoFactory.getUserDao().retrieveAll();
         out.println(String.format("<h1>Users List (%d)</h1>", users.size()));
         out.println("<ol>");
         for (User user : users) {
         out.println(String.format("<li>[%s] %s: %s</li>", user.getUid(), user.getEmail(), user.getDisplayName()));
         }
         out.println("</ol>");

         User u = new User();
         u.setEmail("email@example.com");
         u.setPassword("secreto");
         u.setDisplayName("Example");
         u.persist();

         out.println(u.getEmail());
         out.println(u.getUid());

         } catch (Exception ex) {
         Logger.getLogger(TestDb.class.getName()).log(Level.SEVERE, null, ex);
         try (PrintWriter out = response.getWriter()) {
         out.println(ex.toString());
         }
         }*/
        try (PrintWriter out = response.getWriter()) {
            List<User> users = getDbUsers();
            out.println("<h1>Existing Users<h1>");
            out.println("<ol>");
            for (User user : users) {
                out.println(String.format("<li>[%d] %s: %s", user.getUid(), user.getDisplayName(), user.getEmail()));
            }
            out.println("</ol>");
        }

        try {
            User u = new User();
            u.setEmail("example@mail.com");
            u.setPassword("secreto");
            u.setDisplayName("Exempli Gracia");
            u.persist();
        } catch (UniqueConstraintException ex) {
            Logger.getLogger(TestDb.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (PrintWriter out = response.getWriter()) {
            List<User> users = getDbUsers();
            out.println("<h1>Existing Users<h1>");
            out.println("<ol>");
            for (User user : users) {
                out.println(String.format("<li>[%d] %s: %s", user.getUid(), user.getDisplayName(), user.getEmail()));
            }
            out.println("</ol>");
        }

        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet TestDb</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TestDb at " + request.getContextPath() + "</h1>");
            //out.println(String.format("<h2>%s</h2>", dao.toString()));
            out.println("</body>");
            out.println("</html>");
        }
    }

    private List<User> getDbUsers() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TestDb.class.getName()).log(Level.SEVERE, null, ex);
        }

        String url = "jdbc:postgresql://localhost:5433/famipics";
        Properties props = new Properties();
        props.setProperty("user", "famipics");
        props.setProperty("password", "famipics");
        //props.setProperty("port", "5433");
        props.setProperty("ssl", "true");

        List<User> users = new ArrayList<>();
        String query = "SELECT uid, email, display_name FROM users";

        try (Connection connection = DriverManager.getConnection(url, props)) {
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                User u = new User();
                u.setUid(rs.getInt("uid"));
                u.setEmail(rs.getString("email"));
                u.setDisplayName(rs.getString("display_name"));
                users.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoPostgresql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
