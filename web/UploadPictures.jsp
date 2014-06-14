<%-- 
    Document   : UploadPictures
    Created on : Jun 13, 2014, 9:28:36 PM
    Author     : guillermo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

        <!-- Boostrap -->
        <link rel="stylesheet" href="lib/bootstrap/css/bootstrap.css" />
        <link rel="stylesheet" href="lib/bootstrap/css/bootstrap-theme.css" />
        <script src="lib/bootstrap/js/bootstrap.js"></script>

        <!-- Custom CSS & JavaScript -->
        <link rel="stylesheet" href="css/screen.css" />
    </head>
    <body>
        <c:import url="Header.jsp"></c:import>

        <div class="container">
            <form method="post" action="UploadPicture" enctype="multipart/form-data">
                <fieldset>
                    <input id="filename" name="filename" type="file" size="50" />
                    <input type="submit" value="Upload picture" class="btn" />
                </fieldset>
            </form>
        </div>
    </body>
</html>
