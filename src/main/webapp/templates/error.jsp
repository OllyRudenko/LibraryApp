<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page import="java.util.*" import="java.io.*"%>

<html>
<head>
<title>ERROR</title>
</head>

<body>
<% String message = (String) request.getSession().getAttribute("errorMessage");  %>

<%= message %>

</body>
</html>