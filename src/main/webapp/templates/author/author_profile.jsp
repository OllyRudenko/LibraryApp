<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@page import="ua.olharudenko.libraryapp.models.LocalizedAuthor"%>
<%@page import="ua.olharudenko.libraryapp.enums.Locale"%>
<%@page import="ua.olharudenko.libraryapp.enums.Role"%>
<%@page import="java.util.Optional"%>
<%@ page import="java.util.*" import="java.io.*"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
<title>AUTHOR</title>
</head>

<body>
<br>
<% String loc = (String) request.getSession().getAttribute("userLocale"); %>

<input type="hidden" name="locale" value="local"/>
<input type="hidden" id="locale" th:field="*{locale}" th:value="${loc}"/>

<br><br>



<% LocalizedAuthor author = (LocalizedAuthor) request.getSession().getAttribute("author"); %>
<% String userRole = (String) request.getSession().getAttribute("userRole"); %>
<% Long userId = (Long) request.getSession().getAttribute("userId"); %>

<%= author.getFullName() %>
<br>biografy: <%= author.getBiografy() %>

<br><br>


<form id="make_order" action="controller">
				<input type="hidden" name="command" value="makeOrder"/>
				<input type="hidden" name="userRole" value="${userRole}"/>
				<input type="hidden" name="userId" value="${userId}"/>

				<table id="list_menu_table">
					<thead>
						<tr>
							<td>title</td>
							<td>local</td>
							<td>choose</td>
						</tr>
					</thead>

					<c:forEach items="${books}" var="book">
						<tr>
							<td>${book.title}</td>
							<td>${book.publishLocale}</td>
							<td><input type="checkbox" name="id" value="${book.id}"/></td>
						</tr>
					</c:forEach>
				</table>
				<br>
				<input type="submit"/>
			</form>

</body>
</html>