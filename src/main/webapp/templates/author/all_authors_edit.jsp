<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
        <input type="hidden" name="userRole" value="${userRole}"/>
        <input type="hidden" name="userId" value="${userId}"/>
            <caption><h3>Result (Authors):</h3></caption>
            <thead>
                <tr class="tr tr-success">
                    <td>Full Name</td>
                    <td>Biografy</td>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${authors}" var="author">
                    <tr>
                        <td><a class="btn btn-danger" href="/libraryApp/controller?command=viewAuthorProfile&id=${author.authorId}&locale=${author.local}">
                        ${author.fullName}
                        </a></td>
                        <td>${author.biografy}</td>
                        <td>
                            <a class="btn btn-danger" onclick="return confirm('Are you sure you want to delete?')" href="/libraryApp/controller?command=deleteAuthorProfile&id=${author.authorId}&locale=${author.local}">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <br><br>
    <div>
<form class="" action="/libraryApp/controller" method="post" onsubmit="return checkForm(this);">
<jsp:useBean id="localizedAuthor" class="ua.olharudenko.libraryapp.models.LocalizedAuthor" scope="session"></jsp:useBean>
<jsp:setProperty property="*" name="localizedAuthor"/>

<input type="hidden" name="command" value="addNewAuthor"/>
<input type="hidden" name="userRole" value="${userRole}"/>
<input type="hidden" name="userId" value="${userId}"/>

<br>Full name:<br><input type="text" name="fullName" value=""/><br>
<br><span class="error" font-color="red">${errors.fullName}</font></span>
<br> Biografy:<br><input type="text" name="biografy" value=""/><br>
<br><span class="error" font-color="red">${errors.biografy}</font></span>
<br>
<select name="local" id="local">
   <option value="EN">EN</option>
   <option value="UK">UK</option>
   <option value="RU">RU</option>
</select>
<br><br>
<button class="w-100 mb-2 btn btn-lg rounded-4 btn-primary" type="submit">ADD author</button>
</form>
    </div>

    <script src="webjars/jquery/2.2.4/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>

    <script type="text/javascript">

      function checkForm(form)
      {
        if(form.fullName.value == "") {
          alert("Error: Full Name cannot be blank!");
          form.fullName.focus();
          return false;
        }
        if(form.fullName.value.length > 200) {
          alert("Error: Full Name cannot be longer 200 simbols");
          form.fullName.focus();
          return false;
        }
        if(form.biografy.value == "") {
          alert("Error: Biography cannot be blank!");
          form.biografy.focus();
          return false;
        }
        if(form.biografy.value.length > 1200) {
          alert("Error: Biography cannot be longer 1200 simbols!");
          form.biografy.focus();
          return false;
        }
      }
    </script>

</body>
</html>