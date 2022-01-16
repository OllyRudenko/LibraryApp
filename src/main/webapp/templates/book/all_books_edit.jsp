<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="ua.olharudenko.libraryapp.enums.Locale"%>

<!DOCTYPE >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<meta charset="UTF-8"/>
<title>ALL AUTHORS</title>
<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css"
                    rel="stylesheet">
</head>
    <body>
    <% String userRole = (String) request.getSession().getAttribute("userRole"); %>
    <% Long userId = (Long) request.getSession().getAttribute("userId"); %>

    <div class="container">
        <table class="table table-striped">
            <caption><h3>Result (Books):</h3></caption>
            <thead>
                <tr class="tr tr-success">
                    <td>Book</td>
                    <td>Authors</td>
                    <td>Description</td>
                </tr>
            </thead>
            <tbody>
            <input type="hidden" name="userRole" value="${userRole}"/>
                <c:forEach items="${books}" var="book">
                    <tr>
                        <td><a class="btn btn-danger" href="/libraryApp/controller?command=viewBookProfile&id=${book.id}">
                        ${book.title}
                        </a></td>
                        <td><a class="btn btn-danger" href="/libraryApp/controller?command=viewAuthorProfile&id=${book.getLocalizedAuthor().authorId}&locale=${book.publishLocale}&userId=${userId}&userRole=${userRole}">
                        ${book.getLocalizedAuthor().fullName}</a>
                        </td>
                        <td>${book.description}</td>
                        <td>
                            <a class="btn btn-danger" onclick="return confirm('Are you sure you want to delete?')" href="/libraryApp/controller?command=deleteBook&id=${book.id}&locale=${book.publishLocale}">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <br><br>
    <div>

<form class="" action="/libraryApp/controller" method="post" onsubmit="return checkForm(this);">
<jsp:useBean id="book" class="ua.olharudenko.libraryapp.models.Book" scope="session"></jsp:useBean>
<jsp:setProperty property="*" name="book"/>
<input type="hidden" name="command" value="addNewBook"/>
<input type="hidden" name="userRole" value="${userRole}"/>

<br>Title:<br><input type="text" name="title" value="${title}"/><br>
<br><span class="error" font-color="red">${errors.title}</font></span>
<br> Author:<br><input type="number" name="authorId" value="authorId"/><br>
<br><span class="error"><font color="red">${errors.authorId}</font></span>
<br> Issue Date:<br><input type="number" name="issueDate" value="issueDate"/></font><br>
<br><span class="error"><font color="red">${errors.issueDate}</font></span>
<br> Description:<br><input type="text" name="description" value="description"/><br>
<br><span class="error"><font color="red">${errors.description}</font></span>
<br> items:<br><input type="number" name="items" value="items"/><br>
<br><span class="error"><font color="red">${errors.items}</font></span>
<br> Publish House:<br><input type="number" name="publishHouseId" value="publishHouseId"/><br>
<br><span class="error"><font color="red">${errors.publishHouseId}</font></span>
<br>
<select name="publishLocale" id="publishLocale">
   <option value="EN">EN</option>
   <option value="UK">UK</option>
   <option value="RU">RU</option>
</select>
<br><br>
<button class="w-100 mb-2 btn btn-lg rounded-4 btn-primary" type="submit">ADD book</button>
</form>
    </div>

    <script src="webjars/jquery/2.2.4/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>

    <script type="text/javascript">

      function checkForm(form)
      {
        if(form.title.value == "") {
          alert("Error: Title cannot be blank!");
          form.title.focus();
          return false;
        }
        if(form.authorId.value == "") {
          alert("Error: Author cannot be blank!");
          form.authorId.focus();
          return false;
        }
        if(isNaN(form.authorId.value)) {
          alert("Error: AuthorId should be a number!");
          form.authorId.focus();
          return false;
        }
        if(form.issueDate.value == "") {
          alert("Error: Issue Year cannot be blank!");
          form.issueDate.focus();
          return false;
        }
        if(form.description.value == "") {
          alert("Error: Description cannot be blank!");
          form.description.focus();
          return false;
        }
        if(form.issueDate.value.length != 4) {
          alert("Error: Issue Year should be 4 simbols!");
          form.issueDate.focus();
          return false;
        }
        if(form.items.value == "") {
          alert("Error: items cannot be blank!");
          form.items.focus();
          return false;
        }
        if(form.publishHouseId.value == "") {
          alert("Error: Publish House cannot be blank!");
          form.items.focus();
          return false;
        }
      }
    </script>
</body>
</html>