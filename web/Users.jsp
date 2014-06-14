<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="famipics.domain.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="UserBean" class="famipics.domain.User"></jsp:useBean>
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
                <ul>
            <c:forEach var="user" items="${UserBean.all}">
                <li><c:out value="${user.displayName}"></c:out><c:if test="${sessionScope.currentUser.admin}">[<a href="">delete</a>]</c:if></li>
            </c:forEach>
                </ul>
        </div>
    </body>
</html>
