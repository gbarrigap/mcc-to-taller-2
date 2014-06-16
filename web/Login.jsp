<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>FamiPics</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

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
        <div class="jumbotron">
            <h1>FamiPics</h1>
        </div>
        
        <div class="container">
            
            <c:if test="${sessionScope.message != null}">
                <div class="alert alert-${sessionScope.messageClass}">${sessionScope.message}</div>
                <c:remove var="message" scope="session" />
            </c:if>
                
            <form method="post" action="Login">
                <fieldset>
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input id="email" name="email" type="text" class="form-control" />
                    </div>

                    <div class="form-group">
                        <label for="password">Password</label>
                        <input id="password" name="password" type="password" class="form-control" />
                    </div>

                    <div class="form-group text-right">
                        <a href="CreateAccount.jsp">I don't have an account</a>
                        <input type="submit" value="Login" class="btn btn-primary" />
                    </div>
                </fieldset>
            </form>
        </div>
    </body>
</html>
