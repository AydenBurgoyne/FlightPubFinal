<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>Error Page</title>

    <%@include file="/WEB-INF/jsp/layout/header.jsp"%>

    <style>
        #centre{
            margin: auto;
            text-align: center;
            width: 50%;
            padding: 10em;
        }
    </style>
</head>
<body>

<div id="centre">
    <h1>Something went wrong! </h1>
    <h2>Our Engineers are on it</h2>
    <button class="btn btn-info" onclick="window.location.href = '${pageContext.request.contextPath}/'">Go Home</button>
</div>


</body>
<%@include file="/WEB-INF/jsp/layout/footer.jsp"%>
</html>
