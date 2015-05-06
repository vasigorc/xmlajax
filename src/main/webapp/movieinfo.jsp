<%-- 
    Document   : movieinfo
    Created on : 2 mai 2015, 17:33:58
    Author     : vasigorc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<div class="panel panel-default">
    <!-- Default panel contents -->
    <div class="panel-heading">${selectedMovie}</div>

    <c:if test="${movieInfo.noPasses}">
        <div class="panel-body">
            <p>
                <em>The theater offers free courtesy tickets if it's your
                    birthday. Please contact theater administration.</em>
            </p>
        </div>
    </c:if>
    <!-- List group -->
    <ul class="list-group">
        <li class="list-group-item"><em>Duration: </em>${movieInfo.duration}</li>
        <li class="list-group-item"><em>Genre: </em>${movieInfo.genre}</li>
        <li class="list-group-item"><em>Rank: </em>
            <c:choose>
                <c:when test="${movieInfo.rank eq 'G'}">
                    <span class="roundborderGreen">${movieInfo.rank}</span>
                </c:when>
               <c:otherwise>
                   <span class="roundborderRed">${movieInfo.rank}</span>
               </c:otherwise>
            </c:choose>
        </li>
        <li class="list-group-item"><em>Showing on: </em>
            <c:forEach items="${movieInfo.showingon}" var="show">
                ${show}&nbsp;&nbsp;
            </c:forEach>
        </li>
        <li class="list-group-item"><em>Released: </em>${movieInfo.released}</li>
            <c:if test="${fn:length(movieInfo.actors)>0}">
            <li class="list-group-item"><em>Actors: </em>
                <c:forEach items="${movieInfo.actors}" var="actor" varStatus="status">
                    ${actor}
                    <c:if test="${not status.last}">
                        ,&nbsp;
                    </c:if>
                </c:forEach>
            </li>
        </c:if>
        <li class="list-group-item"><em>Directors: </em>
            <c:forEach items="${movieInfo.directors}" var="director" varStatus="status">
                ${director}
                <c:if test="${not status.last}">
                    ,&nbsp;
                </c:if>
            </c:forEach>
        </li>
        <li class="list-group-item"><em>Producers: </em>
            <c:forEach items="${movieInfo.producers}" var="producer" varStatus="status">
                ${producer}
                <c:if test="${not status.last}">
                    ,&nbsp;
                </c:if>
            </c:forEach>
        </li>
        <c:if test="${fn:length(movieInfo.writers)>0}">
            <li class="list-group-item"><em>Writers: </em>
                <c:forEach items="${movieInfo.writers}" var="writer" varStatus="status">
                    ${writer}
                    <c:if test="${not status.last}">
                        ,&nbsp;
                    </c:if>
                </c:forEach>
            </li>
        </c:if>
        <li class="list-group-item"><em>Studios: </em>
            <c:forEach items="${movieInfo.studios}" var="studio" varStatus="status">
                ${studio}
                <c:if test="${not status.last}">
                    ,&nbsp;
                </c:if>
            </c:forEach>
        </li>
    </ul>
</div>