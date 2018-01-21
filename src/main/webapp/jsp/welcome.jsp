<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set value="alert" var="alert"/>
<c:set var="command" value="${command}"/>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="/styles/bootstrap.css" type="text/css"/>
    <link rel="stylesheet" href="/styles/style.css" type="text/css"/>
    <link rel="stylesheet" href="/styles/headerstyle.css" type="text/css"/>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" pageEncoding="utf-8">
    <title>Hotelrooming</title>
    <fmt:setLocale scope="session" value="${sessionScope.welcomeLocale}"/>
    <fmt:setLocale scope="request" value="${welcomeLocale}"/>
    <fmt:setBundle basename="by.tr.hotelbooking.localization.front-end" scope="session" var="loc"/>
    <fmt:message bundle="${loc}" key="local.word.signIn" var="word_sign_in"/>
    <fmt:message bundle="${loc}" key="local.word.signUp" var="word_sign_up"/>
    <fmt:message bundle="${loc}" key="local.word.sign_in_on_website" var="sign_in_on_website"/>
    <fmt:message bundle="${loc}" key="local.word.lastname" var="lastname"/>
    <fmt:message bundle="${loc}" key="local.word.name" var="name"/>
    <fmt:message bundle="${loc}" key="local.word.email" var="email"/>
    <fmt:message bundle="${loc}" key="local.word.pickUsername" var="pick_username"/>
    <fmt:message bundle="${loc}" key="local.word.createPassword" var="create_password"/>
    <fmt:message bundle="${loc}" key="local.word.sign_up_on_website" var="sign_up_on_website"/>
    <fmt:message bundle="${loc}" key="local.word.pass_reset" var="pass_reset"/>
    <fmt:message bundle="${loc}" key="local.word.sign_in_label" var="in_label"/>
    <fmt:message bundle="${loc}" key="local.word.sign_up_label" var="up_label"/>
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>

</head>
<body>
<div class="content">
    <div class="container">
        <%@include file="parts/welcome-header.jsp"%>
        <div class="form">
            <ul class="tab-group">
                <li class="tab active"><a href="#login">${word_sign_in}</a></li>
                <li class="tab"><a href="#signup">${word_sign_up}</a></li>

            </ul>
            <div class="tab-content">
                <div id="login">
                    <h1>${in_label}</h1>
                    <form action="/hotelrooming" method="post" accept-charset="utf-8">
                        <input type="hidden" name="command" value="sign_in"/>
                        <div class="field-wrap">
                            <label>
                                ${pick_username}<span class="req">*</span>
                            </label>
                            <input type="text" name="login" pattern="^[A-Za-z]\w{4,}$" title="Начинается с A-Z или a-z, только цифры и латинские буквы. Длина не менее 5 символов" required/>
                        </div>
                        <div class="field-wrap">
                            <label>
                                ${create_password}<span class="req">*</span>
                            </label>
                            <input type="password" name="password" pattern="^[A-Za-z]\w{4,}$" title="Начинается с A-Z или a-z, только цифры и латинские буквы. Длина не менее 5 символов" required/>
                        </div>
                        <p class="error-message">${requestScope.information}</p>
                        <button type="submit" class="button button-block">${sign_in_on_website}</button>
                    </form>
                </div>
                <div id="signup">
                    <h1>${up_label}</h1>
                    <form action="/hotelrooming" method="post" accept-charset="utf-8">
                        <input type="hidden" name="command" value="sign_up"/>
                        <div class="top-row">
                            <div class="field-wrap">
                                <label>
                                    ${name}<span class="req">*</span>
                                </label>
                                <input type="text" name="name" pattern="^[A-Za-z]{1,}|[А-Яа-я]{1,}$" title="Только буквы A-Za-z или А-Яа-я" required/>
                            </div>
                            <div class="field-wrap">
                                <label>
                                    ${lastname}<span class="req">*</span>
                                </label>
                                <input type="text" name="lastname" pattern="^[A-Za-z]{1,}|[А-Яа-я]{1,}$" title="Только буквы A-Za-z или А-Яа-я" required/>
                            </div>
                        </div>
                        <div class="field-wrap">
                            <label>
                                ${pick_username}<span class="req">*</span>
                            </label>
                            <input type="text" name="login" pattern="^[A-Za-z]\w{4,}$" title="Начинается с A-Z или a-z, только цифры и латинские буквы. Длина не менее 5 символов" required/>
                        </div>
                        <div class="field-wrap">
                            <label>
                                ${email}<span class="req">*</span>
                            </label>
                            <input type="email" name="email" required/>
                        </div>
                        <div class="field-wrap">
                            <label>
                                ${create_password}<span class="req" >*</span>
                            </label>
                            <input type="password" name="password" pattern="^[A-Za-z]\w{4,}$" title="Начинается с A-Z или a-z, только цифры и латинские буквы. Длина не менее 5 символов" required/>
                        </div>
                        <p class="error-message">${requestScope.information}</p>
                        <button type="submit" class="button button-block">${sign_up_on_website}</button>
                    </form>
                </div>
            </div><!-- tab-content -->
        </div> <!-- /form -->

    </div>

</div>
<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
<script  src="/js/index.js"></script>
</body>

</html>