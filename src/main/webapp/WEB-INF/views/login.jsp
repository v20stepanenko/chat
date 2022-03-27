<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/login" id = "login"></form>
<h1>Login</h1>
<form action="/login" method="post">
    <input type="text" name="name" placeholder="Имя">
    <input type="submit">
</form>
<h3 style="color: red">${errorMsg}</h3>
</body>
</html>
