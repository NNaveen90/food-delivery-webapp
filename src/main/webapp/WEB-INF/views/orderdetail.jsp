<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Order #${order.id} - FoodDelivery</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="navbar.jsp" %>
<div class="container">
    <c:if test="${not empty param.msg and param.msg == 'placed'}">
        <div class="alert alert-success">🎉 Order placed successfully!</div>
    </c:if>
    <h2>Order #${order.id}</h2>
    <div class="detail-grid">
        <div>
            <p><strong>Restaurant:</strong> ${order.restaurantName}</p>
            <p><strong>Status:</strong> <span class="badge status-${order.status.toLowerCase()}">${order.status}</span></p>
            <p><strong>Delivery Address:</strong> ${order.deliveryAddress}</p>
            <p><strong>Ordered At:</strong> ${order.createdAt}</p>
        </div>
    </div>

    <h3>Items</h3>
    <table class="table">
        <thead>
            <tr><th>Item</th><th>Qty</th><th>Unit Price</th><th>Subtotal</th></tr>
        </thead>
        <tbody>
            <c:forEach var="item" items="${order.items}">
            <tr>
                <td>${item.menuItemName}</td>
                <td>${item.quantity}</td>
                <td>&#8377; <fmt:formatNumber value="${item.unitPrice}" pattern="#,##0.00"/></td>
                <td>&#8377; <fmt:formatNumber value="${item.subtotal}"  pattern="#,##0.00"/></td>
            </tr>
            </c:forEach>
        </tbody>
        <tfoot>
            <tr>
                <th colspan="3">Total</th>
                <th>&#8377; <fmt:formatNumber value="${order.totalAmount}" pattern="#,##0.00"/></th>
            </tr>
        </tfoot>
    </table>
    <a href="${pageContext.request.contextPath}/order?action=myorders" class="btn btn-outline">Back to Orders</a>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>
