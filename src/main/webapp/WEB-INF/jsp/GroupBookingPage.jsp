<%--@elvariable id="currentFlight" type="me.groupFour.data.FlightEntity"--%>
<%@ page import="me.groupFour.data.PlaneSeatingArrangementsEntity" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Book Flight</title>
    <jsp:include page="./layout/header.jsp"/>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js" integrity="sha384-LtrjvnR4Twt/qOuYxE721u19sVFLVSA4hf/rRt6PrZTmiPltdZcI7q7PXQBYTKyf" crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
    <h3>Flight Details</h3>

    <br>

    <h3>Booking information</h3>

    <table class="table">
        <tr>
            <td>Plane type:</td>
            <td id="planeCode">${currentFlight.planeCode.planeCode}</td>
        </tr>
        <tr>
            <td>Route</td>
            <td>
                <table class="table">
                    <tr>
                        <th>Departure</th>
                        <th>Arrival</th>
                    </tr>
                    <tr>
                        <td>
                            ${currentFlight.departureCode.airport} @ ${currentFlight.departureTime}
                        </td>
                        <td>
                            ${currentFlight.destinationCode.airport} @ ${currentFlight.arrivalTime}
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td>Pricing:</td>
<%--            TODO link pricing to group booking page--%>
            <td>$ ${price.price} per seat</td>
        </tr>
        <tr>
            <td>
                <c:if test="${isLast == true}">
                    <button class="btn btn-info" onclick="window.open('${pageContext.request.contextPath}/hotel-addon')">
                        Add Hotel
                    </button>
                </c:if>
            </td>
        </tr>
    </table>
<br>
    <h3>Please select a seat for this flight in your journey:</h3>

    <table class="table table-bordered" id="seatSelectionTable" style="width: 100%;height:60%; padding-left: 4em">
    </table>

    <br>
    <h3>Seats selected</h3>

    <table class="table"> .
        <tr id="seatTableHeader">
            <th>Location</th>
            <th>Option</th>
            <th>Remove</th>
        </tr>
        <tr id="selectionTemplate">
            <td>
                <input type="hidden">
                <p></p>
            </td>
            <td>
                <select class="form-control purchaseType">
                    <option value="Purchase">Purchase</option>
                    <option value="Reserve">Reserve</option>
                </select>
            </td>
            <td>
                <button class="btn btn-danger removeSeatSelection">X</button>
            </td>
        </tr>

    </table>
    <br>
    <br>
    <h3>Recipients</h3>

    <form action="${pageContext.request.contextPath}/Journey/BookingHandlingGroup" id="form" method="GET">
        <table class="table table-bordered" id="reservedRecipientList">
            <tr>
                <th>Email</th>
                <th>Allocated Seat</th>
                <th>Remove</th>
            </tr>
            <tr id="reservedRecipientTemplate">
                <td>
                    <input type="hidden" class="form-control" name="">
                    <p class="emailReserve"></p>
                </td>
                <td>
                    <select class="seatSelected form-control" name="">

                    </select>
                </td>
                <td>
                    <button class="btn btn-danger removeRecipient">X</button>
                </td>
            </tr>

            <c:if test="${isFirst}">
            <c:if test="${activeUser != null}">
            <tr style="display: table-row;" class="alert-warning">
                <td>
                    <input type="hidden" class="form-control" name="UserEmails" value="${activeUser.email}">
                    <p class="emailReserve">${activeUser.email}</p>
                </td>
                <td>
                    <select class="seatSelected form-control" name="seatID">

                    </select>
                </td>
                <td></td>
            </tr>
                </c:if>
            </c:if>

            <tr>
                <td colspan="2">
                    <input class="form-control" type="email" id="newReserveEmail" placeholder="Recipient email">
                </td>
                <td>
                    <button type="button" id="submitReserveEmail" class="btn btn-success">Add</button>
                </td>
            </tr>
            <tr>
                <td>
                    <input id="submit" type="submit" class="form-control btn btn-info" value="Book">
                </td>
            </tr>
        </table>
        <br>

    </form>

</div>
<p style="padding-bottom:2em"></p>
</body>

<style>
    .available {
        background-color: green;
    }

    .available:hover {
        background-color: yellow;
    }

    .excluded {
        background-color: white;
    }

    .blocked {
        background-color: red;
    }

    .selected {
        background-color: orange;
    }

    .reserved {
        background-color: blue;
    }

    .reserve-btn{
        margin-right: 15px;
    }
</style>

<script>

    function updateReserveEmailList(){
        //Update all
        $(".seatSelected").each( function (i, reservedType){
           let selected = $(this).find(":selected").text();

           $(this).empty()

            $(reservedType).append("<option></option>");

           $(".selected").each( function (){
               let newOption = $("<option></option>")

               $(newOption).text($(this).attr("id"));
               $(newOption).val($(this).attr("id"));

               if($(this).attr("id") === selected){
                   $(newOption).attr("selected",true);
               }

               $(reservedType).append(newOption);

           });

        });
    }

    //Creates a notification next to dom element provided
    function generateNotification(context, message) {
        $(context).after(
            "<div class='alert alert-info errors'>" +
            message +
            "</div>"
        );
    }

    function validateEmail(email) {
        const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return re.test(String(email).toLowerCase());
    }




    //Get flightid, ticket type, search
    $(document).ready(function () {

        <c:if test="${isFirst == true}">
            sessionStorage.removeItem("emails");
        </c:if>
        function addSeatRow(context,rowNumber,head){
            let seatConfiguration = ${Arrangement} //[3,5,3];

            let alphabet = ["A","B","C","D","E","F","G","H","I","J","K"];

            var configCounter;

            let alphabetCounter = 0;

            let newRow = $("<tr></tr>");

            if(head == true){
                $(newRow).append("<td></td>");
            }else{
                $(newRow).append("<td>"+ rowNumber +"</td>");
            }

            //Loop through each seat configuration
            for (configCounter = 0; configCounter < seatConfiguration.length; configCounter++) {
                console.log("TRIG 1")

                let currentNumberOfSeats = seatConfiguration[configCounter]

                console.log("currentNumberOfSeats "+ currentNumberOfSeats)
                let seatSegmentCounter = 0

                for (seatSegmentCounter = 0; seatSegmentCounter < currentNumberOfSeats; seatSegmentCounter++) {
                    console.log("TRIG 2")

                    if(head == true){
                        $(newRow).append("<th>"+ alphabet[alphabetCounter] +"</th>");
                    }else{
                        $(newRow).append("<td class='available' id='" + alphabet[alphabetCounter] + rowNumber +"'></td>");
                    }

                    alphabetCounter++
                }
                //Add a space column
                $(newRow).append("<td></td>");
            }

            $(context).append(newRow);
        }

        //Create alphabet at the top
        addSeatRow($("#seatSelectionTable"),-1,true)

        let startingNumber = ${offset}

        let numberOfRows = ${Rows};

        let status = 0

        for (status = 0; status < numberOfRows; status++) {
            addSeatRow($("#seatSelectionTable"),startingNumber+status,false)
        }





        //Check if web storage avaliable
        if (typeof(Storage) === "undefined") {
            alert("Web storage not supported in this browser")
            return;
        }

        if (window.sessionStorage.emails) {
            var emails = JSON.parse(window.sessionStorage.getItem("emails"));

            var i;
            for (i = 0; i < emails.length; i++) {

                //Add it to reserved recipients
                let recipientRow = $("#reservedRecipientTemplate")[0].outerHTML;

                let newRecipientRow = $(recipientRow);

                $(newRecipientRow).removeAttr("id")

                //Add email to row
                let tds = $(newRecipientRow).find("td");

                $(tds).eq(1).find("select").first().attr("name","seatID")

                <c:if test="${isFirst == false}">
                    $(tds).eq(2).find("button").first().remove()
                </c:if>

                console.log(tds);

                $(tds).eq(0).find("input").first().val(emails[i]).attr("name","UserEmails")
                $(tds).eq(0).find("p").first().text(emails[i])
                $(newRecipientRow).show();

                if($(".emailReserve").size() === 1 ){
                    $(newRecipientRow).addClass("alert-warning")
                    $("#reservedRecipientTemplate").after(newRecipientRow);
                }else{
                    $(".emailReserve").last().parent().parent().after(newRecipientRow);
                }
            }
        }

        $("#selectionTemplate").hide();
        $("#reservedRecipientTemplate").hide();
        $(document).on('change', '.reserveOption', function () {
            $("#hiddenValue").val("t");
        });

        $("#form").submit(function (event) {
            $(".errors").remove();

            console.log("RUNS")

            //Check if their any seats selected
            if($(".emailReserve").size() === 1){
                generateNotification($("#submit"),"Must select seats for all recipients")
                event.preventDefault();
                return;
            }

        let blankSeats = 0

            //Check if their a double seat selection
            $(".seatSelected").each( function (){

                if($(this).find(":selected").text() === "" ){
                    blankSeats++
                    console.log("ERROR 2")
                }
            });

            if(blankSeats !== 1){
                generateNotification($("#submit"),"Please select a seat");
                event.preventDefault();
                return
            }

            //Check if their first here

            <c:if test="${isFirst == true}">
            let emailStore = [];

            $(".seatSelected").each( function (){
                let email = $(this).parent().parent().find("td").eq(0).find("p").eq(0).text()
                if(email === ""){
                    return
                }
                emailStore.push(email)
            });

            sessionStorage.setItem("emails", JSON.stringify(emailStore));
            sessionStorage.setItem("bookingType","Group");
            </c:if>
            //End here in trip leg
        });



        //To exclude seats insert into array below
        let excludedSeats = <%= request.getAttribute("Excluded") %>;

        $.each(excludedSeats, function (index, val) {
            var upperCaseSeatCode = val.toUpperCase();

            $("#" + upperCaseSeatCode).removeClass();
            $("#" + upperCaseSeatCode).addClass("excluded");

        });

        //TODO link bookedlist to js
        let bookedSeats = ${BookedList};

        $.each(bookedSeats, function (index, val) {
            var upperCaseSeatCode = val.toUpperCase();

            $("#" + upperCaseSeatCode).removeClass();
            $("#" + upperCaseSeatCode).addClass("blocked");

        });


        $(document).on('change', '.seatSelected', function (index, type) {
            let seatSelected = $(this).find(":selected").text()

            let duplicationCount = 0

            $(".seatSelected").each( function (){
                if($(this).find(":selected").text() === ""){
                    return;
                }
               if(seatSelected === $(this).find(":selected").text()){
                   duplicationCount++;
               }
            });

            if(duplicationCount > 1){
                alert("Can't select this seat,seat already selected.")
                $(this).prop("selectedIndex", 0)
            }


        });

        $(document).on('click', '.removeRecipient', function () {
            console.log("TRIG")
            let tds = $(this).parent().parent().find("td")

            $(tds[1]).find("button").each(function (){
                let currentSeat = $(this).text()
                console.log("Remove seat "+ currentSeat)
                $(".purchaseType").each(function (){
                    let tdSeatLocation = $(this).parent().parent().find("td").eq(0).text()

                    console.log("SEAT "+ tdSeatLocation)
                    if(currentSeat === tdSeatLocation){
                        console.log("SET " +$(this).html())
                        $(this).prop('selectedIndex',0);
                        $(this).change()
                        return false;
                    }
                });
            })

            $(this).parent().parent().empty().remove();
        });

        //Triggers when user changes the payment option
        $(document).on('change', '.purchaseType', function () {
            let tds = $(this).parent().parent().find("td")
            let optionsTD = $(tds[2]).find("select")

            let seatSelectedId = $(tds[0]).text();

            console.log(seatSelectedId)

            if($(this).find(":selected").text() === "Purchase"){
                $("#"+seatSelectedId).removeClass("reserved").addClass("selected")
            }else{
                $("#"+seatSelectedId).removeClass("selected").addClass("reserved")
                updateReserveEmailList()
            }

        });

        //When end user add a new reserve email
        $(document).on("click", "#submitReserveEmail", function () {
            var email = $("#newReserveEmail").val().toLowerCase()

            //Clear errors
            $(".errors").remove();

            if(email === ""){
                generateNotification($("#newReserveEmail"),"Field is blank")
                return;
            }

            //Check if legit email
            if(validateEmail(email) == false){
                generateNotification($("#newReserveEmail"),"Invalid email address")
                return;
            }

            //Check if email already exists
            $(".emailReserve").each(function (index, value){

                if($(this).text() === $("#newReserveEmail").val().toLowerCase()){
                    generateNotification($("#newReserveEmail"),"email address already exists")
                    return false;
                }
            });

            //Add it to reserved recipients
            let recipientRow = $("#reservedRecipientTemplate")[0].outerHTML;

            let newRecipientRow = $(recipientRow);

            $(newRecipientRow).removeAttr("id")

            //Add email to row
            let tds = $(newRecipientRow).find("td");

            $(tds).eq(1).find("select").first().attr("name","seatID")

            console.log(tds);

            $(tds).eq(0).find("input").first().val(email).attr("name","UserEmails")
            $(tds).eq(0).find("p").first().text(email)
            $(newRecipientRow).show();

            if($(".emailReserve").size() === 1 ){
                $(newRecipientRow).addClass("alert-warning")
                $("#reservedRecipientTemplate").after(newRecipientRow);
            }else{
                $(".emailReserve").last().parent().parent().after(newRecipientRow);
            }

            $("#newReserveEmail").val("")
            //Update all reserved form selections
            updateReserveEmailList();

        });

        //Triggers when clicking on all available seats
        $(document).on("click", ".available", function () {
            //Seat selected
            let seatSelection = $(this).attr("id");

            //Sets seatId in the form when user clicks available seat
            $("#seatSelection").val(seatSelection).text(seatSelection);

            $(this).removeClass("available");
            $(this).addClass("selected");

            //Get selection template
            let selectionHtml = $("#selectionTemplate")[0].outerHTML;

            //creates a new dom element and copy template above in it
            let newSeatSelection = $(selectionHtml)

            $(newSeatSelection).removeAttr("id");

            let seatSelectionTD = $(newSeatSelection).find("td")

            //$(seatSelectionTD[0]).text(seatSelection) //Set seat location to first td

            $(seatSelectionTD[0]).find("input").first().val(seatSelection).attr("name","seatID")
            $(seatSelectionTD[2]).find("select").first().attr("name","UserEmails")
            $(seatSelectionTD[1]).find("select").first().attr("name","purchaseType")

            $(seatSelectionTD[0]).find("p").first().text(seatSelection)

            //Hide reserve list
            // console.log($(seatSelectionTD[2]).find("select"))
            let reserveDropDown = $(seatSelectionTD[2]).find("select")[1];
            $(reserveDropDown).hide()

            $(seatSelectionTD[2]).find("button").first().attr("seatId",seatSelection)


           $(newSeatSelection).show();

            updateReserveEmailList();

            $("#seatTableHeader").after(newSeatSelection);
        })

        $(document).on("click", ".removeSeatSelection", function () {
            let seatID = $(this).attr("seatId");
            console.log("seatID: "+ seatID)

            //Updates seats table
            $("#" + seatID).removeAttr("class");
            $("#" + seatID).addClass("available");

            $(".reserve-btn").each( function (){
                if(seatID === $(this).text()){
                    $(this).remove()
                }
            });

            //Remove out of selected seats
            $(this).parent().parent().empty().remove();

            updateReserveEmailList()

        })
    })
</script>

<jsp:include page="./layout/footer.jsp"/>

</html>
