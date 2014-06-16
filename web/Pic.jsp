<%-- 
    Document   : Pic
    Created on : Jun 15, 2014, 11:39:33 PM
    Author     : guillermo
--%>

<%@page import="famipics.dao.DaoFactory"%>
<%@page import="famipics.domain.Pic"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Pic pic = DaoFactory.getPicDao().retrieve(Integer.parseInt(request.getParameter("pid")));%>
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

            <img src="files/pics/<%= pic.getFilename()%>" style="max-width: 95%" />
        <p><%= pic.getComment()%></p>
    </body>
</html>
