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
        function deleteUser(Email) {
                window.location.href= "${pageContext.request.contextPath}/UserManagement/DeleteUser?AccountID="+Email;
        }
        function ViewAccount(Email) {
            window.location.href= "${pageContext.request.contextPath}/UserManagement/ViewAccountAdmin?AccountID="+Email;
        }
        function ReactiveUser(Email) {
            window.location.href= "${pageContext.request.contextPath}/UserManagement/Activate?AccountID="+Email;
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
    <title>Account Management</title>
</head>
<body>
    <div class="container">
        <h3>Account List</h3>
        <br>
        <ul class="myList">
            <c:forEach items="${ActiveAccounts}" var="User" varStatus="status">
                <c:if test="${activeUser!=null}">
                <c:if test="${!activeUser.email.equals(User.email)}">

                <li class="myListItem">
                    User:
                    ${User.firstName}
                    <br>
                    Email:
                    ${User.email}
                    <br>
                    <c:if test="${User.status.equals('active')}">
                        <button type="button" id="book" onclick="deleteUser('${User.email}')">Delete</button>
                        <button type="button" id="book" onclick="ViewAccount('${User.email}')">View</button>
                    </c:if>
                    <c:if test="${User.status.equals('inactive')}">
                        <button type="button" id="book" onclick="ReactiveUser('${User.email}')">Reactivate</button>


                    </c:if>


                </c:if>
                </c:if>
                <c:if test="${activeUser==null}">
                <li class="myListItem">
                    User:
                    ${User.firstName}
                    <br>
                    Email:
                    ${User.email}
                    <br>
                    <c:if test="${User.status.equals('active')}">
                        <button type="button" id="book" onclick="deleteUser('${User.email}')">Delete</button>
                        <button type="button" id="book" onclick="ViewAccount('${User.email}')">View</button>
                    </c:if>
                    <c:if test="${User.status.equals('inactive')}">
                        <button type="button" id="book" onclick="ReactiveUser('${User.email}')">Reactivate</button>

                    </c:if>
                    </c:if>


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
