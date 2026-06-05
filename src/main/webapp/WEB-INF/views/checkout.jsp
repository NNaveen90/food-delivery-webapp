<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Checkout - FoodDelivery</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="navbar.jsp" %>
<div class="container">
    <div class="form-card">
        <h2>Checkout</h2>
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <table class="table">
            <thead>
                <tr><th>Item</th><th>Qty</th><th>Price</th><th>Subtotal</th></tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${cartItems}">
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
                    <th>&#8377; <fmt:formatNumber value="${totalAmount}" pattern="#,##0.00"/></th>
                </tr>
            </tfoot>
        </table>

        <form action="${pageContext.request.contextPath}/order" method="post">
            <input type="hidden" name="action"       value="place">
            <input type="hidden" name="restaurantId" value="${restaurantId}">
            <div class="form-group">
                <label>Delivery Address</label>
                <textarea name="deliveryAddress" class="form-control" rows="3" required
                    placeholder="Full delivery address">${sessionScope.user.address}</textarea>
            </div>
            <button type="submit" class="btn btn-success btn-block">Place Order</button>
        </form>
    </div>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>
