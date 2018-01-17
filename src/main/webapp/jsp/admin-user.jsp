<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="role" value="${sessionScope.role}"/>
<c:set var="admin" value="ADMINISTRATOR"/>
<c:set var="user" value="USER"/>
<c:set value="alert" var="alert"/>
<c:set var="command" value="${command}"/>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="../styles/bootstrap.css" type="text/css"/>
    <link rel="stylesheet" href="../styles/style.css" type="text/css"/>
    <link rel="stylesheet" href="../styles/headerstyle.css" type="text/css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" pageEncoding="utf-8">
    <fmt:setLocale scope="session" value="${sessionScope.userLocale}"/>
    <fmt:setBundle basename="by.tr.hotelbooking.localization.front-end" scope="session" var="loc"/>
    <fmt:message bundle="${loc}" key="local.menu.accounts_control" var="accounts_control"/>
    <fmt:message bundle="${loc}" key="local.menu.new_orders" var="new_orders"/>
    <fmt:message bundle="${loc}" key="local.menu.current_contracts" var="current_contracts"/>
    <fmt:message bundle="${loc}" key="local.menu.hotelrooms_control" var="hotelrooms_control"/>
    <fmt:message bundle="${loc}" key="local.menu.log_out" var="log_out"/>
   <c:choose>
       <c:when test="${role eq(admin)}">
           <title>administrator</title>
       </c:when>
       <c:when test="${role eq(user)}">
           <title>user</title>
       </c:when>
       <c:otherwise>
           ERROR
       </c:otherwise>
   </c:choose>
</head>
<body>

<div class="content">
    <%@include file="parts/header.jsp"%>
        <div class="container">
            <div class="row">
    <c:choose>
        <c:when test="${role eq(admin)}">
            <%@include file="parts/admin-menu.jsp"%>
        </c:when>
        <c:when test="${role eq(user)}">
            <%@include file="parts/user-menu.jsp"%>
        </c:when>
        <c:otherwise>
            ERROR
        </c:otherwise>
    </c:choose>
            </div>
        </div>

</div>

</body>
</html>