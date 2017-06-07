<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>
    <head>
        <link rel="stylesheet" media="screen" href="./static/css/style.css"/>
    </head>
    <body>
        <a href="${spring:mvcUrl('registrationUser').build()}">Registration</a>
        <a href="${spring:mvcUrl('loginUser').build()}">Login</a>
        <br>
        ${error}
    </body>
</html>
