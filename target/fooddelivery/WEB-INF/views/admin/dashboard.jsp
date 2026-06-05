<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard - FoodDelivery</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="../navbar.jsp" %>
<div class="container">
    <h2>Admin Dashboard</h2>
    <div class="stat-grid">
        <div class="stat-card">
            <h3>${userCount}</h3>
            <p>Total Users</p>
            <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-sm btn-outline">View</a>
        </div>
        <div class="stat-card">
            <h3>${restaurantCount}</h3>
            <p>Restaurants</p>
            <a href="${pageContext.request.contextPath}/admin/restaurants" class="btn btn-sm btn-outline">View</a>
        </div>
        <div class="stat-card">
            <h3>${orderCount}</h3>
            <p>Total Orders</p>
            <a href="${pageContext.request.contextPath}/admin/orders" class="btn btn-sm btn-outline">View</a>
        </div>
    </div>

    <h3>Recent Orders</h3>
    <table class="table">
        <thead>
            <tr><th>#</th><th>User</th><th>Restaurant</th><th>Amount</th><th>Status</th><th>Action</th></tr>
        </thead>
        <tbody>
        <c:forEach var="o" items="${recentOrders}">
            <tr>
                <td>${o.id}</td>
                <td>${o.userName}</td>
                <td>${o.restaurantName}</td>
                <td>&#8377;${o.totalAmount}</td>
                <td><span class="badge status-${o.status.toLowerCase()}">${o.status}</span></td>
                <td>
                    <a href="${pageContext.request.contextPath}/admin/orderDetail?id=${o.id}"
                       class="btn btn-sm btn-outline">Detail</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%@ include file="../footer.jsp" %>
</body>
</html>
