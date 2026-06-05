<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>${restaurant.name} - Menu</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="navbar.jsp" %>
<div class="container">
    <h2>${restaurant.name}</h2>
    <p class="text-muted">${restaurant.cuisine} &bull; ⭐ ${restaurant.rating} &bull; ${restaurant.address}</p>

    <c:if test="${not empty sessionScope.cart}">
        <div class="cart-bar">
            🛒 ${sessionScope.cart.size()} item(s) in cart &nbsp;
            <a href="${pageContext.request.contextPath}/order?action=checkout&restaurantId=${restaurant.id}"
               class="btn btn-success btn-sm">Checkout</a>
            <form action="${pageContext.request.contextPath}/cart" method="post" style="display:inline">
                <input type="hidden" name="action" value="clear">
                <button type="submit" class="btn btn-sm btn-danger">Clear Cart</button>
            </form>
        </div>
    </c:if>

    <div class="card-grid">
        <c:forEach var="item" items="${menuItems}">
            <div class="card">
                <div class="card-body">
                    <h4>${item.name}</h4>
                    <p class="text-muted">${item.category}</p>
                    <p>${item.description}</p>
                    <p class="price">&#8377; ${item.price}</p>
                    <c:if test="${not empty sessionScope.user}">
                        <form action="${pageContext.request.contextPath}/cart" method="post">
                            <input type="hidden" name="action"       value="add">
                            <input type="hidden" name="menuItemId"   value="${item.id}">
                            <input type="hidden" name="restaurantId" value="${restaurant.id}">
                            <button type="submit" class="btn btn-primary btn-sm">Add to Cart</button>
                        </form>
                    </c:if>
                    <c:if test="${empty sessionScope.user}">
                        <a href="${pageContext.request.contextPath}/user?action=login"
                           class="btn btn-outline btn-sm">Login to Order</a>
                    </c:if>
                </div>
            </div>
        </c:forEach>
        <c:if test="${empty menuItems}">
            <p>No menu items available.</p>
        </c:if>
    </div>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>
