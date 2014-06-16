/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package famipics.servlet;

import famipics.dao.RecordNotFoundException;
import famipics.dao.RepositoryConnectionException;
import famipics.domain.User;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author guillermo
 */
@WebServlet(name = "Landing", urlPatterns = {"/Landing"})
public class Landing extends HttpServlet {

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
        //HttpSession session = request.getSession(true);

        // If there is a cookie for this user,
        // assume previous authentication.
        String rememberToken = "";
        try {
            for (Cookie cookie : request.getCookies()) {
                if ("famipics_remember_token".equals(cookie.getName())) {
                    rememberToken = cookie.getValue();
                }
            }
        }catch(Exception ex) {
            ex.toString();
        }

        // If no cookie was found, authenticate the user.
        if (rememberToken.isEmpty()) {
            response.sendRedirect("Login.jsp?page=home");
        } else {
            try {
                User user = User.findByRememberToken(rememberToken);
                request.getSession(true).setAttribute("currentUser", user);

                // @todo Enhance this procedure!
                // Cookie found; go to the user's home!
                response.sendRedirect("Pics.jsp?page=home");
            } catch (RecordNotFoundException | RepositoryConnectionException ex) {
                Logger.getLogger(Landing.class.getName()).log(Level.SEVERE, null, ex);
                response.sendRedirect("Logout");
            }
        }


        /*try (PrintWriter out = response.getWriter()) {
         if (session.getAttribute("currentUser") == null) {
         //out.println("<h1>Is new!</h2>");
         response.sendRedirect("Login.jsp");
         } else {
         //out.println("<h1>Is old!</h2>");
         response.sendRedirect("Pics.jsp");
         }
         }*/
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
