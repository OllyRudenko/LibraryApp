<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="ua.olharudenko.libraryapp.models.User"%>

<!DOCTYPE >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<meta charset="UTF-8"/>
<title>ALL MY ORDERS</title>
<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
</head>
    <body>
    <% String userRole = (String) request.getSession().getAttribute("userRole"); %>
    <% Long userId = (Long) request.getSession().getAttribute("userId"); %>
    <div class="container">

    	<input type="hidden" name="command" value="viewAllBooks"/>
    	<input type="hidden" name="userId" value="${userId}"/>
    	<input type="hidden" name="userRole" value="${userRole}"/>

        <table class="table table-striped">
            <caption><h3>Result (Orders):</h3></caption>
            <thead>
                <tr class="tr tr-success">
                    <td>Book</td>
                    <td>Authors</td>
                    <td>Status</td>
                    <td>is confirm</td>
                </tr>
            </thead>

            <tbody>
                <c:forEach items="${orders}" var="order">
                    <tr>
                        <td><a class="btn btn-danger" href="/libraryApp/controller?command=viewBookProfile&id=${order.getBook().id}">
                        ${order.getBook().title}
                        </a></td>
                        <td><a class="btn btn-danger" href="/libraryApp/controller?command=viewAuthorProfile&id=${order.getBook().getLocalizedAuthor().authorId}&locale=${order.getBook().publishLocale}">
                        ${order.getBook().getLocalizedAuthor().fullName}</a>
                        </td>
                        <td>${order.orderStatus}</td>
                        <td>${order.adminOrderStatus}</td>
                        <td>
                        <a class="btn btn-danger" onclick="return confirm('Are you sure you want to delete?')" href="/libraryApp/controller?command=deleteOrder&id=${order.id}">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <br><br>
    <div>
    </div>

<div>
<form class="" action="/libraryApp/controller" method="get">
<input type="hidden" name="command" value="viewAllBooks"/>
<input type="hidden" name="userRole" value="${userRole.toString()}"/>
<input type="hidden" name="userId" value="${userId}"/>
<button class="w-100 mb-2 btn btn-lg rounded-4 btn-primary" type="submit">make new orders</button>
</form>
</div>
    <script src="webjars/jquery/2.2.4/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>