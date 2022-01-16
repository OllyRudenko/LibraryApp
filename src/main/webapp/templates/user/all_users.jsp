<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Real time info</title>
<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css"
                    rel="stylesheet">
</head>
    <body>
    <div class="container">
    <form id="make_order" action="controller" method="post">

        	<input type="hidden" name="command" value="changeUserRole"/>
        	<input type="hidden" name="userId" value="${userId}"/>
        	<input type="hidden" name="userRole" value="${userRole}"/>

        <table class="table table-striped">
            <caption><h3>Result (Users):</h3></caption>
            <thead>
                <tr class="tr tr-success">
                    <td>First Name</td>
                    <td>Last Name</td>
                    <td>User role</td>
                    <td>Librarian role</td>
                    <td>Admin role</td>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.role}</td>
                        <td><input type="checkbox" name="idForLibrarian" value="${user.id}"/></td>
                        <td><input type="checkbox" name="idForAdmin" value="${user.id}"/></td>
                        <td><input type="checkbox" name="idForVisitor" value="${user.id}"/></td>
                        <td>
                            <a class="btn btn-danger" onclick="return confirm('Are you sure you want to delete?')" href="/libraryApp/controller?command=deleteUser&userRole=${user.role}&id=${user.id}">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <br>
        <input type="submit"/>
        </form>
    </div>
    <script src="webjars/jquery/2.2.4/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>