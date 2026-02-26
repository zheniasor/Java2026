<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.transmittingnumber.entity.PhoneEntry" %>
<html>
<body>

<%
    List<PhoneEntry> entries = (List<PhoneEntry>) request.getAttribute("entries");
%>

<h2>Телефонная книга</h2>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Фамилия</th>
    </tr>

    <%
        for (PhoneEntry entry : entries) {
    %>
    <tr>
        <td><%= entry.getId() %></td>
        <td><%= entry.getLastName() %></td>
    </tr>
    <%
        }
    %>

</table>

<br>
<a href="${pageContext.request.contextPath}/index.jsp">Добавить запись</a>

</body>
</html>