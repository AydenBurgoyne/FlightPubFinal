<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Booking Confirmation Page</title>
    <jsp:include page="layout/header.jsp"/>
</head>
<body>

<div class="container">
    <h3>Confirmation Page</h3>

    <table class="table table-striped">
        <tr>
            <td>AccountInfo:</td>
        </tr>
        <c:forEach items="${GroupBooking.bookingsList}" var="booking" varStatus="status">
            <c:if test="${GroupBooking.accountLeader.email.equals(booking.accountID.email)}">
                <tr>
                    <td>AccountLeader:</td>
                    <td>${GroupBooking.accountID.email}</td>
                </tr>
            </c:if>
            <c:if test="${!GroupBooking.accountLeader.email.equals(booking.accountID.email)}">
            <tr>
                <td>Group Member:${status.index+1}</td>
                <td>${booking.accountID.email}</td>
            </tr>
            </c:if>
        </c:forEach>
        <tr>
            <th>Flight Number</th>
            <th>Departure</th>
            <th>Destination</th>
        </tr>
        <c:forEach items="${GroupBooking.bookingsList.get(0).journeyID.legsOfJourney}" var="leg">
            <tr>
                <td>${leg.flightID.flightNumber}</td>
                <td>${leg.flightID.departureCode.airport}</td>
                <td>${leg.flightID.destinationCode.airport}</td>
            </tr>
        </c:forEach>
        <c:if test="${GroupBooking.hotelBooking!=null}">
            <tr>
                <td>Hotel Information:</td>
                <td>Start Date:${GroupBooking.hotelBooking.startDate}</td>
                <td>End Date:${GroupBooking.hotelBooking.endDate}</td>
                <br>
                <td>Location:${GroupBooking.hotelBooking.hotelEntityID.location.airport}</td>
                <td>Number of people in booking:${GroupBooking.hotelBooking.people}</td>
            </tr>

        </c:if>
        <c:if test="${reserved=='t'}">
            <p class="lead" style="text-align: center">
                An email has been sent to each passenger who has had a seat reserved for them. <br>
                They will have two weeks (or less if the flight is sooner than 4 weeks away <br>
                i.e. the confirmation must be made at least two weeks before the flight) to confirm their seat via the
                link in the email.
            </p>
            <a href="${pageContext.request.contextPath}/Journey/confirmIndividualSeat">Seat Confirmation</a>
        </c:if>

    </table>
</div>
</body>
<jsp:include page="layout/footer.jsp"/>
</html>
