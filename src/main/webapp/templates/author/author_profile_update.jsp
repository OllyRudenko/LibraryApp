<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@page import="ua.olharudenko.libraryapp.models.LocalizedAuthor"%>
<%@ page import="java.util.*" import="java.io.*"%>

<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
<title>UPDATE VISITOR</title>
</head>

<body>
<% LocalizedAuthor author = (LocalizedAuthor) request.getSession().getAttribute("author"); %>
<%= author.getAuthorId() %>

<div class="modal-body p-5 pt-0">
                <form class="" method="POST" action="/libraryApp/controller">
                    <input type="hidden" name="command" value="updateAuthorProfile"/>
                    <input type="hidden" name="authorId" value="${author.authorId}"/>
                    <input type="hidden" name="local" value="${author.local}"/>
                    <input type="hidden" name="userRole" value="${user.role}"/>
                    <div class="form-floating mb-3">
                        <input type="text" class="form-control rounded-4" id="floatingInput"
                               placeholder="First name" name="fullName" value="${author.fullName}">
                        <label for="floatingInput">Full name</label>
                    </div>
                    <div class="form-floating mb-3">
                        <input type="text" class="form-control rounded-4" id="floatingInput"
                               placeholder="Biografy" name="biografy"  value="${author.biografy}">
                        <label for="floatingInput">Biografy</label>
                    </div>

                    <button class="w-100 mb-2 btn btn-lg rounded-4 btn-primary" type="submit">UPDATE</button>
                    <small class="text-muted">clicking Update</small>
                    <hr class="my-4">
                </form>
            </div>
        </div>

<br><br>

</body>
</html>