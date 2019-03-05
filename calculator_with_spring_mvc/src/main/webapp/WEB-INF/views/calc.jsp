<%--
  Created by IntelliJ IDEA.
  User: merenaas
  Date: 26.02.19
  Time: 19:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
<div class="form" style="background-image: url('/static/images/424566890c2bb6d0a2788debd66c3d3d.jpg');">
    <form method="post">
        <label for="first_arg">Введите первый аргумент</label>
        <input type="text" name="a" id="first_arg"/><br>
        <label for="second_arg">Введите второй аргумент</label>
        <input type="text" name="b" id="second_arg"/><br>
        <select name="op">
            <option>+</option>
            <option>-</option>
            <option>*</option>
            <option>/</option>
        </select>
        <input type="submit" value="посчитать"/>
        <h1>Результат: ${res}</h1>
    </form>
    <p style="color: #4733d0; font-size: 20px;">${error}</p>
</div>
</body>
</html>
