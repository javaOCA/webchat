<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Registration page</title>
    <link rel="stylesheet" media="screen" href="./static/css/style.css"/>
</head>
<body>

    <sf:form modelAttribute="user" action="${formHandler}" cssClass="reg-form">
        <h1><s:message code="auth.title"></s:message></h1>
        <hr>
        <div class="form-row">
            <label for="name"><s:message code="auth.name"></s:message></label>
            <sf:input path="name" required="required"/>
        </div>
        <div class="form-row">
            <sf:errors path="name" cssClass="error"/>
        </div>
        <div class="form-row">
            <label for="login"><s:message code="auth.login"></s:message></label>
            <sf:input path="login" required="required"/>
        </div>
        <div class="form-row">
            <sf:errors path="login" cssClass="error"/>
        </div>
        <div class="form-row">
            <label for="password"><s:message code="auth.password"></s:message></label>
            <sf:password path="password" required="required"/>
        </div>
        <div class="form-row">
            <sf:errors path="password" cssClass="error"/>
        </div>
        <hr>
        <div class="form-row">
            <button type="submit" class="btn_submit"><s:message code="auth.submit"/></button>
        </div>
    </sf:form>

</body>
</html>
