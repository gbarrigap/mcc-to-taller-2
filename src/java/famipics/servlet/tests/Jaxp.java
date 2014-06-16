/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package famipics.servlet.tests;

import famipics.dao.RepositoryConnectionException;
import famipics.dao.UniqueConstraintException;
import famipics.dao.jaxp.UserDaoJaxp;
import famipics.domain.User;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;
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
@WebServlet(name = "Jaxp", urlPatterns = {"/Jaxp"})
public class Jaxp extends HttpServlet {

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
        try {
            response.setContentType("text/html;charset=UTF-8");
            
            // Get this servlet context real path, to initialize the DAO
            // object with the proper xml file.
            //String repositoryPath = request.getServletContext().getRealPath("") + "/../../database/famipics-repo.xml";
            
            //List<User> users = new UserDaoJaxp(repositoryPath).retrieveAll();
            List<User> users = new UserDaoJaxp().retrieveAll();
            
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet Jaxp</title>");
                out.println("</head>");
                out.println("<body>");
                out.println(Calendar.getInstance().getTime());
                out.println("<h1>Servlet Jaxp at " + request.getContextPath() + "</h1>");
                out.println("<ul>");
                for (User user : users) {
                    out.printf("<li>%s: %s</li>", user.getEmail(), user.getDisplayName());
                }
                out.println("</ul>");
                
                out.println("<h1>New user!</h2>");
                
                User u = new User();
                u.setEmail("garganta2@pepo.cl");
                u.setDisplayName("Garganta de Lata");
                new UserDaoJaxp().create(u);
                
                users = new UserDaoJaxp().retrieveAll();
                out.println("<ul>");
                for (User user : users) {
                    out.printf("<li>%s: %s</li>", user.getEmail(), user.getDisplayName());
                }
                out.println("</ul>");
                
                out.println("</body>");
                out.println("</html>");
            } catch (UniqueConstraintException ex) {
                Logger.getLogger(Jaxp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }   catch (RepositoryConnectionException ex) {
            Logger.getLogger(Jaxp.class.getName()).log(Level.SEVERE, null, ex);
            try (PrintWriter out = response.getWriter()) {
                File f = new File("");
                String filePath = f.getAbsolutePath();
                String servletPath = request.getServletPath();
                String servletContextPath = request.getServletContext().getContextPath();
                String servletContextRealPath = request.getServletContext().getRealPath("");
                String contextPath = request.getServletContext().getRealPath("") + "/../../database/famipics-repo.xml";
                out.println("<h1>ERROR: </h1>" + filePath);
                out.println(String.format("<p>filePath: %s</p>", filePath));
                out.println(String.format("<p>servletPath: %s</p>", servletPath));
                out.println(String.format("<p>servletContextPath: %s</p>", servletContextPath));
                out.println(String.format("<p>servletContextRealPath: %s</p>", servletContextRealPath));
                out.println(String.format("<p>contextPath: %s</p>", contextPath));
            }
        }
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
