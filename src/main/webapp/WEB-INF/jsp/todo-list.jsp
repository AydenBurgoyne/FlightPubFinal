<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ToDo Items</title>
</head>
<body>
<h1> bbb </h1>
<a href="${pageContext.request.contextPath}/todo/create"> Create New ToDoItem</a>

<table>
    <tr>
        <th>Item Name</th>
        <th>Description</th>
        <th>Done?</th>
        <th>Actions</th>
    </tr>
    <c:forEach items="${items}" var="item">
        <tr>
            <td>${item.name}</td>
            <td>${item.desc}</td>
            <td>${item.done}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
