/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package famipics.servlet;

import famipics.dao.RepositoryConnectionException;
import famipics.dao.UniqueConstraintException;
import famipics.domain.Pic;
import famipics.domain.User;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@WebServlet("/UploadPicture")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class UploadPicture extends HttpServlet {

    /**
     * Name of the directory where uploaded files will be saved, relative to the
     * web application directory.
     */
    private static final String SAVE_DIR = "../../web/files/pics";

    private static final String SUCCESS_MESSAGE = "Upload successful";
    private static final String FAIL_MESSAGE = "Upload did not work";

    /**
     * handles file upload
     *
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(UploadPicture.class.getName()).log(Level.SEVERE, null, ex);
        }
        HttpSession currentSession = request.getSession(false);
        User currentUser = (User) currentSession.getAttribute("currentUser");

        // gets absolute path of the web application
        String appPath = request.getServletContext().getRealPath("");
        // constructs path of the directory to save uploaded file
        String savePath = appPath + File.separator + SAVE_DIR;

        // creates the save directory if it does not exists
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }

        try {
            Part filePart = request.getPart("filename");
            String filename = String.format("%s-%s", Pic.getSecureFilenamePrefix(), extractFileName(filePart));
            String comment = request.getParameter("comment");

            //for (Part part : request.getParts()) {
            //    filename = String.format("%s-%s", Pic.getSecureFilenamePrefix(), extractFileName(part));
            //    //part.write(savePath + File.separator + filename);
            //    comment = extractComment(part);
            //}
            filePart.write(savePath + File.separator + filename);

            Pic pic = new Pic();
            pic.setUser(currentUser);
            pic.setFilename(filename);
            pic.setComment(comment);
            pic.persist();

            currentSession.setAttribute("uploadResultMessage", SUCCESS_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(UploadPicture.class.getName()).log(Level.SEVERE, null, ex);
            currentSession.setAttribute("uploadResultMessage", FAIL_MESSAGE);
        } catch (RepositoryConnectionException ex) {
            Logger.getLogger(UploadPicture.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UniqueConstraintException ex) {
            Logger.getLogger(UploadPicture.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                response.sendRedirect("Pics.jsp");
            } catch (IOException ex) {
                Logger.getLogger(UploadPicture.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Extracts file name from HTTP header content-disposition
     */
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }

    private String extractComment(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("comment")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }
}
