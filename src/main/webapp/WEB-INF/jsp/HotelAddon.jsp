<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Hotel Addon</title>

    <%@include file="./layout/header.jsp" %>

    <style>
        #centre {
            margin: auto;
            text-align: center;
            width: 50%;
            padding: 10em;
        }
    </style>

</head>
<body>


<c:choose>
    <%--@elvariable id="hotelBooked" type="bool"--%>
    <c:when test="${hotelBooked == true}">

        <div id="centre">
            <h1>You have already booked a hotel for this session</h1>
            <h2>Click on the button below to continue your booking</h2>
            <button class="btn btn-info" onclick="window.close()">Close tab</button>
        </div>

    </c:when>
    <c:otherwise>
        <div class="container">
            <table class="table table-bordered">
                    <%--@elvariable id="HotelBookingService" type="me.groupFour.data.HotelBookingService"--%>
                <form:form action="${pageContext.request.contextPath}/hotel-addon/show" method="get"
                           modelAttribute="HotelBookingService">
                <tr>
                    <td>
                        Location
                        <span>
    <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-geo-alt" fill="currentColor"
         xmlns="http://www.w3.org/2000/svg">
    <path fill-rule="evenodd"
          d="M12.166 8.94C12.696 7.867 13 6.862 13 6A5 5 0 0 0 3 6c0 .862.305 1.867.834 2.94.524 1.062 1.234 2.12 1.96 3.07A31.481 31.481 0 0 0 8 14.58l.208-.22a31.493 31.493 0 0 0 1.998-2.35c.726-.95 1.436-2.008 1.96-3.07zM8 16s6-5.686 6-10A6 6 0 0 0 2 6c0 4.314 6 10 6 10z"></path>
    <path fill-rule="evenodd"
          d="M8 8a2 2 0 1 0 0-4 2 2 0 0 0 0 4zm0 1a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"></path>
    </svg>

    <form:input value="${lastFlight.destinationCode.airport}" readonly="true" cssStyle="margin-top: 2em;"
                class="form-control" path="location"/> <br>
    <form:errors cssStyle="color: red" path="location" cssClass="error"/>
    </span>
                    </td>
                    <td>

                        Check-in
                        <form:input class="form-control" type="date" path="startDate" value="${checkinTime}"/>
                        <form:errors cssStyle="color: red" path="startDate" cssClass="error"/> <br>

                        Check-out
                        <form:input class="form-control" type="date" path="endDate" value="${checkoutTime}"/>
                        <form:errors cssStyle="color: red" path="endDate" cssClass="error"/> <br>
                    </td>

                    <td>
                        People
                        <div class="input-group">
    <span class="input-group-btn">
    <button type="button" style="margin-top: 2em;" class="btn btn-danger btn-number" data-type="minus"
            data-field="quant">
    <svg width="1.8em" height="1.8em" viewBox="0 0 16 16" class="bi bi-person-dash"
         fill="currentColor" xmlns="http://www.w3.org/2000/svg">
    <path fill-rule="evenodd"
          d="M8 5a2 2 0 1 1-4 0 2 2 0 0 1 4 0zM6 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm6 5c0 1-1 1-1 1H1s-1 0-1-1 1-4 6-4 6 3 6 4zm-1-.004c-.001-.246-.154-.986-.832-1.664C9.516 10.68 8.289 10 6 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10zM11 7.5a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1h-4a.5.5 0 0 1-.5-.5z"></path>
    </svg>
    </button>
    </span>

                            <label>
                                <form:input cssStyle="margin-top: 2em;" type="text" id="peopleNumber" name="quant"
                                            cssClass="form-control input-number" value="2" min="1"
                                            max="10" path="people"/>

                            </label>
                            <span class="input-group-btn">
    <button type="button" style="margin-top: 2em;" class="btn btn-success btn-number"
            data-type="plus" data-field="quant">
    <svg width="1.8em" height="1.8em" viewBox="0 0 16 16" class="bi bi-person-plus"
         fill="currentColor" xmlns="http://www.w3.org/2000/svg">
    <path fill-rule="evenodd"
          d="M8 5a2 2 0 1 1-4 0 2 2 0 0 1 4 0zM6 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm6 5c0 1-1 1-1 1H1s-1 0-1-1 1-4 6-4 6 3 6 4zm-1-.004c-.001-.246-.154-.986-.832-1.664C9.516 10.68 8.289 10 6 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10zM13.5 5a.5.5 0 0 1 .5.5V7h1.5a.5.5 0 0 1 0 1H14v1.5a.5.5 0 0 1-1 0V8h-1.5a.5.5 0 0 1 0-1H13V5.5a.5.5 0 0 1 .5-.5z"></path>
    </svg>
    </button>
    </span>
                        </div>
                    </td>

                    <td rowspan="2">
                        <input type="submit" style="margin-top:2em;" class="form-control btn btn-info" value="Search">
                        <input type="submit" value="send it"/>
                    </td>
                </tr>


                    <%--<c:forEach items="${journeyList}" var="journey">--%>
                    <%--@elvariable id="hotelEntityList" type="me.groupFour.data.HotelEntity"--%>
                    <%--@elvariable id="activeUser" type="me.groupFour.data.AccountEntity"--%>

                <tr>

                </tr>


                <c:choose>
                <c:when test="${activeUser.type == true}">
                <tr>
                    <td>
                        Business Hotels
                    </td>
                </tr>
                <c:forEach items="${hotelEntityList}" var="hotel">

                <c:if test="${hotel.type == true}">
                <tr>
                    <td>
                            ${hotel.location.airport}<br>
                            ${hotel.hotelID}<br>
                            ${hotel.roomsAvailable}<br>
                        $${hotel.price}<br>
                            ${hotel.type}
                    </td>
                    <td>
                        <button type="button" name="book" onclick="bookHotel(${hotel.hotelID})">Book now!</button>
                    </td>
                </tr>
                </c:if>

                </c:forEach>


                <tr>
                    <td>
                        Recreational Hotels
                    </td>
                </tr>
                <c:forEach items="${hotelEntityList}" var="hotel">

                <c:if test="${hotel.type == false}">
                <tr>
                    <td>
                            ${hotel.location.airport}<br>
                            ${hotel.hotelID}<br>
                            ${hotel.roomsAvailable}<br>
                        $${hotel.price}<br>
                            ${hotel.type}
                    </td>
                    <td>
                        <button type="button" name="book" onclick="bookHotel(${hotel.hotelID})">Book now!</button>
                    </td>
                </tr>
                </c:if>


                </c:forEach>

                </c:when>


                <c:otherwise>

                    <%--@elvariable id="hotelEntityList" type="me.groupFour.data.HotelEntity"--%>
                <tr>
                    <td>
                        Recreational Hotels
                    </td>
                </tr>
                <c:forEach items="${hotelEntityList}" var="hotel">
                <c:if test="${hotel.type == false}">
                <tr>
                    <td>
                            ${hotel.location.airport}<br>
                            ${hotel.hotelID}<br>
                            ${hotel.roomsAvailable}<br>
                        $${hotel.price}<br>
                            ${hotel.type}
                    </td>
                    <td>
                        <button type="button" name="book" onclick="bookHotel(${hotel.hotelID})">Book now!</button>
                    </td>
                </tr>
                </c:if>
                </c:forEach>

                <tr>
                    <td>
                        Business Hotels
                    </td>
                </tr>
                <c:forEach items="${hotelEntityList}" var="hotel">
                <c:if test="${hotel.type == true}">
                <tr>
                    <td>
                            ${hotel.location.airport}<br>
                            ${hotel.hotelID}<br>
                            ${hotel.roomsAvailable}<br>
                        $${hotel.price}<br>
                            ${hotel.type}
                    </td>
                    <td>
                        <button type="button" name="book" onclick="bookHotel(${hotel.hotelID})">Book now!</button>
                    </td>
                </tr>
                </c:if>
                </c:forEach>

                </c:otherwise>
                </c:choose>
                    <%--hotel type refers to the user type, false == business and true == recreational
                        simply for recommendations of hotels per user type,
                        not to limit users to book only one type--%>
                </form:form>

        </div>

        <script>

            function bookHotel(hotelID) {
                if (confirm("Are you sure you want to book this hotel?")) {
                    window.location.href = "${pageContext.request.contextPath}/hotel-addon/confirm?hotelID=" + hotelID
                    alert("You have successfully booked this hotel")
                }
                window.close();
            }

            //plugin bootstrap minus and plus
            //http://jsfiddle.net/laelitenetwork/puJ6G/
            $('.btn').click(function (e) {
                e.preventDefault();

                const type = $(this).attr('data-type');
                const input = $("#peopleNumber");
                const currentVal = parseInt(input.val());

                if (!isNaN(currentVal)) {
                    if (type === 'minus') {

                        if (currentVal > input.attr('min')) {
                            input.val(currentVal - 1).change();
                        }
                        if (parseInt(input.val()) === input.attr('min')) {
                            $(this).attr('disabled', true);
                        }

                    } else if (type === 'plus') {

                        if (currentVal < input.attr('max')) {
                            input.val(currentVal + 1).change();
                        }
                        if (parseInt(input.val()) === input.attr('max')) {
                            $(this).attr('disabled', true);
                        }

                    }
                } else {
                    input.val(0);
                }
            });
            $('.input-number').focusin(function () {
                $(this).data('oldValue', $(this).val());
            });
            $('.input-number').change(function () {

                const minValue = parseInt($(this).attr('min'));
                const maxValue = parseInt($(this).attr('max'));
                const valueCurrent = parseInt($(this).val());

                name = $(this).attr('name');
                if (valueCurrent >= minValue) {
                    $(".btn-number[data-type='minus'][data-field='" + name + "']").removeAttr('disabled')
                } else {
                    alert('Sorry, the minimum value was reached');
                    $(this).val($(this).data('oldValue'));
                }
                if (valueCurrent <= maxValue) {
                    $(".btn-number[data-type='plus'][data-field='" + name + "']").removeAttr('disabled')
                } else {
                    alert('Sorry, the maximum value was reached');
                    $(this).val($(this).data('oldValue'));
                }


            });
            $(".input-number").keydown(function (e) {
                // Allow: backspace, delete, tab, escape, enter and .
                if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 190]) !== -1 ||
                    // Allow: Ctrl+A
                    (e.keyCode === 65 && e.ctrlKey === true) ||
                    // Allow: home, end, left, right
                    (e.keyCode >= 35 && e.keyCode <= 39)) {
                    // let it happen, don't do anything
                    return;
                }
                // Ensure that it is a number and stop the keypress
                if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
                    e.preventDefault();
                }
            });
        </script>
    </c:otherwise>
</c:choose>

</body>
<%@include file="./layout/footer.jsp" %>
</html>
