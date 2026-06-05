<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error - FoodDelivery</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="navbar.jsp" %>
<div class="container text-center">
    <h1>Oops! Something went wrong.</h1>
    <p>The page you are looking for could not be found or an error occurred.</p>
    <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-primary">Go Home</a>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>
