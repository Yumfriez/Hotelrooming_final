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
Error message: ${pageContext.errorData.throwable}
<br>
<form action ="/hotelrooming" method="post">
    <input type="hidden" name ="command" value="redirect"/>
    <input type="submit" value="Home"/>
</form>
</body>
</html>
