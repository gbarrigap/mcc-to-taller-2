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

        <!-- jQuery -->
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>

        <!-- Boostrap -->
        <link rel="stylesheet" href="lib/bootstrap/css/bootstrap.css" />
        <link rel="stylesheet" href="lib/bootstrap/css/bootstrap-theme.css" />
        <script src="lib/bootstrap/js/bootstrap.js"></script>

        <!-- Custom CSS & JavaScript -->
        <link rel="stylesheet" href="css/screen.css" />
    </head>
    <body>
        <c:import url="Header.jsp" />

        <div class="container">
            <c:if test="${sessionScope.message != null}">
                <div class="alert alert-${sessionScope.messageClass}">${sessionScope.message}</div>
                <c:remove var="message" scope="session" />
            </c:if>

            <div class="panel panel-default">
                <div class="panel-body">
                    <form method="post" action="UploadPicture" enctype="multipart/form-data">
                        <fieldset>
                            <div class="form-group">
                                <label for="filename">File</label>
                                <input id="filename" name="filename" type="file" class="form-control" />
                            </div>

                            <div class="form-group">
                                <label for="comment">Comment</label>
                                <textarea id="comment" name="comment" maxlength="100" class="form-control"></textarea>
                            </div>

                            <div class="form-group text-right">
                                <a href="Users.jsp">Cancel</a>
                                <input type="submit" value="Upload" class="btn btn-primary" />
                            </div>
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
