<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <jsp:include page="layout/header.jsp"/>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src ="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script type="text/javascript">
        $(function(){
            $("#Airport").autocomplete({
                source: "/search/AirportAutoComplete"
            });
        });
    </script>
</head>
<body class="text-center">

<div class="container">
    <h1>Restricted Airport</h1>


    <form:form action="/RestrictedAirports/ValidateRestrictedAirports" method="post" modelAttribute="RestrictedAirport">

        <table class="table table-bordered">
            <tr>
                <td>Airport Name:</td>
                <td>
                    <form:input class="form-control" path="Airport"/>
                    <form:errors path="Airport" cssStyle="color: #ff0000;"/>
                </td>
            </tr>
            <tr>
                <td>Starting Date of Restrictions:</td>
                <td>
                    <form:input type="date" class="form-control" path="TimeFrom"/> <br/>
                    <form:errors path="TimeFrom" cssStyle="color: #ff0000;"/>
                </td>
            </tr>
            <tr>
                <td>Ending Date of the Restrictions</td>
                <td>
                    <form:input type="date" class="form-control" path="TimeTo"/> <br/>
                    <form:errors path="TimeTo" cssStyle="color: #ff0000;"/>
                </td>
            </tr>

            <tr>
                <td>
                    <input type="submit" class="btn btn-info" value="Submit"/>
                </td>
            </tr>
        </table>
        <br/>
    </form:form>
</div>
</body>
<jsp:include page="layout/footer.jsp"/>
</html>
