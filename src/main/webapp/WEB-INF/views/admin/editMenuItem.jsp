<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Menu Item - Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="../navbar.jsp" %>
<div class="container">
    <div class="form-card">
        <h2>Edit Menu Item</h2>
        <form action="${pageContext.request.contextPath}/admin/saveMenuItem" method="post">
            <input type="hidden" name="id"           value="${menuItem.id}">
            <input type="hidden" name="restaurantId" value="${menuItem.restaurantId}">
            <div class="form-group">
                <label>Item Name</label>
                <input type="text" name="name" class="form-control" value="${menuItem.name}" required>
            </div>
            <div class="form-group">
                <label>Description</label>
                <textarea name="description" class="form-control" rows="2">${menuItem.description}</textarea>
            </div>
            <div class="form-group">
                <label>Price (&#8377;)</label>
                <input type="number" name="price" class="form-control" step="0.01"
                       min="0" value="${menuItem.price}" required>
            </div>
            <div class="form-group">
                <label>Category</label>
                <input type="text" name="category" class="form-control" value="${menuItem.category}">
            </div>
            <button type="submit" class="btn btn-primary btn-block">Update Item</button>
        </form>
    </div>
</div>
<%@ include file="../footer.jsp" %>
</body>
</html>
