<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale scope="session" value="${sessionScope.userLocale}"/>
<c:set var="role" value="${sessionScope.role}"/>
<c:set var="admin" value="ADMINISTRATOR"/>
<c:set var="user" value="USER"/>
<fmt:setBundle basename="by.tr.hotelbooking.localization.front-end" scope="session" var="loc"/>
<fmt:message bundle="${loc}" key="local.word.hotelnumber" var="hotelroom_number"/>
<fmt:message bundle="${loc}" key="local.word.places_count" var="places_count"/>
<fmt:message bundle="${loc}" key="local.word.floor" var="floor"/>
<fmt:message bundle="${loc}" key="local.word.daily_price" var="daily_price"/>
<fmt:message bundle="${loc}" key="local.word.room_type" var="room_type_word"/>
<fmt:message bundle="${loc}" key="local.word.parameters_label" var="parameters_label"/>
<fmt:message bundle="${loc}" key="local.word.edit_hotelroom_label" var="edit_hotelroom_word"/>
<fmt:message bundle="${loc}" key="local.word.old_value" var="old_value"/>
<fmt:message bundle="${loc}" key="local.button.edit_hotelroom_button" var="edit_hotelroom_button"/>
<c:set var="command" value="${command}"/>
<c:set var="hotelroom" value="${requestScope.hotelroom}"/>
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
            <div class="main_label">${edit_hotelroom_word}: ${hotelroom.number}</div>
            <div class="inputs-wrapper">
                <h1>${parameters_label}</h1>
                <form action="/hotelrooming" method="post" accept-charset="utf-8" enctype="multipart/form-data">
                    <input type="hidden" name="command" value="edit_hotelroom"/>
                    <input type="hidden" name="number" value="${hotelroom.number}">
                    <input type="hidden" name="hotelroom_id" value="${hotelroom.id}"><br/>
                    <div class="field-wrap">
                        <input type="text" name="places" value="${hotelroom.placesCount}" placeholder="${places_count}*" title="Только цифры (1-99)" pattern="[0-9]{1,2}" required/>
                    </div>
                    <div class="field-wrap">
                        <input type="text" name="price" value="${hotelroom.dailyPrice}" placeholder="${daily_price}*" title="Введите цену без посторонних символов" pattern="[0-9]+(\.[0-9]+)?" required/>
                    </div>
                    <div class="field-wrap">
                        <input type="text" name="floor" value="${hotelroom.floor}" placeholder="${floor}*" title="Только цифры (1-99)" pattern="[0-9]{1,2}" required/>
                    </div>
                    <div class="field-wrap">
                        <select name="room_type" required>
                            <c:forEach var="room_type" items="${requestScope.roomTypesList}">
                                <option value="${room_type.id}">${room_type.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="file-field-wrap button-3d">
                        <label id="image_label" for="files" >Select Image</label>
                        <input id="files"  type="file" name="room_image" accept="image/*" required/>
                    </div>


                    <button type="submit" class="button button-block">${edit_hotelroom_button}</button>
                </form>
            </div>
        </div>

    </div>

</div>
<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
<script  src="/js/index.js"></script>
</body>

</html>