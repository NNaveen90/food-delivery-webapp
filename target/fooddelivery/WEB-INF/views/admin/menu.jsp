<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Menu - Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="../navbar.jsp" %>
<div class="container">
    <div class="page-header">
        <h2>${restaurant.name} - Menu Items</h2>
        <a href="${pageContext.request.contextPath}/admin/addMenuItem?restaurantId=${restaurant.id}"
           class="btn btn-primary">+ Add Item</a>
    </div>
    <table class="table">
        <thead>
            <tr><th>#</th><th>Name</th><th>Category</th><th>Price</th><th>Actions</th></tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${menuItems}">
            <tr>
                <td>${item.id}</td>
                <td>${item.name}</td>
                <td>${item.category}</td>
                <td>&#8377; ${item.price}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/admin/editMenuItem?id=${item.id}"
                       class="btn btn-sm btn-primary">Edit</a>
                    <a href="${pageContext.request.contextPath}/admin/deleteMenuItem?id=${item.id}&restaurantId=${restaurant.id}"
                       class="btn btn-sm btn-danger"
                       onclick="return confirm('Remove this item?')">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a href="${pageContext.request.contextPath}/admin/restaurants" class="btn btn-outline">Back</a>
</div>
<%@ include file="../footer.jsp" %>
</body>
</html>
