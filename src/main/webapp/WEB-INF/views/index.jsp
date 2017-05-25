<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>
<body>
    <a href="${spring:mvcUrl('registrationUser')}">Registration</a>
    <a href="${spring:mvcUrl('loginUser')}">Login</a>
</body>
</html>
