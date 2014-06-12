<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="famipics.domain.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="UserBean" class="famipics.domain.User"></jsp:useBean>
    <!DOCTYPE html>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>JSP Page</title>
        </head>
        <body>
            <h1>Hello World!</h1>
        <c:forEach var="user" items="${UserBean.all}">
            <h2>H_<c:out value="${user.displayName}"></c:out></h2>
        </c:forEach>
    </body>
</html>
