<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Account Details</title>
    <jsp:include page="layout/header.jsp"/>
    <script>
        function JourneyView(Journey) {
            window.location.href= "${pageContext.request.contextPath}/Account/ViewJourney/?JourneyID="+Journey;
        }
        function GroupBooking(BookingID){
            window.location.href="${pageContext.request.contextPath}/Account/ViewGroupBooking/?GroupBookingID="+BookingID
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
</head>
<body>
<div class="container">
<form:form action="/Account/EditAccount" method="post" modelAttribute="AccountEntity">

    <table class="table table-bordered">
    <tr>
    <td>First Name:</td>
    <td>
    <form:input class="form-control" value ="${account.firstName}" path="firstName"/>
    <form:errors path="firstName" cssStyle="color: #ff0000;"/>
    </td>
    </tr>
    <tr>
    <td>Last Name:</td>
    <td>
    <form:input class="form-control" value = "${account.lastName}" path="lastName"/> <br/>
    <form:errors path="lastName" cssStyle="color: #ff0000;"/>
    </td>
    </tr>
    <tr>
    <td>Email Address:</td>
    <td>
    <form:input class="form-control" value ="${account.email}" path="email"/> <br/>
    <form:errors path="email" cssStyle="color: #ff0000;"/>
    </td>
    </tr>
    <tr>
    <td>Address:</td>
    <td>
    <form:input class="form-control" value = "${account.address}" path="address"/> <br/>
    <form:errors path="address" cssStyle="color: #ff0000;"/>
    </td>
    </tr>
    <tr>
    <td>Date of birth:</td>
    <td>

    <form:input id="dob" type="date" class="form-control" value ="${account.getDate()}" path="dateOfBirth"/> <br/>
    <form:errors path="dateOfBirth" cssStyle="color: #ff0000;"/>
    </td>
    </tr>
    <tr>
    <td>Business or recreational traveller?</td>
    <td>
    <form:select class="form-control" path="type">
        <c:if test="${account.type==true}">
        <form:option value="true" label="Business"/>
        </c:if>
        <c:if test="${account.type==false}">
            <form:option value="false" label="Recreational"/>
        </c:if>
    </form:select>
    <br>
    <form:errors path="type" cssStyle="color: #ff0000;"/>
    </td>
    </tr>
    <%--            <form:input class="form-control" path="dummyWishList" value="Standalone" cssStyle="display:none"/> <br/>--%>
    <%--            <form:errors path="dummyWishList" cssStyle="color: #ff0000;"/>--%>

    <form:password class="form-control" value="Password12" path="pword" cssStyle="display:none"/> <br/>
    <form:errors path="pword" cssStyle="color: #ff0000;"/><br>
    <tr>
    <td>
    <input type="submit" class="btn btn-info" value="Submit"/>
    </td>
    </tr>
  </form:form>

    </table>

    <%--        <div class="toast" id="myToast" role="alert" aria-live="assertive" aria-atomic="true">--%>
    <%--            <div class="toast-header">--%>
    <%--                <strong class="mr-auto">Account changes saved</strong>--%>
    <%--                <button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close">--%>
    <%--                    <span aria-hidden="true">&times;</span>--%>
    <%--                </button>--%>
    <%--            </div>--%>
    <%--            <div class="toast-body">--%>
    <%--               Message--%>
    <%--            </div>--%>
    <%--        </div>--%>
    <%--    </div>--%>

    <h4 style="margin-left: 500px">

    </h4>
    <ul class="myList">
        <c:forEach items="${history}" var="bookings" varStatus="status">
            <li class="myListItem">
                <h3>Journey:</h3>
                    ${bookings.journeyID.legsOfJourney.get(0).flightID.departureCode.airport}
                ----->
                    ${bookings.journeyID.legsOfJourney.get(bookings.journeyID.legsOfJourney.size()-1).flightID.destinationCode.airport}
                <button type="button" id="book" onclick="JourneyView('${bookings.journeyID.journeyID}')">View</button>
            </li>
        </c:forEach>
        <c:forEach items="${GroupBookingList}" var="GroupBooking" varStatus="status">
            <li class="myListItem">
                <h3>GroupBooking:</h3>
                    ${GroupBooking.destinationStart.airport}
                ----->
                    ${GroupBooking.destinationEnd.airport}
                <button type="button" id="book" onclick="GroupBooking('${GroupBooking.groupID}')">View</button>

            </li>
        </c:forEach>
   </ul>
</div>
<%--Example of old my list--%>
<%--<ul class="myList">--%>
<%--    <li class="myListItem">--%>
<%--        Melbourne > Sydney, 04-08-19--%>
<%--    </li>--%>
<%--    <li class="myListItem">--%>
<%--        Melbourne > Sydney, 09-08-19--%>
<%--    </li>--%>
<%--    <li class="myListItem">--%>
<%--        Melbourne > Sydney, 14-08-19--%>
<%--    </li>--%>
<%--    <li class="myListItem">--%>
<%--        Melbourne > Sydney, 19-08-19--%>
<%--    </li>--%>
<%--    <li class="myListItem">--%>
<%--        Amsterdam > Rome-Fiumicino, 03-09-19--%>
<%--    </li>--%>
<%--    <li class="myListItem">--%>
<%--        Melbourne > Sydney, 04-10-19--%>
<%--    </li>--%>
<%--    <li class="myListItem">--%>
<%--        Melbourne > Sydney, 09-08-19--%>
<%--    </li>--%>
<%--    <li class="myListItem">--%>
<%--        Melbourne > Sydney, 14-08-19--%>
<%--    </li>--%>
<%--    <li class="myListItem">--%>
<%--        Melbourne > Sydney, 20-08-19--%>
<%--    </li>--%>
<%--    <li class="myListItem">--%>
<%--        Melbourne > Sydney, 23-08-19--%>
<%--    </li>--%>
<%--    <li class="myListItem">--%>
<%--        Melbourne > Sydney, 25-08-19--%>
<%--    </li>--%>
<%--    <li class="myListItem">--%>
<%--        Melbourne > Sydney, 27-08-19--%>
<%--    </li>--%>
<%--    <li class="myListItem">--%>
<%--        Melbourne > Sydney, 29-09-19--%>
<%--    </li>--%>
<%--    <li class="myListItem">--%>
<%--        Melbourne > Sydney, 20-10-19--%>
<%--    </li>--%>
<%--</ul>--%>

</body>
<script>
    // $(document).ready(function(){
    //     $("#submit").on("click", function(){
    //         $("#myToast").toast({ delay: 3000 });
    //         $("#myToast").toast('show');
    //     });
    // });

    function myFunction() {
        alert("Account changes saved");
    }
</script>
<jsp:include page="layout/footer.jsp"/>
</html>
