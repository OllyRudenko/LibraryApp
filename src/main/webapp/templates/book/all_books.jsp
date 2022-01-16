<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="ua.olharudenko.libraryapp.models.User"%>

<!DOCTYPE >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<meta charset="UTF-8"/>
<title>ALL BOOKS</title>
<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css"
                    rel="stylesheet">
</head>
    <body>
    <% String userRole = (String) request.getSession().getAttribute("userRole"); %>
    <% Long userId = (Long) request.getSession().getAttribute("userId"); %>
    <div class="container">
    <input type="hidden" name="userRole" value="${userRole}"/>

    <form id="make_order" action="controller" method="post">


    	<input type="hidden" name="command" value="makeOrder"/>
    	<input type="hidden" name="userId" value="${userId}"/>
    	<input type="hidden" name="userRole" value="${userRole}"/>

        <table class="table table-striped">
            <caption><h3>Result (Books):</h3></caption>
            <thead>
                <tr class="tr tr-success">
                    <td>Book</td>
                    <td>Authors</td>
                    <td>Description</td>
                    <td>choose</td>
                </tr>
            </thead>

            <tbody>
                <c:forEach items="${books}" var="book">
                    <tr>
                        <td><a class="btn btn-danger" href="/libraryApp/controller?command=viewBookProfile&id=${book.id}">
                        ${book.title}
                        </a></td>
                        <td><a class="btn btn-danger" href="/libraryApp/controller?command=viewAuthorProfile&id=${book.getLocalizedAuthor().authorId}&locale=${book.publishLocale}">
                        ${book.getLocalizedAuthor().fullName}</a>
                        </td>
                        <td>${book.description}</td>
                        <td><input type="checkbox" name="id" value="${book.id}"/></td>
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