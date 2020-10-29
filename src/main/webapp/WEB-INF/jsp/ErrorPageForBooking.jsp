<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Journey View</title>
    <jsp:include page="layout/header.jsp"/>
    <script>
        function CancelBooking(BookingID) {
            window.location.href= "${pageContext.request.contextPath}/UserManagement/CancelBooking/?BookingID="+BookingID;
        }

    </script>
</head>

<body>
<H1>Sorry you have to be the groupLeader to delete this booking.</H1>

<style>
    .events li {
        display: flex;
    }

    .events time {
        position: relative;
        color: #ccc;
        padding: 0 1.5em;
    }

    .events time::after {
        content: "";
        position: absolute;
        z-index: 2;
        right: 0;
        top: 0;
        transform: translateX(50%);
        border-radius: 50%;
        background: #fff;
        border: 1px #cccccc solid;
        width: .8em;
        height: .8em;
    }


    .events span {
        padding: 0 1.5em 1.5em 1.5em;
        position: relative;
    }

    .events span::before {
        content: "";
        position: absolute;
        z-index: 1;
        left: 0;
        height: 100%;
        border-left: 1px #ccc solid;
    }

    .events strong {
        display: block;
        font-weight: bolder;
    }


    .events {
        margin: 1em;
        width: 50%;
    }

    .events,
    .events *::before,
    .events *::after {
        box-sizing: border-box;
        font-family: arial;
    }
</style>

</body>
<jsp:include page="layout/footer.jsp"/>
</html>