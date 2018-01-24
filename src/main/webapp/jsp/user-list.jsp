<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="user" value="user"/>
<c:set var="command" value="${requestScope.command}"/>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" pageEncoding="utf-8">
    <fmt:setLocale scope="session" value="${sessionScope.userLocale}"/>
    <fmt:setBundle basename="by.tr.hotelbooking.localization.front-end" scope="session" var="loc"/>
    <fmt:message bundle="${loc}" key="local.word.login" var="login_word"/>
    <fmt:message bundle="${loc}" key="local.word.name" var="name_word"/>
    <fmt:message bundle="${loc}" key="local.word.lastname" var="lastname_word"/>
    <fmt:message bundle="${loc}" key="local.word.email" var="email_word"/>
    <fmt:message bundle="${loc}" key="local.word.isblocked" var="isblocked_word"/>
    <fmt:message bundle="${loc}" key="local.menu.accounts_control" var="accounts_control"/>
    <fmt:message bundle="${loc}" key="local.menu.new_orders" var="new_orders"/>
    <fmt:message bundle="${loc}" key="local.menu.current_contracts" var="current_contracts"/>
    <fmt:message bundle="${loc}" key="local.menu.hotelrooms_control" var="hotelrooms_control"/>
    <fmt:message bundle="${loc}" key="local.menu.log_out" var="log_out"/>
    <link rel="stylesheet" href="../styles/bootstrap.css" type="text/css"/>
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
            <%@include file="parts/admin-menu.jsp"%>
            <div class="col-md-16 products">
                <div class="row">
                    <div class="col-sm-14">
                        <div class="users">
                            <table id="users_table" class="table table-striped table-bordered" cellspacing="0" width="100%">
                                <thead>
                                    <tr>
                                        <th>${login_word}</th>
                                        <th>${name_word}</th>
                                        <th>${lastname_word}</th>
                                        <th>${email_word}</th>
                                        <th>${isblocked_word}</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${requestScope.userList}" var="user">
                                        <tr>
                                            <td>${user.login}</td>
                                        <td>${user.name}</td>
                                        <td>${user.surname}</td>
                                        <td>${user.email}</td>
                                        <td>${user.isBlocked}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
        <script>
            $(document).ready( function () {

                $('#users_table').DataTable({
                    "iDisplayLength": 7,
                    "bFilter": false,
                    "lengthChange": false
                })
            } );
        </script>
</body>
</html>