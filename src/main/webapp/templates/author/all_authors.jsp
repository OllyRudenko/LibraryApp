<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.*" import="java.io.*"%>
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
                        <td><a class="btn btn-danger" href="/libraryApp/controller?command=viewAuthorProfile&id=${author.authorId}&locale=${author.local}&userId=${userId}&userRole=${userRole}">
                        ${author.fullName}
                        </a></td>
                        <td>${author.biografy}</td>
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