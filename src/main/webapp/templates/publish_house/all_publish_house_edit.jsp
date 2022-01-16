<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="ua.olharudenko.libraryapp.models.User"%>

<!DOCTYPE >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<meta charset="UTF-8"/>
<title>ALL PUBLISHING HOUSES</title>
<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css"
                    rel="stylesheet">
</head>
    <body>
    <% String userRole = (String) request.getSession().getAttribute("userRole"); %>
    <% Long userId = (Long) request.getSession().getAttribute("userId"); %>
    <div class="container">

    <form id="make_order" action="controller" method="post">

    	<input type="hidden" name="command" value="deletePublishHouse"/>
    	<input type="hidden" name="userId" value="${userId}"/>
    	<input type="hidden" name="userRole" value="${userRole}"/>

        <table class="table table-striped">
            <caption><h3>Result (Publishing Houses):</h3></caption>
            <thead>
                <tr class="tr tr-success">
                    <td>Name</td>
                    <td>city</td>
                    <td>delete</td>
                </tr>
            </thead>

            <tbody>
                <c:forEach items="${publishing_houses}" var="publishing_house">
                    <tr>
                        <td><a class="btn btn-danger" href="/libraryApp/controller?command=viewHouseProfile&id=${publishing_house.publishingHouseId}&locale=${publishing_house.locale}">
                        ${publishing_house.nameHouse}
                        </a></td>
                        <td>${publishing_house.city}</td>
                        <td>
                        <a class="btn btn-danger" onclick="return confirm('Are you sure you want to delete?')" href="/libraryApp/controller?command=deletePublishHouse&id=${publishing_house.publishingHouseId}&local=${publishing_house.locale}">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <br>

    </form>
    </div>
    <br><br>
    <div>
    <form class="" action="/libraryApp/controller" method="post">
    <jsp:useBean id="lph" class="ua.olharudenko.libraryapp.models.LocalizedPublishingHouse" scope="session"></jsp:useBean>
    <jsp:setProperty property="*" name="lph"/>
    <jsp:useBean id="ph" class="ua.olharudenko.libraryapp.models.PublishingHouse" scope="session"></jsp:useBean>
    <jsp:setProperty property="*" name="ph"/>

    <input type="hidden" name="command" value="addNewLocalizedPublishingHouse"/>
    <input type="hidden" name="userRole" value="${userRole}"/>

    <br>Locale:<br>
    <select name="locale" id="locale">
       <option value="EN">EN</option>
       <option value="UK">UK</option>
       <option value="RU">RU</option>
    </select>
    <br>
    <br> City:<br><input type="text" name="city" value="${lph.city}"/><br>
    <br><span class="error"><font color="red">${errors.city}</font></span>
    <br>   Adress:<br><input type="text" name="adress" value="${lph.adress}"/><br>
    <br><span class="error"><font color="red">${errors.adress}</font></span>
    <br>   Name:<br><input type="text" name="nameHouse" value="${lph.nameHouse}"/>
    <br><span class="error"><font color="red">${errors.nameHouse}</font></span>
    <br>   e-mail:<br><input type="text" name="email" value="${ph.email}"/>
    <br><span class="error"><font color="red">${errors.email}</font></span>
    <br>   phone:<br><input type="text" name="phone" value="${ph.phone}"/>
    <br><span class="error"><font color="red">${errors.phone}</font></span>
    <br>
    <button class="w-100 mb-2 btn btn-lg rounded-4 btn-primary" type="submit">ADD Publishing House</button>
    </form>
        </div>

    <script src="webjars/jquery/2.2.4/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>

    <script type="text/javascript">

      function checkForm(form)
      {
        if(form.city.value == "") {
          alert("Error: City cannot be blank!");
          form.city.focus();
          return false;
        }
        if(form.adress.value == "") {
          alert("Error: Adress cannot be blank!");
          form.adress.focus();
          return false;
        }
        if(form.nameHouse.value == "") {
          alert("Error: Publishing House Name cannot be blank!");
          form.nameHouse.focus();
          return false;
        }
        if(form.email.value == "") {
          alert("Error: Email cannot be blank!");
          form.email.focus();
          return false;
        }
        if(form.phone.value == "") {
          alert("Error: Phone cannot be blank!");
          form.phone.focus();
          return false;
        }
        if(form.phone.value.length != 10) {
          alert("Error: Phone should be 10 simbols!");
          form.phone.focus();
          return false;
        }
        if(isNaN(form.phone.value)) {
          alert("Error: Phone should be a number!");
          form.phone.focus();
          return false;
        }

        var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
        if(!(form.email.value.match(mailformat)))
        {
        alert("You have entered an invalid email address!");
        form.email.focus();
        return false;
        }
      }
    </script>
</body>
</html>