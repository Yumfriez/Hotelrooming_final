<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Redirect</title>
</head>


<script type="text/JavaScript">
    function doRedirect() {
        atTime = "100";
        toUrl = "url";
        setTimeout(document.getElementById("redirect").click(), atTime);
    }
</script>
<body onload="doRedirect();">
Loading...
<form class="myform" action="/hotelrooming" method="post">
    <input type="hidden" name="information" value="${requestScope.information}"/>
    <input type="hidden" name="command" value="redirect"/>
    <input id="redirect" style="visibility:hidden;" type="submit"/>
</form>
</body>
</html>