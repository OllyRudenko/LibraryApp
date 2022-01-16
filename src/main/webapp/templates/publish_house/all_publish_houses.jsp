<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="ua.olharudenko.libraryapp.models.User"%>

<!DOCTYPE >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<meta charset="UTF-8"/>
<title>ALL PUBLISHING HOUSES</title>
<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
</head>
    <body>
    <% String userRole = (String) request.getSession().getAttribute("userRole"); %>
    <% Long userId = (Long) request.getSession().getAttribute("userId"); %>
    <div class="container">

    	<input type="hidden" name="userId" value="${userId}"/>
    	<input type="hidden" name="userRole" value="${userRole}"/>

        <table class="table table-striped">
            <caption><h3>Result (Publishing Houses):</h3></caption>
            <thead>
                <tr class="tr tr-success">
                    <td>Name</td>
                    <td>city</td>
                </tr>
            </thead>

            <tbody>
                <c:forEach items="${publishing_houses}" var="publishing_house">
                    <tr>
                        <td><a class="btn btn-danger" href="/libraryApp/controller?command=viewHouseProfile&id=${publishing_house.publishingHouseId}">
                        ${publish_house.nameHouse}
                        </a></td>
                        <td>${publish_house.city}</td>
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