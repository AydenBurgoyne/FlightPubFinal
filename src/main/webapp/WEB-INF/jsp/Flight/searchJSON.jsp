<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: SixLiner
  Date: 14/08/2020
  Time: 5:22 pm
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <%@ page %>
    <title>Search Flights:</title>
</head>
<body>

<div class="container">
    <h3>Search Journey</h3>
    <table class="table table-bordered">
        <form id="flightForm" action="${pageContext.request.contextPath}/search/searchFlightInJSON" method="GET">
            <tr>
                <td>
                    From Destination
                    <label for="fromDest"></label><input class="form-control" name="fromDest" id="fromDest"/> <br>
                </td>
                <td>
                    To Destination
                    <label for="toDest"></label><input class="form-control" name="toDest" id="toDest"/>
                    <br/>
                </td>
                <td>
                    Via:
                    <label for="viaDest"></label><input class="form-control" name="viaDest" id="viaDest"/> <br/>
                </td>
                <td>
                    Ticket Type:
                    <label for="ticketCode"></label><select class="form-control" name="ticketCode" id="ticketCode">
                        <option value="A" label="Standby">Standby</option>
                        <option value="B" label="Premium Discounted">Premium Discounted</option>
                        <option value="C" label="Discounted">Discounted</option>
                        <option value="D" label="Standard">Standard</option>
                        <option value="E" label="Premium">Premium</option>
                        <option value="F" label="ld">ld</option>
                        <option value="G" label="Platinum">Platinum</option>
                    </select><br/>
                </td>

                <td rowspan="2">
                    <input style="margin-top:70px;" class="form-control btn btn-info" type="submit" id="searchSubmit"
                           value="Search"/>
                </td>
            </tr>
            <tr>
                <td>
                    Earliest Date:
                    <label for="fromTime"></label><input class="form-control" type="date" name="fromTime" id="fromTime"/> <br/>
                </td>
                <td>
                    Latest Departure Date:
                    <label for="toTime"></label><input class="form-control" type="date" name="toTime" id="toTime"/> <br/>
                </td>
                <td>
                    Max Layover Time:
                    <label for="maxLayoverTime"></label><input class="form-control" value="0" type="number" min="0" max="40" name="maxLayoverTime"
                                                               id="maxLayoverTime"/> <br/>
                </td>
                <td>
                    Flight Class:
                    <label for="classCode"></label><select class="form-control" name="classCode" id="classCode">
                        <option value="BUS" label="Business">Business</option>
                        <option value="ECO" label="Economy">Economy</option>
                        <option value="FIR" label="First Class">First Class</option>
                        <option value="PME" label="Premium Economy">Premium Economy</option>
                    </select>
                </td>
            </tr>
        </form>
    </table>
</div>

<div style="text-align: center;color: #4e555b">
    <h2>Search Results</h2>
</div>

<div style="padding: 50px;">
    <div class="container">
<table class="filterTable">
    <tr>
        <td style=" width: 7%">
            <b>Filter By:</b>
        </td>
        <td class="filterData">
            Maximum Price:
            <label for="myRange"></label><input type="range" min="1" step="50" max="10000" value="2000" class="slider" id="myRange">
            <p>Value: <span id="demo"></span></p>
        </td>
        <td class="filterData">
            Minimum review rating:
            <label for="myRangethree"></label><input type="range" min="0" max="5" value="2000" class="slider" id="myRangethree">
            <p>Value: <span id="demothree"></span></p>
        </td>
        <td style=" width: 4%">
            <b>Sort By:</b>
        </td>
        <td class="filterData">
            <label for="filterBy"></label><select class="form-control" id="filterBy">
                <option value="price">Price</option>
                <option value="duration">Duration</option>
                <option value="jetlag">Jet Lag Severity</option>
            </select>
        </td>
        <td class="filterData">
            <label for="orderBy"></label><select class="form-control" id="orderBy">
                <option value="asc">Ascending</option>
                <option value="desc">Descending</option>
            </select>
        </td>
    </tr>
</table>
    </div>
<br>
<h3>Sponsored Flights</h3>
<table  class="alert-warning">
    <thead>
    <tr>
        <th>Journey Legs</th>
        <th>Price</th>
        <th>Departure Time</th>
        <th>Arrival Time</th>
        <th>Total Journey Duration</th>
        <th>Jet Lag Indicator</th>
        <th>Average Journey Review</th>
    </tr>
    </thead>
    <tbody id="sponsored">

    </tbody>
</table>
<br>
<h3>Standard Flights</h3>
<div class="tableFixHead" style="margin: 0 auto;height:60%; overflow-x:hidden">
    <table>
        <thead>
        <tr>
            <th>Journey Legs</th>
            <th>Price</th>
            <th>Departure Time</th>
            <th>Arrival Time</th>
            <th>Total Journey Duration</th>
            <th>Jet Lag Indicator</th>
            <th>Average Journey Review</th>
        </tr>
        </thead>
        <tbody id="resultsSet">

        </tbody>
    </table>
</div>
</div>


<script type="text/javascript">

    //Creates a notification next to dom element provided
    function generateNotification(context, message) {
        $(context).after(
            "<div class='alert alert-info errors'>" +
            message +
            "</div>"
        );

        $("#searchSubmit").removeClass("btn-warning").addClass("btn-info");
        $("#searchSubmit").val("Search");
        $("#searchSubmit").attr("disabled", false);
    }

    //Creates a error next to dom element provided
    function generateError(context, errorDescription) {
        $(context).after(
            "<div class='alert alert-danger errors'>" +
            "  <strong>Error!</strong> " + errorDescription +
            "</div>"
        );

        $("#searchSubmit").removeClass("btn-warning").addClass("btn-info");
        $("#searchSubmit").val("Search");
        $("#searchSubmit").attr("disabled", false);
    }

    function showSponosredRows(){
        if( $('#sponsored > tr').eq(0)){
            $('#sponsored > tr').eq(0).show()
        }

        if( $('#sponsored > tr').eq(1)){
            $('#sponsored > tr').eq(1).show()
        }

        if( $('#sponsored > tr').eq(2)){
            $('#sponsored > tr').eq(2).show()
        }
    }

    //Sort by functionality

    function sort(filterBy, order) {
        var rows = $('#resultsSet > tr').get();
        var allSponsoredRows = $('#sponsored > tr').get();

        $('#sponsored > tr').hide()

        if (filterBy == "price") {
            allSponsoredRows.sort(compareByPrice);
            rows.sort(compareByPrice);
        } else if (filterBy == "duration") {
            rows.sort(compareByDuration);
            allSponsoredRows.sort(compareByDuration);
        } else if (filterBy == "jetlag") {
            rows.sort(compareByJetLag);
            allSponsoredRows.sort(compareByJetLag);
        } else {
            return;
        }


        if (order == "desc") {
            rows.reverse();
            allSponsoredRows.reverse()
        }

        $.each(rows, function (index, row) {
            $('#resultsSet').append(row);
        });

        $.each(allSponsoredRows, function (index, row) {
            $('#sponsored').append(row);
        });

        //Show first three rows
        showSponosredRows()
    }

    function compareByPrice(a, b) {
        $(a).parent().attr("id")
        var A = Number($(a).children('td').eq(1).text().substring(1));
        var B = Number($(b).children('td').eq(1).text().substring(1));

        if (A < B) {
            return -1;
        }

        if (A > B) {
            return 1;
        }

        return 0;
    }

    //
    function compareByDuration(a, b) {
        var A = Number($(a).children('td').eq(4).text());
        var B = Number($(b).children('td').eq(4).text());

        if (A < B) {
            return -1;
        }

        if (A > B) {
            return 1;
        }

        return 0;
    }


    function compareByJetLag(a, b) {
        var A = Number($(a).children('td').eq(5).text());
        var B = Number($(b).children('td').eq(5).text());

        if (A < B) {
            return -1;
        }

        if (A > B) {
            return 1;
        }

        return 0;
    }

    $('#filterBy').on('change', function () {
        sort(this.value, $("#orderBy").val());
    });

    $('#orderBy').on('change', function () {
        sort($("#filterBy").val(), this.value);
    });


    //Listens for search form to submit
    $("#flightForm").submit(function (event) {
        event.preventDefault(); //prevent default action
        var post_url = $(this).attr("action"); //get form action url
        var request_method = $(this).attr("method"); //get form GET/POST method
        var form_data = $(this).serialize(); //Encode form elements for submission

        //Hide all errors
        $(".errors").remove();

        //Check if from destination is a destination
        if ($("#fromDest").val() != $("#fromDest").attr("location")) {
            generateError($("#fromDest"), "Requires a valid location");
            return;
        }

        //Check if destination is a destination
        if ($("#toDest").val() != $("#toDest").attr("location")) {
            generateError($("#toDest"), "Requires a valid location");
            return;
        }


        //Check via
        if ($("#viaDest").val() != "") {
            if ($("#viaDest").val() != $("#viaDest").attr("location")) {
                generateError($("#viaDest"), "Requires a valid location");
                return;
            }
        }

        if ($("#maxLayoverTime").val() == "") {
            generateError($("#maxLayoverTime"), "Requires max layover time");
            return;
        }

        //Check early date and latest departure

        if ($("#fromTime").val() == "") {
            generateError($("#fromTime"), "Date required");
            return;
        }

        if ($("#toTime").val() == "") {
            generateError($("#toTime"), "Date required");
            return;
        }

        //Compare the dates
        if (new Date($("#toTime").val()) <= new Date($("#fromTime").val())) {
            generateError($("#fromTime"), "Earliest Time must be less than latest departure Date")
        }

        //Delete all current results
        $(".results").remove();

        //Setup url in url bar
        window.history.pushState("Search Flight", "Search Flight", "${pageContext.request.contextPath}?" + form_data);

        //Change button colour
        $("#searchSubmit").addClass("btn-warning").removeClass("btn-info");
        $("#searchSubmit").val("Processing");
        $("#searchSubmit").attr("disabled", true);

        //Submit request off
        $.ajax({
            url: post_url,
            type: request_method,
            data: form_data,
            error: function (xhr, ajaxOptions, thrownError) {
                generateError($("#searchSubmit"), "Server error");
                return;
            }
        }).done(function (response) {
            let flightJSONResponse = JSON.parse(response);

            var newRow = "";

            if (flightJSONResponse.error) {
                generateError($("#searchSubmit"), "Server error");
                return;
            }

            if (flightJSONResponse['journeyList'] == "") {
                generateNotification($("#searchSubmit"), "No flight found");
                return;
            }

            //Each row in list
            var count = 0;
            var FlightCount =0;
            var sponsoredCounter = 0;
            var length = flightJSONResponse['journeyList'].length-1;
            for (var journey in flightJSONResponse['journeyList']) {
                newRow = "";
                let currentJourney = flightJSONResponse['journeyList'][journey];
                let bool = currentJourney.restricted;
                if(bool){
                    count++;
                    if (count == length) {
                        if (FlightCount === 0) {
                            generateNotification($("#searchSubmit"), "No flight found");
                            return;
                        }
                        // conitnues with the loop if the journey has a restricted airport.
                    }
                    continue;
                }
                    count++;
                    FlightCount++;
                console.log("Current Journey: " + currentJourney);
                newRow = "<tr class='results' url='" + currentJourney.flightURL + "'>";

                //Loop through journey legs
                newRow += "<td><table><tr>";

                if (currentJourney['legs'].length != 0) {
                    newRow += "<td>" + currentJourney['legs'][0].departure + "</td>";
                }

                for (leg in currentJourney['legs']) {
                    console.log("LEG DEST: " + currentJourney['legs'][leg].destination)
                    newRow += "<td>" + currentJourney['legs'][leg].destination + "</td>";
                }

                newRow += "</tr></table></td>";
                //legs done

                //Add price
                newRow += "<td>$" + currentJourney.price + "</td>";

                //Add departure Time
                newRow += "<td>" + currentJourney.departureTime + "</td>";

                //Add Arrival time
                newRow += "<td>" + currentJourney.arrivalTime + "</td>";

                //Add total duration
                newRow += "<td>" + currentJourney.totalDuration + "</td>";

                //Add jetlag indicator
                newRow += "<td class='jetlag jetlag" + currentJourney.jetLagIndicator + "'>" + currentJourney.jetLagIndicator + "</td>";

                newRow += "<td>" + currentJourney.review + "</td>";

                newRow += "</tr>";

                $("#resultsSet").append(newRow);

                if(currentJourney.isSponsored){
                    let sponsoredRow = $(newRow);
                    $(sponsoredRow).hide()
                    $("#sponsored").append(sponsoredRow);
                }


                $("#searchSubmit").removeClass("btn-warning").addClass("btn-info");
                $("#searchSubmit").val("Search");
                $("#searchSubmit").attr("disabled", false);
            }

            //Sort results
            sort($("#filterBy").val(), $("#orderBy").val());

            showSponosredRows()


        });
    });
</script>

<script>

    //Extract get parameters out of URL
    //https://stackoverflow.com/questions/19491336/how-to-get-url-parameter-using-jquery-or-plain-javascript
    function getUrlParameter(sParam) {
        var sPageURL = window.location.search.substring(1),
            sURLVariables = sPageURL.split('&'),
            sParameterName,
            i;

        for (i = 0; i < sURLVariables.length; i++) {
            sParameterName = sURLVariables[i].split('=');

            if (sParameterName[0] === sParam) {
                return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
            }
        }
    }

    $(document).ready(function () {
        if (getUrlParameter("fromDest")) {
            $("#fromDest").val(getUrlParameter("fromDest"))
            $("#fromDest").attr("location", getUrlParameter("fromDest"))
        }

        if (getUrlParameter("toDest")) {
            $("#toDest").val(getUrlParameter("toDest"))
            $("#toDest").attr("location", getUrlParameter("toDest"))
        }

        if (getUrlParameter("viaDest")) {
            $("#viaDest").val(getUrlParameter("viaDest"))
            $("#viaDest").attr("location", getUrlParameter("viaDest"))
        }

        if (getUrlParameter("fromTime")) {
            $("#fromTime").val(getUrlParameter("fromTime"))
        }

        if (getUrlParameter("toTime")) {
            $("#toTime").val(getUrlParameter("toTime"))
        }

        if (getUrlParameter("maxLayoverTime")) {
            $("#maxLayoverTime").val(getUrlParameter("maxLayoverTime"))
        }

        if (getUrlParameter("classCode")) {
            $("#classCode option[value=" + getUrlParameter("classCode") + "]").attr('selected', 'selected')
        }

        if (getUrlParameter("ticketCode")) {
            $("#ticketCode option[value=" + getUrlParameter("ticketCode") + "]").attr('selected', 'selected')
        }

        if (getUrlParameter("fromDest")) {
            $('#flightForm').submit();
        }

        //This handles the max price
        $(document).on('input', '#myRange', function () {

            $('.results').each(function (i, result) {
                //Compare values

                if (Number($(this).children('td').eq(1).text().substring(1)) <= $("#myRange").val()) {
                    $(this).show();
                } else {
                    $(this).hide();
                }

            });

            let maxRows = 3
            let status = 1
            $('#sponsored > tr').each(function (i, result) {
                if(status > maxRows){
                    $(this).hide()
                }

                status++
            });

            $("#demo").text($(this).val())
        });

        //Handles on changes value of minium rating
        $(document).on('input', '#myRangethree', function () {
            $('.results').each(function (i, result) {
                //Compare values
                if (Number($(this).children('td').eq(6).text()) >= $("#myRangethree").val()) {
                    $(this).show();
                } else {
                    $(this).hide();
                }
            });

            let maxRows = 3
            let status = 1
            $('#sponsored > tr').each(function (i, result) {
                if(status > maxRows){
                    $(this).hide()
                }

                status++
            });

            $("#demothree").text($(this).val())
        });

        $(document).on("click", ".results", function () {
            window.location.href = "/Journey/viewJourney?flightIds=" + $(this).attr('url') + "&f"
        })
    })
</script>

<script type="text/javascript">
    $(function () {
        $("#toDest").autocomplete({
            source: "/search/AirportAutoComplete",
            delay: 550,
            select: function (event, ui) {
                $(this).attr("location", ui.item.value);
            },
            response: function (event, ui) {
                $.each(ui.content, function (index, value) {
                    if (value.label.toUpperCase() === $("#toDest").val().toUpperCase()) {
                        $("#toDest").attr("location", value.label);
                        $("#toDest").val(value.label);
                    }
                });
            }
        });
    });
    $(function () {
        $("#fromDest").autocomplete({
            source: "/search/AirportAutoComplete",
            delay: 550,
            select: function (event, ui) {
                $(this).attr("location", ui.item.value);
            },
            response: function (event, ui) {
                $.each(ui.content, function (index, value) {
                    if (value.label.toUpperCase() === $("#fromDest").val().toUpperCase()) {
                        $("#fromDest").attr("location", value.label);
                        $("#fromDest").val(value.label);
                    }
                });
            }
        });
    });
    $(function () {
        $("#viaDest").autocomplete({
            source: "/search/AirportAutoComplete",
            delay: 550,
            select: function (event, ui) {
                $(this).attr("location", ui.item.value);
            },
            response: function (event, ui) {
                $.each(ui.content, function (index, value) {
                    if (value.label.toUpperCase() === $("#viaDest").val().toUpperCase()) {
                        $("#viaDest").attr("location", value.label);
                        $("#viaDest").val(value.label);
                    }
                });
            }
        });
    });
</script>

<style>

    .tableFixHead {
        overflow-y: auto;
        height: 80%;
    }

    .errors {
        margin-top: 30px;
    }

    .tableFixHead thead th {
        position: sticky;
        top: 0;
    }

    /* Just common table stuff. Really. */
    table {
        border-collapse: collapse;
        width: 100%;
    }

    .results:hover {
        cursor: pointer;
        background-color: gray;
        color: white;
        opacity: 0.4;

    }

    .results > td {
        border: 1px solid #b8daff;
    "
    }

    th, td {
        padding: 8px 16px;
    }

    th {
        background: #eee;
    }

    .filterTable {
        table-layout: fixed;
    }

    .filterData {
        width: 12.5%;
    }

    .slider {
        -webkit-appearance: none;
        width: 100%;
        height: 10px;
        background: #d3d3d3;
        outline: none;
        opacity: 0.7;
        -webkit-transition: .2s;
        transition: opacity .2s;
    }

    .slider:hover {
        opacity: 1;
    }

    .slider::-webkit-slider-thumb {
        -webkit-appearance: none;
        appearance: none;
        width: 10px;
        height: 10px;
        background: #4CAF50;
        cursor: pointer;
    }

    .slider::-moz-range-thumb {
        width: 25px;
        height: 25px;
        background: #4CAF50;
        cursor: pointer;
    }

    .jetlag {
        color: white;
        border: 1px solid #b8daff;
    }

    .jetlag1 {
        background-color: #b1dfbb;
    }

    .jetlag2 {
        background-color: #ffdf7e;
    }

    .jetlag3 {
        background-color: #e4606d;
    }
</style>

</body>
</html>

