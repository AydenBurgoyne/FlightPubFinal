<%--
  Created by IntelliJ IDEA.
  User: SixLiner
  Date: 22/08/2020
  Time: 11:41 am
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Enroll into Group</title>
    <%@include file="../layout/header.jsp"%>

</head>
<body>
    <div class="container">
        <form action="/GroupBooking/processEnrolment/" method="post" id="form">
            <h3>Fill In details below to enrol into group booking</h3>
            <br>
            <table class="table table-bordered">
                <tr>
                    <td>First Name:</td>
                    <td>
                        <input class="form-control" type="text" name="firstName" required>
                    </td>
                </tr>
                <tr>
                    <td>Last name:</td>
                    <td>
                        <input class="form-control" type="text" name="lastName" required>
                    </td>
                </tr>
                <tr>
                    <td>Email:</td>
                    <td>
                        <input class="form-control" type="email" name="email" required>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input class="btn btn-info" id="submit" type="submit" value="submit">
                    </td>
                </tr>
            </table>
        </form>

        <c:if test="${error != null}">
            <h1>Group Booking not found, please use different link.</h1>
            <script>$("#form").hide();</script>
        </c:if>
    </div>
</body>


<script>

    function generateNotification(context, message) {
        $(context).after(
            "<div class='alert alert-info errors'>" +
            message +
            "</div>"
        );

        $("#submit").removeClass("btn-warning").addClass("btn-info");
        $("#submit").val("Submit");
        $("#submit").attr("disabled", false);
    }

    //Creates a error next to dom element provided
    function generateError(context, errorDescription) {
        $(context).after(
            "<div class='alert alert-danger errors'>" +
            "  <strong>Error!</strong> " + errorDescription +
            "</div>"
        );

        $("#submit").removeClass("btn-warning").addClass("btn-info");
        $("#submit").val("Submit");
        $("#submit").attr("disabled", false);
    }
    
    $("#flightForm").submit(function (event) {
        event.preventDefault(); //prevent default action

        var post_url = $(this).attr("action"); //get form action url
        var request_method = $(this).attr("method"); //get form GET/POST method
        var form_data = $(this).serialize(); //Encode form elements for submission

        $("#submit").addClass("btn-warning").removeClass("btn-info");
        $("#submit").val("Processing");
        $("#submit").attr("disabled", false);

        //Submit data
        $.ajax({
            url: post_url,
            type: request_method,
            data: form_data,
            error: function (xhr, ajaxOptions, thrownError) {
                generateError($("#submit"), "Server error");
                return;
            }
        }).done(function (response) {

            if (response !== "Success") {
                generateNotification($("#submit"),response);
            }

            //Direct users to choose their seats in the flight
            window.location.href = "/groupBooking/SeatSelection";
        });
    });
</script>

<%@include file="../layout/footer.jsp"%>

</html>
