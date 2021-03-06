/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package famipics.servlet;

import famipics.dao.InvalidLoginException;
import famipics.dao.RecordNotFoundException;
import famipics.dao.RepositoryConnectionException;
import famipics.domain.User;
import famipics.util.HashUtilities;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author guillermo
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(true);

        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            User user = User.authenticate(email, HashUtilities.sha1(password));

            session.setAttribute("currentUser", user);

            String rememberToken = session.getId();
            Cookie cookie = new Cookie("famipics_remember_token", rememberToken);
            cookie.setMaxAge(60 * 60 * 24 * 365); // 1 year expiration!
            response.addCookie(cookie);
            user.setRememberToken(rememberToken, true);
            user.setLastLogin(Calendar.getInstance().getTime().toString(), true);

            // Send the user to the main page.
            response.sendRedirect("Pics.jsp");
        } catch (InvalidLoginException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            session.setAttribute("message", "Login credentials not valid! Try again.");
            session.setAttribute("messageClass", "warning");
            response.sendRedirect("Login.jsp");
        } catch (RepositoryConnectionException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            // Let the user know about the error!
        } catch (RecordNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            session.setAttribute("message", "A problem occurred while trying to encrypt your password.");
            session.setAttribute("messageClass", "danger");
            response.sendRedirect("Login.jsp");
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
