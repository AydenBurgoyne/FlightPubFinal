<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <jsp:include page="layout/header.jsp"/>
</head>
<body class="text-center">

<div class="container">
    <h1>Guest Details</h1>


    <form:form action="/Account/ValidateAccCreationGuest" id="form" method="post" modelAttribute="AccountEntity">

        <table class="table table-bordered">
            <tr>
                <td>First Name:</td>
                <td>
                    <form:input class="form-control" path="firstName"/>
                    <form:errors path="firstName" cssStyle="color: #ff0000;"/>
                </td>
            </tr>
            <tr>
                <td>Last Name:</td>
                <td>
                    <form:input class="form-control" path="lastName"/> <br/>
                    <form:errors path="lastName" cssStyle="color: #ff0000;"/>
                </td>
            </tr>
            <tr>
                <td>Email Address:</td>
                <td>
                    <form:input class="form-control" path="email"/> <br/>
                    <form:errors id="email" path="email" cssStyle="color: #ff0000;"/>
                </td>
            </tr>
            <tr>
                <td>Address:</td>
                <td>
                    <form:input class="form-control" path="address"/> <br/>
                    <form:errors path="address" cssStyle="color: #ff0000;"/>
                </td>
            </tr>
            <tr>
                <td>Date of birth:</td>
                <td>
                    <form:input type="date" class="form-control" path="dateOfBirth"/> <br/>
                    <form:errors path="dateOfBirth" cssStyle="color: #ff0000;"/>
                </td>
            </tr>
            <tr>
                <td>Business or recreational traveller?</td>
                <td>
                    <form:select class="form-control" path="type">
                        <form:option value="true" label="Business"/>
                        <form:option value="false" label="Recreational"/>
                    </form:select>
                    <br>
                    <form:errors path="type" cssStyle="color: #ff0000;"/>
                </td>
            </tr>
<%--            <form:input class="form-control" path="dummyWishList" value="Standalone" cssStyle="display:none"/> <br/>--%>
<%--            <form:errors path="dummyWishList" cssStyle="color: #ff0000;"/>--%>


            <form:password class="form-control" value="Guest1" path="pword" cssStyle="display:none"/> <br/>
            <form:errors path="pword" cssStyle="color: #ff0000;"/><br>
            <tr>
                <td>
                    <input type="submit" class="btn btn-info" value="Submit"/>
                </td>
            </tr>
        </table>
        <br/>
    </form:form>
</div>
<script>
    $(document).ready( function () {
        if(sessionStorage.getItem("bookingType") === "Group"){
            var emails = JSON.parse(window.sessionStorage.getItem("emails"));
            $("#email").val(emails[0]).attr("disabled",true);
        }

        $("#form").submit(function (event) {
            $("#email").removeAttr("disabled")
        });
    });
</script>
</body>

<jsp:include page="layout/footer.jsp"/>
</html>