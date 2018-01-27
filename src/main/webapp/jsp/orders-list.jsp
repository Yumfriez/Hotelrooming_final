<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale scope="session" value="${sessionScope.userLocale}"/>
<c:set var="role" value="${sessionScope.role}"/>
<c:set var="userLogin" value="${sessionScope.login}"/>
<c:set var="admin" value="ADMINISTRATOR"/>
<c:set var="user" value="USER"/>
<fmt:setBundle basename="by.tr.hotelbooking.localization.front-end" scope="session" var="loc"/>
<fmt:message bundle="${loc}" key="local.word.places_count" var="places_count_label"/>
<fmt:message bundle="${loc}" key="local.word.min_daily_price" var="min_daily_price_label"/>
<fmt:message bundle="${loc}" key="local.word.max_daily_price" var="max_daily_price_label"/>
<fmt:message bundle="${loc}" key="local.word.room_type" var="room_type_label"/>
<fmt:message bundle="${loc}" key="local.word.parameters_label" var="parameters_label"/>
<fmt:message bundle="${loc}" key="local.word.make_order_label" var="make_order_label"/>
<fmt:message bundle="${loc}" key="local.button.make_order_button" var="make_order_button"/>
<fmt:message bundle="${loc}" key="local.button.find" var="find_button"/>
<fmt:message bundle="${loc}" key="local.button.cancel_order" var="cancel_order_button"/>
<fmt:message bundle="${loc}" key="local.word.user_login" var="user_login_label"/>
<fmt:message bundle="${loc}" key="local.word.date_in" var="date_in_label"/>
<fmt:message bundle="${loc}" key="local.word.days_count" var="days_count_label"/>
<c:set var="command" value="${requestScope.command}"/>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="../styles/bootstrap.css" type="text/css"/>
    <link rel="stylesheet" href="../styles/style.css" type="text/css"/>
    <link rel="stylesheet" href="../styles/pagination.css" type="text/css"/>
    <link rel="stylesheet" href="../styles/headerstyle.css" type="text/css"/>
    <link rel="stylesheet" href="../styles/orders.css" type="text/css">
    <link rel="stylesheet" href="../styles/orders-table/style.css" type="text/css">

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" pageEncoding="utf-8">
    <title>Hotelrooming</title>



</head>
<body onload="$('#table').stacktable();">
<div class="content">
    <%@include file="parts/header.jsp"%>
    <div class="container">
        <table id="table" class="tablesorter orders-table stacktable large-only">
            <thead>
            <tr>
                <th>${user_login_label}</th>
                <th>${places_count_label}</th>
                <th>${min_daily_price_label}</th>
                <th>${max_daily_price_label}</th>
                <th>${room_type_label}</th>
                <th>${date_in_label}</th>
                <th>${days_count_label}</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.ordersList}" var="order">
                <c:if test="${role eq(admin) or (role eq(user) and userLogin eq(order.accountLogin))}">
                <tr>
                    <td>${order.accountLogin}</td>
                    <td>${order.preferedPlacesCount}</td>
                    <td>${order.minPrice}</td>
                    <td>${order.maxPrice}</td>
                    <td>${order.roomType.name}</td>
                    <td>${order.preferedDateIn}</td>
                    <td>${order.daysCount}</td>
                    <c:choose>
                        <c:when test="${role eq(admin)}">
                            <td>
                                <form action="/hotelrooming" method="post">
                                    <input type="hidden" name="command" value="find_hotelrooms">
                                    <input type="hidden" name="order_id" value="${order.id}">
                                    <input type="hidden" name="room_type" value="${order.roomType.id}">
                                    <input type="hidden" name="min_price" value="${order.minPrice}">
                                    <input type="hidden" name="max_price" value="${order.maxPrice}">
                                    <input type="hidden" name="date_in" value="${order.preferedDateIn}">
                                    <input type="hidden" name="days_count" value="${order.daysCount}">
                                    <input type="hidden" name="places_count" value="${order.preferedPlacesCount}">
                                    <input type="hidden" name="client_login" value="${order.accountLogin}">
                                    <input class="orders-button" type="submit" value="${find_button}">
                                </form>
                            </td>
                            <td>
                                <form action="/hotelrooming" method="post">
                                    <input type="hidden" name="command" value="cancel_order">
                                    <input type="hidden" name="order_id" value="${order.id}">
                                    <input class="orders-button" type="submit" value="${cancel_order_button}">
                                </form>
                            </td>
                        </c:when>
                        <c:when test="${role eq(user)}">
                            <td>
                                <form action="/hotelrooming" method="post">
                                    <input type="hidden" name="command" value="cancel_order">
                                    <input type="hidden" name="order_id" value="${order.id}">
                                    <input class="orders-button" type="submit" value="${cancel_order_button}">
                                </form>
                            </td>
                        </c:when>
                    </c:choose>

                </tr>
                </c:if>
            </c:forEach>
            </tbody>
        </table>



    </div>
    <div class="pagination">
        <%@include file="parts/pagination.jsp" %>
    </div>
</div>
<script src="../js/jquery-3.2.1.js"></script>
<script src="../js/stacktable.js"></script>
<script src="../js/jquery.tablesorter.js"></script>
<script type="text/javascript">
    $(document).ready(function() {
        $("#table").tablesorter();
    });
</script>
</body>
</html>