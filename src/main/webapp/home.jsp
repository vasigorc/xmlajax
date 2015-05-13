<%-- 
    Document   : home
    Created on : 22 avr. 2015, 22:40:49
    Author     : vasigorc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Start Page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/bootstrap.min.css"></link>
        <link rel="stylesheet" href="css/addstyles.css"></link>
        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script type="text/javascript" src="js/homejQ.js"></script>
    </head>
    <body>

        <h1 style="text-align: center">Look by:</h1>
        <div id="maincontent" style="margin-left: 5em; margin-right: 5em">
            <div class="radio">
                <label class="radio-inline">
                    <input type="radio" data-bind="checked: radioSelectedOptionValue" value="Movie Title">Movie Title
                </label>
                <label class="radio-inline">
                    <input type="radio" data-bind="checked: radioSelectedOptionValue" value="Specific Actor">Specific Actor
                </label>
                <label class="radio-inline">
                    <input type="radio" data-bind="checked: radioSelectedOptionValue" value="Specific Director">Specific Director
                </label>
            </div>            
            <div data-bind="visible: radioSelectedOptionValue() == 'Movie Title'">
                <select class="form-control" id="movieinput">
                    <c:forEach var="movie" items="${movielist}">
                        <option><c:out value="${movie}"/></option>                    
                    </c:forEach>
                </select>
            </div>
            <div data-bind="visible: radioSelectedOptionValue() == 'Specific Actor'">
                <input type="text" id="actorInput" placeholder="Enter actor's name">
            </div>
            <div data-bind="visible: radioSelectedOptionValue() == 'Specific Director'">
                <input  type="text" id="directorInput" placeholder="Enter director's name">
            </div>
            <p class="text-center">
                <input id="retrieve" class="btn btn-default" type="submit" value="With DOM">
                <input id="findstax" class="btn btn-default" type="submit" value="With StAX">
            </p>
            <div id="dynamiccontent"></div>
        </div>        
        <script type="text/javascript" src="js/knockout-3.3.0.js"></script>
        <script type="text/javascript" src="js/knockout.mapping-latest.js"></script>
        <script type="text/javascript">
            var viewModel = {
                optionValues: ["Movie Title", "Specific Actor", "Specific Director"],
                radioSelectedOptionValue: ko.observable("Specific Actor")
            };
            ko.applyBindings(viewModel);
        </script>
    </body>
</html>
