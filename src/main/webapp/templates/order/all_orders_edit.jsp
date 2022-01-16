<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="ua.olharudenko.libraryapp.models.LocalizedAuthor"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<meta charset="UTF-8"/>
<title>ALL AUTHORS</title>
<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css"
                    rel="stylesheet">
</head>
    <body>
    <% String userRole = (String) request.getSession().getAttribute("userRole"); %>
    <div class="container">
    <form id="make_order" action="controller" method="post">


        	<input type="hidden" name="command" value="changeAdminOrderStatus"/>
        	<input type="hidden" name="userId" value="${userId}"/>
        	<input type="hidden" name="userRole" value="${userRole}"/>

        <table class="table table-striped">
            <caption><h3>Result (Orders):</h3></caption>
            <thead>
                <tr class="tr tr-success">
                    <td>Book</td>
                    <td>Authors</td>
                    <td>User</td>
                    <td>Order status</td>
                    <td>Admin status</td>
                    <td>is confirm</td>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${orders}" var="order">
                    <tr>
                        <td><a class="btn btn-danger" href="/libraryApp/controller?command=viewBookProfile&id=${order.getBook().id}">
                        ${order.getBook().title}
                        </a></td>
                        <td><a class="btn btn-danger" href="/libraryApp/controller?command=viewAuthorProfile&id=${order.getBook().getLocalizedAuthor().authorId}&locale=${order.getBook().getLocalizedAuthor().local}">
                        ${order.getBook().getLocalizedAuthor().fullName}</a>
                        </td>
                        <td>${order.getUser().firstName} ${order.getUser().lastName}</td>
                        <td>${order.orderStatus}</td>
                        <td>${order.adminOrderStatus}</td>
                        <td><input type="checkbox" name="id" value="${order.id}"/></td>
                        <td>
                            <a class="btn btn-danger" onclick="return confirm('Are you sure you want to delete?')" href="/libraryApp/controller?command=deleteOrder&userRole=${userRole}&userId=${userId}&id=${order.id}">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <br>
        <input type="submit"/>
        </form>
    </div>
    <br><br>
    <div>
    </div>

    <script src="webjars/jquery/2.2.4/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>