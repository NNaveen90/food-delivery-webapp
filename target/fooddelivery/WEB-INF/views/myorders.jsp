<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>My Orders - FoodDelivery</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="navbar.jsp" %>
<div class="container">
    <h2>My Orders</h2>
    <c:if test="${empty orders}">
        <p>You have no orders yet. <a href="${pageContext.request.contextPath}/restaurant?action=list">Order now!</a></p>
    </c:if>
    <table class="table">
        <c:if test="${not empty orders}">
        <thead>
            <tr>
                <th>#</th><th>Restaurant</th><th>Amount</th>
                <th>Status</th><th>Date</th><th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="o" items="${orders}">
            <tr>
                <td>${o.id}</td>
                <td>${o.restaurantName}</td>
                <td>&#8377; <fmt:formatNumber value="${o.totalAmount}" pattern="#,##0.00"/></td>
                <td><span class="badge status-${o.status.toLowerCase()}">${o.status}</span></td>
                <td>${o.createdAt}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/order?action=detail&id=${o.id}"
                       class="btn btn-sm btn-outline">Detail</a>
                    <c:if test="${o.status == 'PENDING'}">
                        <a href="${pageContext.request.contextPath}/order?action=cancel&id=${o.id}"
                           class="btn btn-sm btn-danger"
                           onclick="return confirm('Cancel this order?')">Cancel</a>
                    </c:if>
                </td>
            </tr>
            </c:forEach>
        </tbody>
        </c:if>
    </table>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>
