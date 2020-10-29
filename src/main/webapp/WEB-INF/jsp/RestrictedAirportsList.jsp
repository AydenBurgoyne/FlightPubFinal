<%--
  Created by IntelliJ IDEA.
  User: ayden
  Date: 10/09/2020
  Time: 2:06 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script>
        function deleteRestriction(RestrictionID) {
                window.location.href= "${pageContext.request.contextPath}/RestrictedAirports/deleteRestricted?RestrictionID=" + RestrictionID
        }
    </script>
    <style>

        .myList {
            width: 40%;
            height: 40%;
            overflow: scroll;
            margin: 25px auto 80px;

            padding: 0;

            border: 2px solid #ccc;

            font-size: 16px;
            font-family: Arial, sans-serif;

            -webkit-overflow-scrolling: touch;
        }

        * {
            box-sizing: border-box;
        }

        .myListItem {
            padding: 10px 20px;

            border-bottom: 1px solid #ccc;

        }

        .myListItem:last-child {
            border-bottom: none;
        }

        .myListItem:nth-child(even) {
            background: #f8f8f8;
        }


    </style>
    <jsp:include page="layout/header.jsp"/>
    <title>Restricted Airports</title>
</head>
<body>
    <div class="container">
        <h3>Restricted Airports List:</h3>
        <br>
        <ul class="myList">
            <c:forEach items="${RestrictedList}" var="Restriction" varStatus="status">
                <li class="myListItem">
                        Airport:
                        ${Restriction.destinationCode.airport}
                    <br>
                        Restrictions Start:
                        ${Restriction.timefromTimestamp}
                    <br>
                        Restrictions End:
                        ${Restriction.timetoTimestamp}
                    <button type="button" id="book" onclick="deleteRestriction(${Restriction.restrictionID})">Remove</button>

                </li>

                <%--prints out the legs don't need in this section. Just want the overview for the journey.--%>
                <%--           <c:forEach items="${bookings.journeyID.legsOfJourney}" var="legs">--%>
                <%--               <li>--%>
                <%--               ${legs.flightID.departureCode.airport}--%>
                <%--                   ---->--%>
                <%--               ${legs.flightID.destinationCode.airport}--%>
                <%--               </li>--%>
                <%--           </c:forEach>--%>
            </c:forEach>
        </ul>
    </div>
</body>
</html>
