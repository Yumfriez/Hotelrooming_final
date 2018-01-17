<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale scope="session" value="${sessionScope.userLocale}"/>
<fmt:setBundle basename="by.tr.hotelbooking.localization.front-end" scope="session" var="loc"/>
<fmt:message bundle="${loc}" key="local.button.homepage" var="homepage"/>
<fmt:message bundle="${loc}" key="local.button.comments" var="comments"/>
<fmt:message bundle="${loc}" key="local.button.settings" var="settings"/>
<fmt:message bundle="${loc}" key="local.button.name.ru" var="locale_ru"/>
<fmt:message bundle="${loc}" key="local.button.name.en" var="locale_en"/>
<nav class="navbar">
    <div class="container">
        <div class="navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav">
                <li>
                    <form action="/hotelrooming" method="post">
                        <input type="hidden" name="command" value="show_main_page"/>
                        <input type="submit" value="${homepage}"/>
                    </form>
                </li>
                <li>
                    <form action="/hotelrooming" method="post">
                        <input type="hidden" name="command" value="show_comments"/>
                        <input type="submit" value="${comments}"/>
                    </form>
                </li>
                <li>
                    <form action="/hotelrooming" method="post" accept-charset="UTF-8">
                        <input type="hidden" name="command" value="personal"/>
                        <input type="submit" value="${settings}"/>
                    </form>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <form action="/hotelrooming" method="post">
                        <input class="lang_ru" type="hidden" name="command" value="change_locale"/>
                        <input class="lang_ru" type="hidden" name="locale" value="ru"/>
                        <input type="hidden" name="page" value="admin_user_page"/>
                        <input class="lang_ru" type="submit" value="${locale_ru}"/>
                    </form>
                </li>
                <li>
                    <form action="/hotelrooming" method="post">
                        <input class="lang_en" type="hidden" name="command" value="change_locale"/>
                        <input class="lang_en" type="hidden" name="locale" value="en"/>
                        <input type="hidden" name="page" value="admin_user_page"/>
                        <input class="lang_en" type="submit" value="${locale_en}"/>
                    </form>
                </li>
            </ul>
        </div>
    </div>
</nav>