<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Menu Item - Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="../navbar.jsp" %>
<div class="container">
    <div class="form-card">
        <h2>Add Menu Item</h2>
        <form action="${pageContext.request.contextPath}/admin/saveMenuItem" method="post">
            <input type="hidden" name="restaurantId" value="${restaurantId}">
            <div class="form-group">
                <label>Item Name</label>
                <input type="text" name="name" class="form-control" required>
            </div>
            <div class="form-group">
                <label>Description</label>
                <textarea name="description" class="form-control" rows="2"></textarea>
            </div>
            <div class="form-group">
                <label>Price (&#8377;)</label>
                <input type="number" name="price" class="form-control" step="0.01" min="0" required>
            </div>
            <div class="form-group">
                <label>Category</label>
                <input type="text" name="category" class="form-control" placeholder="e.g. Starter, Main Course">
            </div>
            <button type="submit" class="btn btn-primary btn-block">Save Item</button>
        </form>
    </div>
</div>
<%@ include file="../footer.jsp" %>
</body>
</html>
