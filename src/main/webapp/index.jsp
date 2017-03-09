<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div tiles:fragment="content">
    <form name="f" action="login" method="post">
        <fieldset>
            <legend>Please Login</legend>
            <div class="alert alert-error">
                <c:if test="${not empty param.login}"> 账户或者密码错误！</c:if>
            </div>
            <div class="alert alert-success">
                <c:if test="${not empty param.logout}"> 成功登出</c:if>
            </div>
            <label for="username">Username</label>
            <input type="text" id="username" name="username"/>
            <label for="password">Password</label>
            <input type="password" id="password" name="password"/>
            <div class="form-actions">
                <button type="submit" class="btn">Log in</button>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </div>
        </fieldset>
    </form>
</div>
</body>
</html>