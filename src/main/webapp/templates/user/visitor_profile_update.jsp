<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@page import="ua.olharudenko.libraryapp.models.User"%>
<%@ page import="java.util.*" import="java.io.*"%>

<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
<title>UPDATE VISITOR</title>
</head>

<body>
<% User user = (User) request.getSession().getAttribute("user"); %>
<%= user.getId() %>

<div class="modal-body p-5 pt-0">
                <form class="" method="POST" action="/libraryApp/controller">
                    <input type="hidden" name="command" value="updateUser"/>
                    <input type="hidden" name="id" value="${user.id}"/>
                    <input type="hidden" name="role" value="${user.role}"/>
                    <div class="form-floating mb-3">
                        <input type="text" class="form-control rounded-4" id="floatingInput"
                               placeholder="First name" name="firstName" value="${user.firstName}">
                        <label for="floatingInput">First name</label>
                    </div>
                    <div class="form-floating mb-3">
                        <input type="text" class="form-control rounded-4" id="floatingInput"
                               placeholder="Last name" name="lastName"  value="${user.lastName}">
                        <label for="floatingInput">Last name</label>
                    </div>
                    <div class="form-floating mb-3">
                        <input type="email" class="form-control rounded-4" id="floatingInput"
                               placeholder="name@example.com" name="email" value="${user.email}">
                        <label for="floatingInput">Email address</label>
                    </div>
                    <div class="form-floating mb-3">
                        <input type="text" class="form-control rounded-4" id="floatingPassword1"
                               placeholder="Password"  name="password" value="${user.password}">
                        <label for="floatingPassword1">Password</label>
                    </div>
                    <div class="form-floating mb-3">
                        <input type="text" class="form-control rounded-4" id="floatingInput"
                               placeholder="phone" name="phone" value="${user.phone}">
                        <label for="floatingInput">phone</label>
                    </div>
                    <div class="form-floating mb-3">
                        <input type="text" class="form-control rounded-4" id="floatingInput"
                               placeholder="adress" name="adress" value="${user.adress}">
                        <label for="floatingInput">adress</label>
                    </div>
                    <button class="w-100 mb-2 btn btn-lg rounded-4 btn-primary" type="submit">UPDATE</button>
                    <small class="text-muted">clicking Update</small>
                    <hr class="my-4">
                </form>
            </div>
        </div>
<%= user.getFirstName() %>
<%= user.getLastName() %>
<br>phone: <%= user.getPhone() %>
<br><br>

</body>
</html>