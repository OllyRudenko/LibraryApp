<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@page import="ua.olharudenko.libraryapp.models.User"%>
<%@page import="org.apache.taglibs.standard.tag.rt.fmt.BundleTag"%>
<%@ page import="java.util.*,java.util.Locale" import="java.io.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setBundle basename="resources"/>

<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">


<head>
<title>ADMIN</title>

<!-- Bootstrap core CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }
    </style>
</head>

<body>
<br>
<% String loc = (String) request.getSession().getAttribute("userLocale"); %>
<% Long userId = (Long) request.getSession().getAttribute("userId"); %>

<span class="lang">
                    <form>
                        <select name="locale" id="locale" onchange="submit()">
                           <option value="EN">EN</option>
                           <option value="UK">UK</option>
                           <option value="RU">RU</option>
                        </select>
                    </form>
                </span>


<jsp:useBean id="user" class="ua.olharudenko.libraryapp.models.User" scope="session"></jsp:useBean>
<jsp:setProperty property="*" name="user"/>

<div class="album py-5 bg-light">
        <div class="container">

<p class="lead text-muted"><fmt:message key="admin"/></p>
<p class="lead text-muted"> +<jsp:getProperty property="phone" name="user"/><br></p>
<p class="lead text-muted"> <jsp:getProperty property="email" name="user"/><br></p>

    <section class="py-5 text-center container">
<br>
<h1 class="fw-light">
<jsp:getProperty property="firstName" name="user"/>
<jsp:getProperty property="lastName" name="user"/><br>
</h1>
<br><br>
<a href="templates/user/user_profile_update.jsp"><fmt:message key="update"/></a>
<a href="/libraryApp/controller?command=logout"><fmt:message key="logout"/></a>
    </section>
        </div>
</div>

<div>
<table><tr>
<td>
<form class="" action="/libraryApp/controller" method="post">
<input type="hidden" name="command" value="viewAllUsers"/>
<input type="hidden" name="userRole" value="${user.getRole().toString()}"/>
<button class="w-100 mb-2 btn btn-lg rounded-4 btn-primary" type="submit"><fmt:message key="all_users"/></button>
</form>
</td>
<td>
<form class="" action="/libraryApp/controller" method="post">
<input type="hidden" name="command" value="viewAllAuthors"/>
<input type="hidden" name="userId" value="${userId}"/>
<input type="hidden" name="userRole" value="${user.getRole().toString()}"/>
<button class="w-100 mb-2 btn btn-lg rounded-4 btn-primary" type="submit"><fmt:message key="all_authors"/></button>
</form>
</td>
<td>
<form class="" action="/libraryApp/controller" method="post">
<input type="hidden" name="command" value="viewAllBooks"/>
<input type="hidden" name="userRole" value="${user.getRole().toString()}"/>
<input type="hidden" name="userId" value="${user.getId()}"/>
<button class="w-100 mb-2 btn btn-lg rounded-4 btn-primary" type="submit"><fmt:message key="all_books"/></button>
</form>
</td>
<td>
<form class="" action="/libraryApp/controller" method="post">
<input type="hidden" name="command" value="viewAllOrders"/>
<input type="hidden" name="userRole" value="${user.getRole().toString()}"/>
<input type="hidden" name="userId" value="${user.getId()}"/>
<button class="w-100 mb-2 btn btn-lg rounded-4 btn-primary" type="submit"><fmt:message key="all_orders"/></button>
</form>
</td>
<td>
<form class="" action="/libraryApp/controller" method="post">
<input type="hidden" name="command" value="viewAllPublishHouses"/>
<input type="hidden" name="userRole" value="${user.getRole().toString()}"/>
<input type="hidden" name="userId" value="${user.getId()}"/>
<button class="w-100 mb-2 btn btn-lg rounded-4 btn-primary" type="submit"><fmt:message key="all_publishing_houses"/></button>
</form>
</td>
</tr></table>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>