<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@page import="ua.olharudenko.libraryapp.models.LocalizedAuthor"%>
<%@page import="java.util.Optional"%>

<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
<title>AUTHOR</title>
</head>

<body>
<br>
<% String loc = (String) request.getSession().getAttribute("userLocale"); %>
<% String userRole = (String) request.getSession().getAttribute("userRole"); %>

<input type="hidden" name="locale" value="local"/>
<input type="hidden" id="locale" th:field="*{locale}" th:value="${loc}"/>

<div class="topnav">
  <a class="active" href="#home">EN</a>
  <a href="#news">UK</a>
  <a href="#contact">RU</a>
</div>
<br><br>



<% LocalizedAuthor author = (LocalizedAuthor) request.getSession().getAttribute("author"); %>

<%= author.getFullName() %>
<br>biografy: <%= author.getBiografy() %>

<br><br>
<a href="templates/author/author_profile_update.jsp">update</a>
</body>
</html>