<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<table>
    <tr>
        <c:if test="${currentPageNumber != 1}">
            <td>
                <form action="hotelrooming" method="post">
                    <input type="hidden" name="command" value="${command}"/>
                    <input type="hidden" name="pagination" value="${currentPageNumber - 1}"/>
                    <input type="hidden" name="page" value="user_page"/>
                    <input type="submit" value="Prev" class="other_button"/>
                </form>
            </td>
        </c:if>
        <c:forEach begin="1" end="${pages_count}" var="i">
            <c:choose>
                <c:when test="${currentPageNumber eq i}">
                    <td id="current">${i}</td>
                </c:when>
                <c:otherwise>
                    <td>
                        <form action="hotelrooming" method="post">
                            <input type="hidden" name="command" value="${command}"/>
                            <input type="hidden" name="pagination" value="${i}"/>
                            <input type="hidden" name="page" value="user_page"/>
                            <input type="submit" value="${i}" class="other_button"/>
                        </form>
                    </td>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:if test="${currentPageNumber lt pages_count}">
            <td>
                <form action="hotelrooming" method="post">
                    <input type="hidden" name="command" value="${command}"/>
                    <input type="hidden" name="pagination" value="${currentPageNumber + 1}"/>
                    <input type="hidden" name="page" value="user_page"/>
                    <input type="submit" value="Next" class="other_button"/>
                </form>
            </td>
        </c:if>
    </tr>
</table>