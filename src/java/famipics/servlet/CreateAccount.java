/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package famipics.servlet;

import famipics.dao.RecordNotFoundException;
import famipics.dao.RepositoryConnectionException;
import famipics.dao.UniqueConstraintException;
import famipics.domain.User;
import famipics.util.PasswordEncryptionException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author guillermo
 */
@WebServlet(name = "CreateAccount", urlPatterns = {"/CreateAccount"})
public class CreateAccount extends HttpServlet {

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
        HttpSession session = request.getSession();
        response.setContentType("text/html;charset=UTF-8");

        try {
            String email = request.getParameter("email");
            String displayName = request.getParameter("display-name");
            String password = request.getParameter("password");
            String confirmation = request.getParameter("password-confirmation");

            // All fields required.
            if (email.isEmpty() || displayName.isEmpty() || password.isEmpty()) {
                session.setAttribute("message", "All fields required!");
                session.setAttribute("messageClass", "warning");
                response.sendRedirect("CreateAccount.jsp");
                return;
            }
            
            // Passwords have to match.
            if (!password.equals(confirmation)) {
                session.setAttribute("message", "Passwords do not match!");
                session.setAttribute("messageClass", "warning");
                response.sendRedirect("CreateAccount.jsp");
                return;
            }
            
            User u = new User();
            u.setEmail(email);
            u.setDisplayName(displayName);
            
            // Set an encrypted representation of the password.
            u.setPassword(password, true);

            u.persist();
            
            session.setAttribute("message", "User account created successfully; you can log in now.");
            session.setAttribute("messageClass", "success");
            response.sendRedirect("Login.jsp");
        } catch (UniqueConstraintException ex) {
            session.setAttribute("message", "Email already registered!");
            session.setAttribute("messageClass", "danger");
            response.sendRedirect("CreateAccount.jsp");
            Logger.getLogger(CreateAccount.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RepositoryConnectionException ex) {
            Logger.getLogger(CreateAccount.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RecordNotFoundException ex) {
            Logger.getLogger(CreateAccount.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PasswordEncryptionException ex) {
            Logger.getLogger(CreateAccount.class.getName()).log(Level.SEVERE, null, ex);
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
