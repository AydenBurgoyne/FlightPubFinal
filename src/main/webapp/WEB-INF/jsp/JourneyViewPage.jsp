<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Journey View</title>
    <jsp:include page="layout/header.jsp"/>
</head>
<body>
<%--https://stackoverflow.com/questions/50353797/vertical-progress-steps-with-css--%>
<div class="container" style="padding-bottom: 4em;">
    <h2>Journey View</h2> <br>

    <h3>Events</h3>
    <br>
    <ul class="events">
        <c:forEach items="${Flights}" var="Leg" varStatus="i">
            <li style="background-color:
            <c:if test="${i.index == 0}">red </c:if>
            <c:if test="${i.index == Flights.size()}">black </c:if>;"/>
            <time><fmt:formatDate type="date" value="${Leg.timeStampToDate(Leg.departureTime)}" pattern="HH:mm"/></time>
            <span><strong>${Leg.departureCode.airport}</strong> Depart on <fmt:formatDate type="date"
                                                                                          value="${Leg.timeStampToDate(Leg.departureTime)}"
                                                                                          pattern="dd/MM/yyyy"/></span>
            </li>
            <li>
                <time><fmt:formatDate type="date" value="${Leg.timeStampToDate(Leg.arrivalTime)}"
                                      pattern="HH:mm"/></time>
                <span><strong>${Leg.destinationCode.airport}</strong> Arrive on <fmt:formatDate type="date"
                                                                                                value="${Leg.timeStampToDate(Leg.arrivalTime)}"
                                                                                                pattern="dd/MM/yyyy"/></span>
            </li>
        </c:forEach>
    </ul>
    <hr>

    <H2>Smart traveller Advice:</H2>
    <p>The average smart traveller suggested threat level for this journey is 3/10 </p>
    <p>For more information click the <a href="https://www.smartraveller.gov.au/">SmartTraveller</a> Link </p>

    <h3>Airlines Reviews</h3>
    <table class="table table-bordered">
        <tr>
            <th>Airline</th>
            <th>Comment</th>
            <th>Rating (out of 5)</th>
        </tr>

        <c:set var="averageAirlineRating" value="0" />

        <c:forEach items="${Flights}" var="Leg" varStatus="loop">
            <c:set var="averageAirlineRating" value="${ averageAirlineRating + Leg.airlineCode.airlineRating }" />
            <tr>
                <td>${Leg.airlineCode.airlineName}</td>
                <td>${flightReviews.get(loop.index).review}</td>
                <td>${Leg.airlineCode.airlineRating}</td>
            </tr>
        </c:forEach>

        <c:if test="${averageAirlineRating != 0}">
            <c:set var="averageAirlineRating" value="${Math.floor(averageAirlineRating / Flights.size())}"/>
        </c:if>
        <tr>
            <th colspan="2">Average rating:</th>
            <td>${averageAirlineRating}</td>
        </tr>
    </table>

    <h3>Airport Reviews</h3>
    <table class="table table-bordered">
        <tr>
            <th>Airport</th>
            <th>Comment</th>
            <th>Rating (out of 5)</th>
        </tr>

        <c:set var="averageAirportRating" value="0" />

        <c:forEach items="${Flights}" var="Leg" varStatus="loop">
            <c:set var="averageAirportRating" value="${ averageAirportRating + Leg.destinationCode.airportRating }" />
            <tr>
                <td>${Leg.destinationCode.airport}</td>
                <td>${airportReviews.get(loop.index).review}</td>
                <td>${Leg.destinationCode.airportRating}</td>
            </tr>
        </c:forEach>

        <c:if test="${averageAirportRating != 0}">
            <c:set var="averageAirportRating" value="${Math.floor(averageAirportRating / Flights.size())}"/>
        </c:if>
        <tr>
            <th colspan="2">Average rating:</th>
            <td>${averageAirportRating}</td>
        </tr>
    </table>

    <p class="lead">Overall trip rating is: ${Math.floor((averageAirportRating + averageAirlineRating) / 2) }</p>

    <h3>Book this Journey</h3>
    <div class="row">
        <div class="col-sm-3">
            <form action="${pageContext.request.contextPath}/Journey/newGroupBooking" method="get">
                <input type="hidden" value="${FlightID}" name="flightIds"/>
                <input type="hidden" value="${ticket.ticketCode}" name="ticketType"/>
                <input type="hidden" value="${classType.classCode}" name="classCode"/>

                <input type="submit" class="btn btn-info" value="Start Group Booking"/>
            </form>
        </div>
        <div class="col-sm-6">
            <form action="${pageContext.request.contextPath}/Journey/newIndividualBooking" method="get">
                <input type="hidden" value="${FlightID}" name="flightIds"/>
                <input type="hidden" value="${ticket.ticketCode}" name="ticketType"/>
                <input type="hidden" value="${classType.classCode}" name="classCode"/>

                <input type="submit" class="btn btn-info" value="Start Individual Booking"/>
            </form>
        </div>
    </div>
</div>

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