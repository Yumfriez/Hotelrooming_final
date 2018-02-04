<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="command" value="${requestScope.command}"/>
<c:set var="show_hotelrooms" value="show_hotelrooms"/>
<c:set var="find_hotelrooms" value="find_hotelrooms"/>
<c:set var="files_directory" value="${requestScope.filesPath}"/>
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
<fmt:message bundle="${loc}" key="local.word.hotelrooms" var="hotelrooms"/>
<fmt:message bundle="${loc}" key="local.word.hotelroom_reserved" var="hotelroom_reserved"/>
<fmt:message bundle="${loc}" key="local.button.order_hotelroom" var="order_hotelroom_button"/>
<fmt:message bundle="${loc}" key="local.button.send_contract" var="send_contract_button"/>
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
    <title>${hotelrooms}</title>
</head>
<body>

<div class="content">
    <%@include file="parts/header.jsp"%>
    <div class="container">
        <div class="row">
            <c:choose>
                <c:when test="${role eq(admin)}">
                    <%@include file="parts/admin-menu.jsp"%>
                    <c:if test="${command eq(show_hotelrooms)}">
                    <div class="col-md-4">
                        <div class="list-group">
                            <form action="hotelrooming" method="get" accept-charset="UTF-8">
                                <input type="hidden" name="command" value="show_page_for_add_hotelroom"/>
                                <input type="submit" class="list-group-item" value="${add_hotelroom}"/>
                            </form>
                        </div>
                    </div>
                    </c:if>
                </c:when>
                <c:when test="${role eq(user)}">
                    <%@include file="parts/user-menu.jsp"%>
                </c:when>
                <c:otherwise>
                    ERROR
                </c:otherwise>
            </c:choose>

            <div class="col-md-12 products">
                <div class="row">
                    <c:forEach var="hotelroom" items="${requestScope.hotelroomsList}">
                        <div class="col-sm-6">
                            <div class="product">
                                <form action="hotelrooming" method="post">
                                    <input type="hidden" name="command" value="show_hotelroom"/>
                                    <input type="hidden" name="hotelroom_id" value="${hotelroom.id}"/>
                                    <input id="room_number" type="submit" value="${hotelroom_number}: ${hotelroom.number}"/>
                                </form>
                                <div class="product-img">
                                <form action="hotelrooming" method="post">
                                    <input type="hidden" name="command" value="show_hotelroom"/>
                                    <input type="hidden" name="hotelroom_id" value="${hotelroom.id}"/>
                                    <input class="hotelroom_image" type="image"
                                           src="${pageContext.request.contextPath}${files_directory}${hotelroom.imageName}"
                                           alt="${hotelroom.roomType.name}"/>
                                </form>
                            </div>
                                <p class="product-title">
                                    <b>${places_count}: </b>${hotelroom.placesCount}<br/>
                                    <b>${floor}:</b> ${hotelroom.floor}<br/>
                                </p>
                                <p class="product-desc"><b>${room_type}: </b>${hotelroom.roomType.name}<br/></p>
                                <p class="product-price"> <b>${daily_price}: </b>${hotelroom.dailyPrice}<br/></p>
                                <div id="button_under_hotelroom">
                                    <c:choose>
                                        <c:when test="${command eq(show_hotelrooms)}">
                                            <c:choose>
                                                <c:when test="${role eq(admin)}">
                                                    <form class="button" action="hotelrooming" method="post">
                                                        <input type="hidden" name="hotelroom_id" value="${hotelroom.id}"/>
                                                        <input type="hidden" name="command" value="show_edit_hotelroom_page"/>
                                                        <input class="all-btns" type="submit" value="${edit_button}"/>
                                                    </form>
                                                    <form class="button" action="hotelrooming" method="post">
                                                        <input type="hidden" name="hotelroom_id" value="${hotelroom.id}"/>
                                                        <input type="hidden" name="command" value="remove_hotelroom"/>
                                                        <input class="all-btns" type="submit" value="${delete_button}" onclick="confirm('Вы уверены?')"/>
                                                    </form>
                                                </c:when>
                                                <c:when test="${role eq(user)}">
                                                </c:when>
                                                <c:otherwise>
                                                    ERROR
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:when test="${command eq(find_hotelrooms)}">
                                            <c:choose>
                                                <c:when test="${role eq(admin)}">
                                                    <form class="button" action="hotelrooming" method="post">
                                                        <input type="hidden" name="hotelroom_id" value="${hotelroom.id}"/>
                                                        <input type="hidden" name="order_id" value="${requestScope.order_id}">
                                                        <input type="hidden" name="command" value="add_contract"/>
                                                        <input class="all-btns" type="submit" value="${send_contract_button}"/>
                                                    </form>
                                                </c:when>
                                                <c:otherwise>
                                                    ERROR
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                    </c:choose>
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
