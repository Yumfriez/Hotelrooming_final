<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale scope="session" value="${sessionScope.userLocale}"/>
<fmt:setBundle basename="by.tr.hotelbooking.localization.front-end" scope="session" var="loc"/>
<fmt:message bundle="${loc}" key="local.menu.show_hotelrooms" var="show_hotelrooms_label"/>
<fmt:message bundle="${loc}" key="local.menu.make_order" var="make_order_label"/>
<fmt:message bundle="${loc}" key="local.menu.sent_orders" var="sent_orders_label"/>
<fmt:message bundle="${loc}" key="local.menu.current_contracts" var="current_contracts_label"/>
<fmt:message bundle="${loc}" key="local.menu.new_offers" var="new_offers_label"/>
<fmt:message bundle="${loc}" key="local.menu.log_out" var="log_out_label"/>

        <div class="col-md-4">
            <div class="list-group">
                <form action="hotelrooming" method="post" accept-charset="UTF-8">
                    <input type="hidden" name="command" value="show_hotelrooms"/>
                    <input type="submit" class="list-group-item" value="${show_hotelrooms_label}"/>
                </form>
                <form action="hotelrooming" method="post" accept-charset="UTF-8">
                    <input type="hidden" name="command" value="show_page_for_make_order"/>
                    <input type="submit" class="list-group-item" value="${make_order_label}"/>
                </form>
                <form action="hotelrooming" method="post" accept-charset="UTF-8">
                    <input type="hidden" name="command" value="show_orders"/>
                    <input type="submit" class="list-group-item" value="${sent_orders_label}"/>
                </form>
                <form action="hotelrooming" method="post" accept-charset="UTF-8">
                    <input type="hidden" name="command" value="show_new_offers"/>
                    <input type="submit" class="list-group-item" value="${new_offers_label}"/>
                </form>
                <form action="hotelrooming" method="post" accept-charset="UTF-8">
                    <input type="hidden" name="command" value="show_user_contracts"/>
                    <input type="submit" class="list-group-item" value="${current_contracts_label}"/>
                </form>
                <form action="hotelrooming" method="post" accept-charset="UTF-8">
                    <input type="hidden" name="command" value="exit"/>
                    <input type="submit" class="list-group-item" value="${log_out_label}"/>
                </form>
            </div>
        </div>

