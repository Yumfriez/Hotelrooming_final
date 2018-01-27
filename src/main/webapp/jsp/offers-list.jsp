<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="command" value="${requestScope.command}"/>
<fmt:setLocale scope="session" value="${sessionScope.userLocale}"/>
<c:set var="role" value="${sessionScope.role}"/>
<c:set var="admin" value="ADMINISTRATOR"/>
<c:set var="user" value="USER"/>
<fmt:setBundle basename="by.tr.hotelbooking.localization.front-end" scope="session" var="loc"/>
<fmt:message bundle="${loc}" key="local.menu.accounts_control" var="accounts_control"/>
<fmt:message bundle="${loc}" key="local.menu.new_orders" var="new_orders"/>
<fmt:message bundle="${loc}" key="local.menu.current_contracts" var="current_contracts"/>
<fmt:message bundle="${loc}" key="local.menu.hotelrooms_control" var="hotelrooms_control"/>
<fmt:message bundle="${loc}" key="local.menu.log_out" var="log_out"/>
<fmt:message bundle="${loc}" key="local.button.delete" var="delete_button"/>
<fmt:message bundle="${loc}" key="local.button.edit" var="edit_button"/>
<fmt:message bundle="${loc}" key="local.button.add_hotelroom_button" var="add_hotelroom"/>
<fmt:message bundle="${loc}" key="local.word.hotelnumber" var="hotelroom_number"/>
<fmt:message bundle="${loc}" key="local.word.places_count" var="places_count"/>
<fmt:message bundle="${loc}" key="local.word.floor" var="floor"/>
<fmt:message bundle="${loc}" key="local.word.daily_price" var="daily_price"/>
<fmt:message bundle="${loc}" key="local.word.room_type" var="room_type"/>
<fmt:message bundle="${loc}" key="local.word.hotelroom_reserved" var="hotelroom_reserved"/>
<fmt:message bundle="${loc}" key="local.button.order_hotelroom" var="order_hotelroom_button"/>
<fmt:message bundle="${loc}" key="local.button.send_contract" var="send_contract_button"/>
<fmt:message bundle="${loc}" key="local.word.total_price" var="total_price_label"/>
<fmt:message bundle="${loc}" key="local.word.date_in" var="date_in_label"/>
<fmt:message bundle="${loc}" key="local.word.date_out" var="date_out_label"/>
<fmt:message bundle="${loc}" key="local.button.accept" var="accept_button"/>
<fmt:message bundle="${loc}" key="local.button.decline" var="decline_button"/>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" pageEncoding="utf-8">
    <link rel="stylesheet" href="../styles/bootstrap.css" type="text/css"/>
    <link rel="stylesheet" href="../styles/pagination.css" type="text/css"/>
    <link rel="stylesheet" href="../styles/style.css" type="text/css"/>
    <link rel="stylesheet" href="../styles/headerstyle.css" type="text/css"/>
    <link rel="stylesheet" href="../styles/datatables.min.css" type="text/css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="../js/datatables.min.js" type="text/javascript"></script>
</head>
<body>

<div class="content">
    <%@include file="parts/header.jsp"%>
    <div class="container">
        <div class="row">
            <%@include file="parts/user-menu.jsp"%>
            <div class="col-md-12 products">
                <div class="row">
                    <c:forEach var="offer" items="${requestScope.offersList}">
                        <div class="col-sm-6">
                            <div class="product">
                                <div class="main_label">${hotelroom_number}: ${offer.hotelroomNumber}</div>
                                <div class="product-img">
                                    <img class="hotelroom_image" src="${pageContext.request.contextPath}/images/hotelrooms/${offer.imageName}"
                                         alt="${offer.roomType.name}">
                                </div>
                                <p class="product-title">
                                    <b>${places_count}: </b>${offer.placesCount}<br/>
                                    <b>${floor}:</b> ${offer.floor}<br/>
                                    <b>${date_in_label}: </b>${offer.dateIn}<br/>
                                    <b>${date_out_label}:</b> ${offer.dateOut}<br/>
                                </p>
                                <p class="product-desc"><b>${room_type}: </b>${offer.roomType.name}<br/></p>
                                <p class="product-price"> <b>${total_price_label}: </b>${offer.totalPrice}<br/></p>
                                <div id="button_under_hotelroom">
                                    <form class="button" action="/hotelrooming" method="post">
                                        <input type="hidden" name="contract_id" value="${offer.id}"/>
                                        <input type="hidden" name="command" value="accept_contract"/>
                                        <input class="green-butt" type="submit" value="${accept_button}"/>
                                    </form>

                                    <form class="button" action="/hotelrooming" method="post">
                                        <input type="hidden" name="contract_id" value="${offer.id}"/>
                                        <input type="hidden" name="command" value="decline_contract"/>
                                        <input class="red-butt" type="submit" value="${decline_button}"/>
                                    </form>

                                </div>
                            </div>
                        </div>
                    </c:forEach>

                </div>
            </div>
        </div>

    </div>
    <div class="pagination">
        <%@include file="parts/pagination.jsp" %>
    </div>
</div>
</body>
</html>
