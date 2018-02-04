<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale scope="session" value="${sessionScope.userLocale}"/>
<c:set var="role" value="${sessionScope.role}"/>
<c:set var="userLogin" value="${sessionScope.login}"/>
<c:set var="currentDate" value="${requestScope.currentDate}"/>
<c:set var="admin" value="ADMINISTRATOR"/>
<c:set var="user" value="USER"/>
<fmt:setBundle basename="by.tr.hotelbooking.localization.front-end" scope="session" var="loc"/>
<fmt:message bundle="${loc}" key="local.word.places_count" var="places_count_label"/>
<fmt:message bundle="${loc}" key="local.word.room_type" var="room_type_label"/>
<fmt:message bundle="${loc}" key="local.button.make_order_button" var="make_order_button"/>
<fmt:message bundle="${loc}" key="local.word.user_login" var="user_login_label"/>
<fmt:message bundle="${loc}" key="local.word.hotelnumber" var="hotelroom_number_label"/>
<fmt:message bundle="${loc}" key="local.button.cancel_order" var="cancel_order_button"/>
<fmt:message bundle="${loc}" key="local.word.date_in" var="date_in_label"/>
<fmt:message bundle="${loc}" key="local.word.date_out" var="date_out_label"/>
<fmt:message bundle="${loc}" key="local.word.total_price" var="total_price_label"/>
<fmt:message bundle="${loc}" key="local.word.accept_status" var="accept_status_label"/>
<fmt:message bundle="${loc}" key="local.word.expired" var="expired_label"/>
<fmt:message bundle="${loc}" key="local.word.not_confirmed" var="not_confirmed_label"/>
<fmt:message bundle="${loc}" key="local.word.confirmed" var="confirmed_label"/>
<fmt:message bundle="${loc}" key="local.menu.current_contracts" var="current_contracts"/>
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
    <title>${current_contracts}</title>
</head>
<body onload="$('#table').stacktable();">
<div class="content">
    <%@include file="parts/header.jsp"%>
    <div class="container">
        <table id="table" class="tablesorter orders-table stacktable large-only">
            <thead>
            <tr>
                <th>${user_login_label}</th>
                <th>${hotelroom_number_label}</th>
                <th>${total_price_label}</th>
                <th>${date_in_label}</th>
                <th>${date_out_label}</th>
                <th>${accept_status_label}</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.contractsList}" var="contract">
                <c:if test="${role eq(admin) or (role eq(user) and userLogin eq(contract.accountLogin))}">
                    <tr>
                        <td>${contract.accountLogin}</td>
                        <td>${contract.hotelroomNumber}</td>
                        <td>${contract.totalPrice}</td>
                        <td>${contract.dateIn}</td>
                        <td>${contract.dateOut}</td>
                        <td>
                            <c:choose>
                                <c:when test="${currentDate lt contract.dateOut}">
                                    <c:choose>
                                        <c:when test="${contract.acceptStatus == false}">${not_confirmed_label}</c:when>
                                        <c:when test="${contract.acceptStatus == true}">${confirmed_label}</c:when>
                                        <c:otherwise>ERROR</c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:otherwise>
                                    ${expired_label}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <c:choose>
                            <c:when test="${role eq(admin)}">
                                <td>
                                    <form action="hotelrooming" method="post">
                                        <input type="hidden" name="command" value="cancel_contract">
                                        <input type="hidden" name="contract_id" value="${contract.id}">
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