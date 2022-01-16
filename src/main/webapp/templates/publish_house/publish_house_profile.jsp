<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@page import="ua.olharudenko.libraryapp.models.LocalizedPublishingHouse"%>
<%@page import="java.util.Optional"%>

<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
<title>BOOK</title>
</head>

<body>
<br>
<% String loc = (String) request.getSession().getAttribute("userLocale"); %>
<% Long userId = (Long) request.getSession().getAttribute("userId"); %>

<input type="hidden" name="locale" value="local"/>
<input type="hidden" id="locale" th:field="*{locale}" th:value="${loc}"/>

<jsp:useBean id="publishing_house" class="ua.olharudenko.libraryapp.models.LocalizedPublishingHouse" scope="session"></jsp:useBean>
<jsp:setProperty property="*" name="publishing_house"/>

<h1 class="fw-light">
<jsp:getProperty property="nameHouse" name="publishing_house"/>
<br>
</h1>
<jsp:getProperty property="city" name="publishing_house"/>


<br><br>
</body>
</html>