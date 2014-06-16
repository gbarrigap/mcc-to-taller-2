<%-- 
    Document   : Pic
    Created on : Jun 15, 2014, 11:39:33 PM
    Author     : guillermo
--%>

<%@page import="famipics.dao.DaoFactory"%>
<%@page import="famipics.domain.Pic"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="PicBean" class="famipics.domain.Pic" />
<c:set var="pic" value="${PicBean.find(param.pid)}" />
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
        <c:import url="Header.jsp" />

        <div class="container" style="width: inherit">
            <div class="panel panel-default">
                <div class="panel-body">
                    <div class="well">${pic.comment}</div>
                    <img src="files/pics/${pic.filename}" style="width: 100%" />
                    <div class="well-sm">
                        <p>Uploaded on: ${pic.uploadedOn}</p>
                        <p>Las modification: ${pic.modifiedOn}</p>
                        <c:if test="${pic.uid == sessionScope.currentUser.uid}">
                            <p>[<a href="EditPic.jsp?pid=${pic.pid}">edit</a>]</p>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
