<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>FoodDelivery - Home</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/views/navbar.jsp" %>

<div class="hero">
    <h1>🍽️ Welcome to FoodDelivery</h1>
    <p>Order from the best restaurants in your city</p>
    <a href="${pageContext.request.contextPath}/restaurant?action=list" class="btn btn-primary">
        Browse Restaurants
    </a>
</div>

<div class="container features">
    <div class="feature-card">
        <h3>🏪 Top Restaurants</h3>
        <p>Choose from a wide variety of cuisines</p>
    </div>
    <div class="feature-card">
        <h3>⚡ Fast Delivery</h3>
        <p>Hot food delivered to your doorstep</p>
    </div>
    <div class="feature-card">
        <h3>📱 Easy Ordering</h3>
        <p>Place orders in just a few clicks</p>
    </div>
</div>

<%@ include file="/WEB-INF/views/footer.jsp" %>
</body>
</html>
