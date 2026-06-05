<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Restaurant - Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="../navbar.jsp" %>
<div class="container">
    <div class="form-card">
        <h2>Edit Restaurant</h2>
        <form action="${pageContext.request.contextPath}/admin/saveRestaurant" method="post">
            <input type="hidden" name="id" value="${restaurant.id}">
            <div class="form-group">
                <label>Name</label>
                <input type="text" name="name" class="form-control" value="${restaurant.name}" required>
            </div>
            <div class="form-group">
                <label>Address</label>
                <textarea name="address" class="form-control" rows="2" required>${restaurant.address}</textarea>
            </div>
            <div class="form-group">
                <label>Phone</label>
                <input type="text" name="phone" class="form-control" value="${restaurant.phone}">
            </div>
            <div class="form-group">
                <label>Cuisine</label>
                <input type="text" name="cuisine" class="form-control" value="${restaurant.cuisine}" required>
            </div>
            <div class="form-group">
                <label>Rating</label>
                <input type="number" name="rating" class="form-control" step="0.1"
                       min="0" max="5" value="${restaurant.rating}">
            </div>
            <button type="submit" class="btn btn-primary btn-block">Update Restaurant</button>
            <a href="${pageContext.request.contextPath}/admin/restaurants" class="btn btn-outline btn-block">Cancel</a>
        </form>
    </div>
</div>
<%@ include file="../footer.jsp" %>
</body>
</html>
