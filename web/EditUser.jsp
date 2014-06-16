<%-- 
    Document   : EditUser
    Created on : Jun 16, 2014, 6:06:40 AM
    Author     : guillermo
--%>

<%@page import="famipics.domain.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="UserBean" class="famipics.domain.User" />
<c:set var="currentUser" value="${sessionScope.currentUser}" />
<c:set var="user" value="${UserBean.find(param.uid)}" />
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
        <h2>Editing user <em>${user.displayName}</em></h2>
        <form method="post" action="UpdateUser">
            <input id="uid" name="uid" type="hidden" value="${user.uid}" />
            <fieldset>
                <div class="form-group">
                    <label for="email">Email</label>
                    <input id="email" name="email" type="text" class="form-control" value="${user.email}" />
                </div>

                <div class="form-group">
                    <label for="display-name">Display Name</label>
                    <input id="display-name" name="display-name" type="text" class="form-control" value="${user.displayName}"/>
                </div>

                <div class="form-group">
                    <label for="password">Password</label>
                    <input id="password" name="password" type="password" class="form-control" />
                </div>

                <div class="form-group">
                    <label for="password-confirmation">Password Confirmation</label>
                    <input id="password-confirmation" name="password-confirmation" type="password" class="form-control" />
                </div>
                
                <div class="form-group">
                    <input id="admin" name="admin" value="true" type="checkbox" ${user.admin ? "checked" : ""} ${user.uid == currentUser.uid ? "disabled" : ""} />
                    <label for="admin">Site administrator</label>
                </div>

                <div class="form-group text-right">
                    <a href="Users.jsp">Cancel</a>
                    <input type="submit" value="Update" class="btn btn-primary" />
                </div>
            </fieldset>
        </form>
    </div>
</body>
</html>
