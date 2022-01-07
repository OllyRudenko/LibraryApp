<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@page import="ua.olharudenko.libraryapp.models.LocalizedAuthor"%>
<%@page import="java.util.Optional"%>

<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
<title>BOOK</title>
</head>

<body>
<br>
<% String loc = (String) request.getSession().getAttribute("userLocale"); %>

<input type="hidden" name="locale" value="local"/>
<input type="hidden" id="locale" th:field="*{locale}" th:value="${loc}"/>

<jsp:useBean id="book" class="ua.olharudenko.libraryapp.models.Book" scope="session"></jsp:useBean>
<jsp:setProperty property="*" name="book"/>

<h1 class="fw-light">
<jsp:getProperty property="title" name="book"/>
<br>
</h1>
<jsp:getProperty property="description" name="book"/>

<% LocalizedAuthor author = (LocalizedAuthor) request.getSession().getAttribute("author"); %>


<br><br>
</body>
</html>