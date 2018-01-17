<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale scope="session" value="${sessionScope.userLocale}"/>
<c:set var="role" value="${sessionScope.role}"/>
<c:set var="admin" value="ADMINISTRATOR"/>
<c:set var="user" value="USER"/>
<fmt:setBundle basename="by.tr.hotelbooking.localization.front-end" scope="session" var="loc"/>
<fmt:message bundle="${loc}" key="local.word.places_count" var="places_count"/>
<fmt:message bundle="${loc}" key="local.word.min_daily_price" var="min_daily_price"/>
<fmt:message bundle="${loc}" key="local.word.max_daily_price" var="max_daily_price"/>
<fmt:message bundle="${loc}" key="local.word.room_type" var="room_type_word"/>
<fmt:message bundle="${loc}" key="local.word.parameters_label" var="parameters_label"/>
<fmt:message bundle="${loc}" key="local.word.make_order_label" var="make_order_label"/>
<fmt:message bundle="${loc}" key="local.button.make_order_button" var="make_order_button"/>
<c:set var="command" value="${command}"/>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="/styles/bootstrap.css" type="text/css"/>
    <link rel="stylesheet" href="/styles/style.css" type="text/css"/>
    <link rel="stylesheet" href="/styles/headerstyle.css" type="text/css"/>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" pageEncoding="utf-8">
    <title>Hotelrooming</title>
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>

</head>
<body>
<div class="content">
    <div class="container header inner">
        <%@include file="parts/header.jsp"%>
        <div class="form">
            <div class="main_label">${make_order_label}</div>
            <div class="inputs-wrapper">
                <h1>${parameters_label}</h1>
                <form action="/hotelrooming" method="post" accept-charset="utf-8">
                    <input type="hidden" name="command" value="make_order"/><br/>
                    <div class="field-wrap">
                        <label>
                            ${places_count}<span class="req">*</span>
                        </label>
                        <input type="text" name="places" title="Только цифры (1-9)" pattern="[1-9]{1}" required/>
                    </div>
                    <div class="field-wrap">
                        <label>
                            ${min_daily_price}<span class="req">*</span>
                        </label>
                        <input type="text" name="min_price" title="Введите цену без посторонних символов" pattern="[0-9]+(\.[0-9]+)?" required/>
                    </div>
                    <div class="field-wrap">
                        <label>
                            ${max_daily_price}<span class="req">*</span>
                        </label>
                        <input type="text" name="max_price" title="Введите цену без посторонних символов" pattern="[0-9]+(\.[0-9]+)?" required/>
                    </div>
                    <div class="field-wrap">
                        <select name="room_type" required>
                            <c:forEach var="room_type" items="${requestScope.roomTypesList}">
                                <option value="${room_type.id}">${room_type.name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <button type="submit" class="button button-block">${make_order_button}</button>
                </form>
            </div>
        </div>

    </div>

</div>
<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
<script  src="/js/index.js"></script>
</body>

</html>