<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@page import="ua.olharudenko.libraryapp.models.User"%>
<%@page import="java.util.Optional"%>

<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
<title>VISITOR</title>
</head>

<body>
<br>
<% String loc = (String) request.getSession().getAttribute("userLocale"); %>

<input type="hidden" name="locale" value="login"/>
<input type="hidden" id="locale" th:field="*{locale}" th:value="${loc}"/>

<div class="topnav">
  <a class="active" href="#home">EN</a>
  <a href="#news">UK</a>
  <a href="#contact">RU</a>
</div>
<br><br>



<% User user = (User) request.getSession().getAttribute("user"); %>

<%= user.getFirstName() %>
<%= user.getLastName() %>
<br>phone: <%= user.getPhone() %>

<br><br>
<a href="templates/user/visitor_profile_update.jsp">update</a>
</body>
</html>