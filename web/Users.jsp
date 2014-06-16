<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="famipics.domain.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="UserBean" class="famipics.domain.User" />
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
        <c:import url="Header.jsp?page=users" />

        <div class="container" style="width: inherit;">
            <div class="panel panel-default">
                <!-- Default panel contents -->
                <div class="panel-heading">Users</div>
                <!--div class="panel-body"><p></p></div-->

                <!-- Table -->
                <table class="table">
                    <tr>
                        <th>Display name</th>
                        <th>Email</th>
                        <th>Last login</th>
                        <th>Admin</th>
                        <th>Operations</th>
                    </tr>
                    <c:forEach var="user" items="${UserBean.all}">
                        <tr>
                            <td>${user.displayName}</td>
                            <td>${user.email}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${empty user.lastLogin}">
                                        Never
                                    </c:when>
                                    <c:otherwise>
                                        ${user.lastLogin}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${user.admin}</td>
                            <td>
                                <c:if test="${sessionScope.currentUser.admin && user.uid != sessionScope.currentUser.uid}">
                                    [<a href="DeleteUser?uid=${user.uid}">delete</a>]
                                </c:if>
                                <c:if test="${sessionScope.currentUser.admin || user.uid == sessionScope.currentUser.uid}">
                                    [<a href="EditUser.jsp?uid=${user.uid}">edit</a>]
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </body>
</html>
