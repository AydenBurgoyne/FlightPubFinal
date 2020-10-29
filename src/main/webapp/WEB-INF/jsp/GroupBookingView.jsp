<<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Journey View</title>
    <jsp:include page="layout/header.jsp"/>
    <script>
        function CancelBooking(BookingID) {
            window.location.href= "${pageContext.request.contextPath}/UserManagement/CancelGroupBooking/?GroupBookingID="+BookingID;
        }

    </script>
</head>

<body>
<%--https://stackoverflow.com/questions/50353797/vertical-progress-steps-with-css--%>
<div class="container" style="padding-bottom: 4em;">
    <h2>Group Booking</h2> <br>
    <ul class="events">
        <%--        <h2>Departure Airport:</h2>--%>
        <%--        ${Journey.legsOfJourney.get(0).flightID.departureCode.airport}"--%>
            <c:forEach items="${GroupBooking.bookingsList}" var="booking" varStatus="status">
                <c:if test="${GroupBooking.accountLeader.email.equals(booking.accountID.email)}">
                        <h2>AccountLeader:</h2>
                        <h3>${GroupBooking.accountID.email}</h3>
                </c:if>
                <c:if test="${!GroupBooking.accountLeader.email.equals(booking.accountID.email)}">
                        <h2>Group Member:${status.index+1}</h2>
                        <h3>${booking.accountID.email}</h3>
                </c:if>
            </c:forEach>
        <c:forEach items="${Journey.legsOfJourney}" var="Leg" varStatus="i">
            <br>
            <h2>Leg ${i.index+1}:</h2>
            <h3>Departure Stopover Airport:</h3>
            <h4>${Leg.flightID.departureCode.airport}</h4>
            <h3>Destination Stopover Airport:</h3>
            <h4>${Leg.flightID.destinationCode.airport}</h4>
        </c:forEach>
            <c:if test="${GroupBooking.hotelBooking!=null}">
                <br>
                <H1>Hotel Information:</H1>
                <H2>Start Date:${GroupBooking.hotelBooking.startDate}</H2>
                <H2>End Date:${GroupBooking.hotelBooking.endDate}</H2>
                <br>
                <H2>Location:${GroupBooking.hotelBooking.hotelEntityID.location.airport}</H2>
                <H2>Number of people in booking:${GroupBooking.hotelBooking.people}</H2>

            </c:if>
        <%--        <h2>Destination Airport:</h2>--%>
        <%--        <h3>${Journey.legsOfJourney.get(0).flightID.destinationCode.airport}</h3>--%>
        <button type="button" id="book" onclick="CancelBooking('${GroupBooking.groupID}')">Cancel GroupBooking</button>

    </ul>
    <hr>



</body>
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

<jsp:include page="layout/footer.jsp"/>
</html>