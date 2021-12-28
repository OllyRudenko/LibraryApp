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
    <div class="container">
        <table class="table table-striped">
            <caption><h3>Result (Authors):</h3></caption>
            <thead>
                <tr class="tr tr-success">
                    <td>Full Name</td>
                    <td>Biografy</td>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${authors}" var="author">
                    <tr>
                        <td><a class="btn btn-danger" href="/libraryApp/controller?command=viewAuthorProfile&id=${author.authorId}&locale=${author.local}">
                        ${author.fullName}
                        </a></td>
                        <td>${author.biografy}</td>
                        <td>
                            <a class="btn btn-danger" onclick="return confirm('Are you sure you want to delete?')" href="/libraryApp/controller?command=deleteAuthorProfile&role=${userRole}&id=${author.authorId}&local=${author.local}">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <br><br>
    <div>
<form class="" action="/libraryApp/controller" method="post">
<input type="hidden" name="command" value="addNewAuthor"/>
<input type="hidden" name="role" value="${userRole}"/>
<br>Full name:<br><input type="text" name="fullName" value=""/><br>
<br> Biografy:<br><input type="text" name="biografy" value=""/><br>
<br>   Locale:<br><input type="text" name="local" value=""/>
<button class="w-100 mb-2 btn btn-lg rounded-4 btn-primary" type="submit">ADD author</button>
</form>
    </div>

    <script src="webjars/jquery/2.2.4/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>