<%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <div class="col-md-4">
            <div class="list-group">
                <form action="/hotelrooming" method="get" accept-charset="UTF-8">
                    <input type="hidden" name="command" value="show_users"/>
                    <input type="submit" class="list-group-item" value="${accounts_control}"/>
                </form>
                <form action="/hotelrooming" method="get" accept-charset="UTF-8">
                    <input type="hidden" name="command" value="new_orders"/>
                    <input type="submit" class="list-group-item" value="${new_orders}"/>
                </form>
                <form action="/hotelrooming" method="get" accept-charset="UTF-8">
                    <input type="hidden" name="command" value="current_contracts"/>
                    <input type="submit" class="list-group-item" value="${current_contracts}"/>
                </form>
                <form action="/hotelrooming" method="get" accept-charset="UTF-8">
                    <input type="hidden" name="command" value="show_hotelrooms"/>
                    <input type="hidden" name="pagination" value="1"/>
                    <input type="submit" class="list-group-item" value="${hotelrooms_control}"/>
                </form>
                <form action="/hotelrooming" method="get" accept-charset="UTF-8">
                    <input type="hidden" name="command" value="exit"/>
                    <input type="submit" class="list-group-item" value="${log_out}"/>
                </form>
            </div>
        </div>

