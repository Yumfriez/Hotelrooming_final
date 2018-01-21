<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="command" value="${requestScope.command}"/>
<fmt:setLocale scope="session" value="${sessionScope.userLocale}"/>
<c:set var="login" value="${sessionScope.login}"/>
<c:set var="role" value="${sessionScope.role}"/>
<c:set var="admin" value="ADMINISTRATOR"/>
<c:set var="user" value="USER"/>
<fmt:setBundle basename="by.tr.hotelbooking.localization.front-end" scope="session" var="loc"/>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" pageEncoding="utf-8">
    <link rel="stylesheet" href="../styles/bootstrap.css" type="text/css"/>
    <link rel="stylesheet" href="../styles/pagination.css" type="text/css"/>
    <link rel="stylesheet" href="../styles/style.css" type="text/css"/>
    <link rel="stylesheet" href="../styles/headerstyle.css" type="text/css"/>
    <link rel="stylesheet" href="../styles/comments.css" type="text/css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body class="comment-body" onload="getDate()">
<c:choose>
    <c:when test="${role eq(admin) or role eq(user)}">
        <%@include file="parts/header.jsp"%>
    </c:when>
    <c:otherwise>
        <%@include file="parts/welcome-header.jsp"%>
    </c:otherwise>
</c:choose>
<div class="comments-content">


    <div class="container">
        <ul class="comment-section">

        <c:forEach var="comment" items="${requestScope.commentsList}">
            <li class="comment user-comment">
                <div class="comment-info">
                    <a href="#" class="user-data">${comment.userName} ${comment.userSurname}</a>
                    <span class="date-info">${comment.commentDate}</span>
                    <c:choose>
                        <c:when test="${role eq(admin)}">
                            <form action="/hotelrooming" method="post">
                                <input type="hidden" name="command" value="delete_comment">
                                <input type="hidden" name="comment_id" value="${comment.id}">
                                <input class="delete-button" type="submit" title="Delete comment" value="x"/>
                            </form>
                        </c:when>
                        <c:when test="${role eq(user)}">
                            <c:if test="${login eq comment.accountLogin}">
                                <form action="/hotelrooming" method="post">
                                    <input type="hidden" name="command" value="delete_comment">
                                    <input type="hidden" name="comment_id" value="${comment.id}">
                                    <input class="delete-button" type="submit" title="Delete comment" value="x"/>
                                </form>
                            </c:if>
                        </c:when>
                    </c:choose>
                </div>
                <p class="comment-text">${comment.text}</p>
            </li>
        </c:forEach>
        <c:if test="${not empty login and role eq(user)}">
            <li class="write-new">

                <form action="/hotelrooming" method="post">
                    <input type="hidden" name="command" value="add_comment">
                    <input type="hidden" name="login" value="${login}">
                    <input type="hidden" name="comment_date" id="todayDate" value=""/>
                    <textarea class="comment-field" placeholder="Write your comment here" name="comment_text"></textarea>
                    <button class="comment-button" type="submit">Submit</button>

                </form>

            </li>
        </c:if>

        </ul>

    </div>
    <div class="pagination">
        <%@include file="parts/pagination.jsp" %>
    </div>
</div>
<script src="../js/date-filling.js"></script>

</body>
</html>
