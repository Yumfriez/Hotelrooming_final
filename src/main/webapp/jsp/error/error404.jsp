<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale scope="session" value="${sessionScope.userLocale}"/>
<c:if test="${empty sessionScope.userLocale}">
    <fmt:setLocale scope="session" value="${sessionScope.welcomeLocale}"/>
</c:if>
<fmt:setBundle basename="by.tr.hotelbooking.localization.exception" scope="session" var="loc"/>
<fmt:message bundle="${loc}" key="404" var="error_message"/>
<html>
<head>
    <title>Error Page</title>
    <link href="../../styles/error.css" rel="stylesheet" type="text/css">
</head>
<body>
<h1> Error</h1>
Request from ${pageContext.errorData.requestURI} is failed
<br/>
Servlet name: ${pageContext.errorData.servletName}
<br/>
Status code: ${pageContext.errorData.statusCode}
<br>
Error message: ${error_message}.
<br>
<form action ="/hotelrooming" method="post">
    <input type="hidden" name ="command" value="redirect"/>
    <input type="submit" value="Home"/>
</form>
</body>
</html>
