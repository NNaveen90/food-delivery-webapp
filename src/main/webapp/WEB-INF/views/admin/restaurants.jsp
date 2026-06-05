<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Restaurants - Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="../navbar.jsp" %>
<div class="container">
    <div class="page-header">
        <h2>Restaurants</h2>
        <a href="${pageContext.request.contextPath}/admin/addRestaurant" class="btn btn-primary">+ Add Restaurant</a>
    </div>
    <table class="table">
        <thead>
            <tr><th>#</th><th>Name</th><th>Cuisine</th><th>Address</th><th>Rating</th><th>Actions</th></tr>
        </thead>
        <tbody>
        <c:forEach var="r" items="${restaurants}">
            <tr>
                <td>${r.id}</td>
                <td>${r.name}</td>
                <td>${r.cuisine}</td>
                <td>${r.address}</td>
                <td>⭐ ${r.rating}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/admin/menu?restaurantId=${r.id}"
                       class="btn btn-sm btn-outline">Menu</a>
                    <a href="${pageContext.request.contextPath}/admin/editRestaurant?id=${r.id}"
                       class="btn btn-sm btn-primary">Edit</a>
                    <a href="${pageContext.request.contextPath}/admin/deleteRestaurant?id=${r.id}"
                       class="btn btn-sm btn-danger"
                       onclick="return confirm('Delete this restaurant?')">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%@ include file="../footer.jsp" %>
</body>
</html>
