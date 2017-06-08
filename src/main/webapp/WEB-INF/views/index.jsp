<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>
    <body>
        <a href="${spring:mvcUrl('registrationUser').build()}">Registration</a>
        <a href="${spring:mvcUrl('loginUser').build()}">Login</a>
    </body>
</html>
