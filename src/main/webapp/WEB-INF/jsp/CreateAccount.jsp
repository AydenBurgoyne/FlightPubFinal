<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <jsp:include page="layout/header.jsp"/>

    <%--    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>--%>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet"/>
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

</head>
<body class="text-center">

<div class="container">
    <h1>Create Account</h1>


    <form:form action="/Account/ValidateAccCreation" id="form" method="post" modelAttribute="AccountEntity">

        <table class="table table-bordered">
            <tr>
                <td>
                    <h3>First Name</h3>
                </td>
                <td>
                    <form:input class="form-control" path="firstName"/>
                    <form:errors path="firstName" cssStyle="color: #ff0000;"/>
                </td>
            </tr>
            <tr>
                <td>
                    <h3>Last Name</h3>
                </td>
                <td>
                    <form:input class="form-control" path="lastName"/> <br/>
                    <form:errors path="lastName" cssStyle="color: #ff0000;"/>
                </td>
            </tr>
            <tr>
                <td>
                    <h3>Email Address</h3>
                </td>
                <td>
                    <form:input class="form-control" path="email"/> <br/>
                    <form:errors path="email" cssStyle="color: #ff0000;"/>
                </td>
            </tr>
            <tr>
                <td>
                    <h3>Home Address</h3>
                </td>
                <td>
                    <form:input class="form-control" path="address"/> <br/>
                    <form:errors path="address" cssStyle="color: #ff0000;"/>
                </td>
            </tr>
            <tr>
                <td>
                    <h3>Date of Birth</h3>
                </td>
                <td>
                    <form:input type="date" class="form-control" path="dateOfBirth"/> <br/>
                    <form:errors path="dateOfBirth" cssStyle="color: #ff0000;"/>
                </td>
            </tr>
            <tr>
                <td>
                    <h3>Traveller Type</h3>
                    <p class="f" style="color: #ababab">Please choose a traveller type that suits you best. <br/>
                        This information will be used to optimise FlightPub's <br/>
                        features to work in a way more useful to you.
                    </p>
                </td>

                <td>
                    <form:select class="form-control" path="type">
                        <form:option value="true" label="Business"/>
                        <form:option value="false" label="Recreational"/>
                    </form:select>
                    <br>
                    <form:errors path="type" cssStyle="color: #ff0000;"/>
                </td>
            </tr>
            <tr>
                <td>
                    <h3>Destination Wishlist</h3>
                    <p style="color: #ababab">Let us know your dream travel destinations, we might just be able<br/>
                        to find you a great deal to make it happen!<br/>
                        Each field can be either left empty or you may type a location in it.
                    </p>
                </td>
                <td>
                        <%--                    <form:input class="form-control" path="wishListItem1"/> <br/>--%>
                        <%--                    <input type="text" class="form-control" name="wishList">--%>
                        <%--                    <input type="text" class="form-control" name="wishList">--%>
                        <%--                    <input type="text" class="form-control" name="wishList">--%>

                    <table class="table table-bordered" id="table">
                        <tr>
                            <td>
                                <input class="form-control" type="text" id="Destination" />
                            </td>
                            <td>
                                <button type="button" class="btn btn-success" id="add">Add</button>
                            </td>
                        </tr>
                    </table>

                        <%--                    <form:errors path="wishListItem1" cssStyle="color: #ff0000;"/>--%>

                        <%--                    <form:input class="form-control" path="wishListItem2"/> <br/>--%>
                        <%--                    <form:errors path="wishListItem2" cssStyle="color: #ff0000;"/>--%>

                        <%--                    <form:input class="form-control" path="wishListItem3"/> <br/>--%>
                        <%--                    <form:errors path="wishListItem3" cssStyle="color: #ff0000;"/>--%>

                </td>


                    <%--&lt;%&ndash;                using standard html to pass an array because spring mappings arent ideal for arrays.&ndash;%&gt;--%>
                    <%--                <td>--%>
                    <%--                    <form class="form-control" method="post">--%>
                    <%--                        <input type="text" name="wishList">--%>
                    <%--                        <input type="text" name="wishList">--%>
                    <%--                        <input type="text" name="wishList">--%>
                    <%--                        <input type="submit">--%>
                    <%--                    </form>--%>
                    <%--                </td>--%>

            </tr>
            <tr>
                <td>
                    <h3>Password</h3>
                    <em style="color: #ababab">
                        Password must be between 6 and 10 characters.<br/>
                        It must contain at least 1 number and 1 capital letter. <br/>
                        It must not contain any special characters.
                    </em>
                </td>
                <td>
                    <form:password class="form-control" path="pword"/> <br/>
                    <form:errors path="pword" cssStyle="color: #ff0000;"/><br>

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

<script type="text/javascript">

    function generateNotification(context, message) {
        $(context).after(
            "<div class='alert alert-info errors'>" +
            message +
            "</div>"
        );
    }

    $(document).ready( function (){
        $("#Destination").autocomplete({
            source: "/search/AirportAutoComplete",
            delay: 150,
            select: function (event, ui) {
                $(this).attr("location", ui.item.value);
            },
            response: function( event, ui ) {
                $.each( ui.content, function( index, value ){
                    if(value.label.toUpperCase() ===  $("#Destination").val().toUpperCase()){
                        $("#Destination").attr("location", value.label);
                        $("#Destination").val(value.label);
                    }
                });
            }
        });

        $("#form").submit(function (event) {
            //If there no wishList Destination
            if($(".wishDestination").size() === 0){
                generateNotification($("#Destination"), "Require at least one destination wish")
                event.preventDefault()
                return;
            }
        });

        $(document).on("click", "#add", function () {
            $(".errors").remove()

            if ($("#Destination").val() === "") {
                generateNotification($("#Destination"), "Can't be blank")
                return
            }

            //Get selected location
            let locationSelected = $("#Destination").attr("location");

            if (locationSelected === "") {
                generateNotification($("#Destination"), "Require a valid destination")
                return
            }

            let pass = true;
            //Check if location already picked
            $(".wishDestination").each( function (){
                if($(this).val().toLowerCase() === locationSelected.toLowerCase()){
                    generateNotification($("#Destination"),"Destination already picked")
                    pass = false;
                    return false;
                }
            });

            if(pass == false){
                return
            }


            $("#Destination").val("");
            $("#table").append("<tr>    <td><input class='form-control wishDestination' disabled type='text' name='wishList' value='" + locationSelected + "'></td>  <td> <button type='button' class='btn btn-danger remove'>Remove</button> </td></tr>");
        });

        $(document).on("click", ".remove", function () {
            $(this).parent().parent().empty().remove();
        });
    });
</script>
</body>
<jsp:include page="layout/footer.jsp"/>
</html>
