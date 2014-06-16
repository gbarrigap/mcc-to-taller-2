<%-- 
    Document   : EditPic
    Created on : Jun 16, 2014, 7:46:24 AM
    Author     : guillermo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="PicBean" class="famipics.domain.Pic" />
<c:set var="pic" value="${PicBean.find(param.pid)}" />
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
            <h2>Editing pic</h2>
            <div class="thumbnail">
                <img src="files/pics/${pic.filename}" alt="" />
            </div>
            <form method="post" action="UpdatePic">
                <input id="pid" name="pid" type="hidden" value="${pic.pid}" />
                <fieldset>
                    <div class="form-group">
                        <label for="comment">Comment</label>
                        <textarea id="comment" name="comment" maxlength="100" class="form-control">${pic.comment}</textarea>
                    </div>

                    <div class="form-group text-right">
                        <a href="Users.jsp">Cancel</a>
                        <input type="submit" value="Update" class="btn btn-primary" />
                    </div>
                </fieldset>
            </form>
    </body>
</html>
