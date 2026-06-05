<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Restaurant - Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="../navbar.jsp" %>
<div class="container">
    <div class="form-card">
        <h2>Add Restaurant</h2>
        <form action="${pageContext.request.contextPath}/admin/saveRestaurant" method="post">
            <div class="form-group">
                <label>Name</label>
                <input type="text" name="name" class="form-control" required>
            </div>
            <div class="form-group">
                <label>Address</label>
                <textarea name="address" class="form-control" rows="2" required></textarea>
            </div>
            <div class="form-group">
                <label>Phone</label>
                <input type="text" name="phone" class="form-control">
            </div>
            <div class="form-group">
                <label>Cuisine</label>
                <input type="text" name="cuisine" class="form-control" required>
            </div>
            <div class="form-group">
                <label>Rating (0.0 - 5.0)</label>
                <input type="number" name="rating" class="form-control" step="0.1" min="0" max="5" value="4.0">
            </div>
            <button type="submit" class="btn btn-primary btn-block">Save Restaurant</button>
            <a href="${pageContext.request.contextPath}/admin/restaurants" class="btn btn-outline btn-block">Cancel</a>
        </form>
    </div>
</div>
<%@ include file="../footer.jsp" %>
</body>
</html>
