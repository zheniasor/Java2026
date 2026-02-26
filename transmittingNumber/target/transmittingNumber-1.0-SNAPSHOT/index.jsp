<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Добавление в телефонную книгу</title>
</head>
<body>

<h1>Добавление записи в телефонную книгу</h1>

<form action="${pageContext.request.contextPath}/phonebook" method="post">
    <table>
        <tr>
            <td>Фамилия:</td>
            <td><input type="text" name="lastName" required></td>
        </tr>
        <tr>
            <td>Телефон:</td>
            <td><input type="text" name="phoneNumber" required></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Добавить"></td>
        </tr>
    </table>
</form>
</body>
</html>