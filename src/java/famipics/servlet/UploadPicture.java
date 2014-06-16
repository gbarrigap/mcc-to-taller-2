/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package famipics.servlet;

import famipics.dao.RecordNotFoundException;
import famipics.dao.RepositoryConnectionException;
import famipics.dao.UniqueConstraintException;
import famipics.domain.Pic;
import famipics.domain.User;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
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

        // Gets absolute path of the web application.
        String appPath = request.getServletContext().getRealPath("");
        // Constructs path of the directory to save uploaded files.
        String savePath = appPath + File.separator + SAVE_DIR;

        // Creates the save directory if it does not exist.
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }

        try {
            Part filePart = request.getPart("filename");
            String filename = extractFileName(filePart);

            // If no file was received, halt process.
            if (filename.isEmpty()) {
                response.sendRedirect("UploadPictures.jsp");
                throw new NoPictureSelectedException();
            }

            // Given a picture is provided, come what may,
            // the user will be redirected to the main page.
            response.sendRedirect("Pics.jsp");
            
            String secureFilename = Pic.getSecureFilenamePrefix() + "-" + filename;
            String filepath = savePath + File.separator + secureFilename;
            String comment = request.getParameter("comment");

            //for (Part part : request.getParts()) {
            //    filename = String.format("%s-%s", Pic.getSecureFilenamePrefix(), extractFileName(part));
            //    //part.write(savePath + File.separator + filename);
            //    comment = extractComment(part);
            //}
            // Wait a few seconds, allowing the image to be uploaded
            // completly, prior to redirecting the user to the main page.
            // This is not a secure way to force full upload before
            // redirection; should be improved!
            synchronized (filePart) {
                boolean wait = true;
                while (wait) {
                    filePart.write(filepath);
                    filePart.wait(2500);
                    wait = false;
                }
            }

            String now = Calendar.getInstance().getTime().toString();
            Pic pic = new Pic();
            pic.setUser(currentUser);
            pic.setFilename(secureFilename);
            pic.setComment(comment);
            pic.setUploadedOn(now);
            pic.setModifiedOn(now);
            pic.persist();

            currentSession.setAttribute("message", SUCCESS_MESSAGE);
            currentSession.setAttribute("messageClass", "success");
        } catch (IOException ex) {
            Logger.getLogger(UploadPicture.class.getName()).log(Level.SEVERE, null, ex);
            currentSession.setAttribute("message", FAIL_MESSAGE);
            currentSession.setAttribute("messageClass", "danger");
        } catch (RepositoryConnectionException | UniqueConstraintException | IllegalMonitorStateException ex) {
            Logger.getLogger(UploadPicture.class.getName()).log(Level.SEVERE, null, ex);
            currentSession.setAttribute("message", FAIL_MESSAGE);
            currentSession.setAttribute("messageClass", "danger");
        } catch (InterruptedException ex) {
            Logger.getLogger(UploadPicture.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoPictureSelectedException ex) {
            Logger.getLogger(UploadPicture.class.getName()).log(Level.SEVERE, null, ex);
            currentSession.setAttribute("message", "You have to select a picture to upload.");
            currentSession.setAttribute("messageClass", "warning");
        } catch (RecordNotFoundException ex) {
            Logger.getLogger(UploadPicture.class.getName()).log(Level.SEVERE, null, ex);
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

    private class NoPictureSelectedException extends Exception {
    }
}
