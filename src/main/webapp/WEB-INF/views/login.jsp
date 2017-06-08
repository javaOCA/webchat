<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login page</title>
    <link rel="stylesheet" media="screen" href="./static/css/style.css"/>
</head>
<body>
<%--
    <h1>Welcome to login page</h1>
    ${error}
    <form action="$loginHandler" method="post" cssClass="reg-form">
        Login: <input type="text" name="login" placeholder="Enter login here"/><br>
        Password: <input type="password" name="password" placeholder="Enter password here"/><br>
        <input type="submit" value="Send"/>
    </form>
--%>
    <sf:form modelAttribute="user" action="${formHandler}" cssClass="reg-form" method="post">
        <h1><s:message code="login.title"></s:message></h1>
        <hr>
        <div class="form-row">
            <label for="login"><s:message code="auth.login"></s:message></label>
            <sf:input path="login" required="required"/>
        </div>
        <div class="form-row">
            <label for="password"><s:message code="auth.password"></s:message></label>
            <sf:password path="password" required="required"/>
        </div>
        <hr>
        <div class="form-row">
            <button type="submit" class="btn_submit"><s:message code="auth.submit"/></button>
            <a href="./registration" class="a-reg"><s:message code="auth.title"></s:message></a>
        </div>
    </sf:form>
    <div class="form-error">${error}</div>
</body>
</html>
