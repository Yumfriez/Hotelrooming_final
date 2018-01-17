<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale scope="session" value="${sessionScope.userLocale}"/>
<fmt:setBundle basename="by.tr.hotelbooking.localization.front-end" scope="session" var="loc"/>
<fmt:message bundle="${loc}" key="local.menu.show_hotelrooms" var="show_hotelrooms"/>
<fmt:message bundle="${loc}" key="local.menu.make_order" var="make_order"/>
<fmt:message bundle="${loc}" key="local.menu.order_status" var="order_status"/>
<fmt:message bundle="${loc}" key="local.menu.current_contracts" var="current_contracts"/>
<fmt:message bundle="${loc}" key="local.menu.log_out" var="log_out"/>

        <div class="col-md-4">
            <div class="list-group">
                <form action="/hotelrooming" method="post" accept-charset="UTF-8">
                    <input type="hidden" name="command" value="show_hotelrooms"/>
                    <input type="submit" class="list-group-item" value="${show_hotelrooms}"/>
                </form>
                <form action="/hotelrooming" method="post" accept-charset="UTF-8">
                    <input type="hidden" name="command" value="show_page_for_make_order"/>
                    <input type="submit" class="list-group-item" value="${make_order}"/>
                </form>
                <form action="/hotelrooming" method="post" accept-charset="UTF-8">
                    <input type="hidden" name="command" value="order_status"/>
                    <input type="submit" class="list-group-item" value="${order_status}"/>
                </form>
                <form action="/hotelrooming" method="post" accept-charset="UTF-8">
                    <input type="hidden" name="command" value="current_contracts"/>
                    <input type="submit" class="list-group-item" value="${current_contracts}"/>
                </form>
                <form action="/hotelrooming" method="post" accept-charset="UTF-8">
                    <input type="hidden" name="command" value="exit"/>
                    <input type="submit" class="list-group-item" value="${log_out}"/>
                </form>
            </div>
        </div>

