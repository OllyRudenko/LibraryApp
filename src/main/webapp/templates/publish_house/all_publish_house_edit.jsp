<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="ua.olharudenko.libraryapp.models.User"%>

<!DOCTYPE >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<meta charset="UTF-8"/>
<title>ALL PUBLISHING HOUSES</title>
<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css"
                    rel="stylesheet">
</head>
    <body>
    <% String userRole = (String) request.getSession().getAttribute("userRole"); %>
    <div class="container">

    <form id="make_order" action="controller" method="post">

    	<input type="hidden" name="command" value="deletePublishHouse"/>
    	<input type="hidden" name="userId" value="${userId}"/>
    	<input type="hidden" name="userRole" value="${userRole}"/>

        <table class="table table-striped">
            <caption><h3>Result (Publishing Houses):</h3></caption>
            <thead>
                <tr class="tr tr-success">
                    <td>Name</td>
                    <td>city</td>
                    <td>delete</td>
                </tr>
            </thead>

            <tbody>
                <c:forEach items="${publishing_houses}" var="publishing_house">
                    <tr>
                        <td><a class="btn btn-danger" href="/libraryApp/controller?command=viewHouseProfile&id=${publishing_house.publishingHouseId}&userRole=${userRole}&locale=${publishing_house.locale}">
                        ${publishing_house.nameHouse}
                        </a></td>
                        <td>${publishing_house.city}</td>
                        <td>
                        <a class="btn btn-danger" onclick="return confirm('Are you sure you want to delete?')" href="/libraryApp/controller?command=deletePublishHouse&userRole=${userRole}&id=${publishing_house.publishingHouseId}&local=${publishing_house.locale}&userId=${userId}">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <br>

    </form>
    </div>
    <br><br>
    <div>
    <form class="" action="/libraryApp/controller" method="post">
    <input type="hidden" name="command" value="addNewLocalizedPublishingHouse"/>
    <input type="hidden" name="userRole" value="${userRole}"/>
    <br>Locale:<br><input type="text" name="locale" value=""/><br>
    <br> City:<br><input type="text" name="city" value=""/><br>
    <br>   Adress:<br><input type="text" name="adress" value=""/><br>
    <br>   Name:<br><input type="text" name="nameHouse" value=""/>
    <br>   e-mail:<br><input type="text" name="email" value=""/>
    <br>   phone:<br><input type="text" name="phone" value=""/>
    <button class="w-100 mb-2 btn btn-lg rounded-4 btn-primary" type="submit">ADD Publishing House</button>
    </form>
        </div>

    <script src="webjars/jquery/2.2.4/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>