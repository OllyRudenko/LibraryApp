<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
    <% Long userId = (Long) request.getSession().getAttribute("userId"); %>

    <div class="container">
        <table class="table table-striped">
            <caption><h3>Result (Books):</h3></caption>
            <thead>
                <tr class="tr tr-success">
                    <td>Book</td>
                    <td>Authors</td>
                    <td>Description</td>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${books}" var="book">
                    <tr>
                        <td><a class="btn btn-danger" href="/libraryApp/controller?command=viewBookProfile&id=${book.id}&userRole=${userRole}">
                        ${book.title}
                        </a></td>
                        <td><a class="btn btn-danger" href="/libraryApp/controller?command=viewAuthorProfile&id=${book.getLocalizedAuthor().authorId}&locale=${book.publish_locale}&userId=${userId}&userRole=${userRole}">
                        ${book.getLocalizedAuthor().fullName}</a>
                        </td>
                        <td>${book.description}</td>
                        <td>
                            <a class="btn btn-danger" onclick="return confirm('Are you sure you want to delete?')" href="/libraryApp/controller?command=deleteBook&userRole=${userRole}&id=${book.getLocalizedAuthor().authorId}&locale=${book.publish_locale}">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <br><br>
    <div>
    </div>

    <script src="webjars/jquery/2.2.4/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>