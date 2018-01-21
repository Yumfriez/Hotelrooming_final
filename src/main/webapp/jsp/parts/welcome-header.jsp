<fmt:setBundle basename="by.tr.hotelbooking.localization.front-end" scope="session" var="loc"/>
<fmt:message bundle="${loc}" key="local.button.name.en" var="en_button"/>
<fmt:message bundle="${loc}" key="local.button.name.ru" var="ru_button"/>
<fmt:message bundle="${loc}" key="local.button.homepage" var="homepage"/>
<fmt:message bundle="${loc}" key="local.button.comments" var="comments"/>
<nav class="navbar">
    <div class="container">
        <div class="navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav">
                <li>
                    <form action="/hotelrooming" method="post">
                        <input type="hidden" name="command" value="redirect"/>
                        <input type="submit" value="${homepage}"/>
                    </form>
                </li>
                <li>
                    <form action="/hotelrooming" method="post">
                        <input type="hidden" name="command" value="show_comments"/>
                        <input type="submit" value="${comments}"/>
                    </form>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <form action="/hotelrooming" method="post">
                        <input class="lang_ru" type="hidden" name="command" value="change_locale"/>
                        <input class="lang_ru" type="hidden" name="locale" value="ru"/>
                        <input type="hidden" name="page" value="welcome_page"/>
                        <input class="lang_ru" type="submit" value="${ru_button}"/>
                    </form>
                </li>

                <li>
                    <form action="/hotelrooming" method="post">
                        <input class="lang_en" type="hidden" name="command" value="change_locale"/>
                        <input class="lang_en" type="hidden" name="locale" value="en"/>
                        <input type="hidden" name="page" value="welcome_page"/>
                        <input class="lang_en" type="submit" value="${en_button}"/>
                    </form>
                </li>
            </ul>
        </div>
    </div>
</nav>
