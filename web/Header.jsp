<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-default navbar-static-top" role="navigation">
    <div class="container">
        <ul class="nav navbar-nav">
            <li class="${'home' == param.page ? 'active' : ''}"><a href="Landing">Home</a></li>
            <li class="${'users' == param.page ? 'active' : ''}"><a href="Users.jsp?page=users">Users</a></li>
            <li class="${'upload' == param.page ? 'active' : ''}"><a href="UploadPictures.jsp?page=upload">Upload Picture</a></li>
        </ul>
        
        <ul class="nav navbar-nav navbar-right">
            <li><p class="navbar-text navbar-right">Signed in as</p></li>
            <li class="dropdown navbar-right">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">${sessionScope.currentUser.displayName}<b class="caret"></b></a>
                <ul class="dropdown-menu">
                    <li><a href="EditUser.jsp?uid=${sessionScope.currentUser.uid}">My profile</a></li>
                    <li><a href="#">My pics</a></li>
                    <li><a href="#">My activity</a></li>
                    <li class="divider"></li>
                    <li><a href="Logout">Logout</a></li>
                </ul>
            </li>
        </ul>
    </div>
</nav>