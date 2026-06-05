<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Profile - FoodDelivery</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="navbar.jsp" %>
<div class="container">
    <div class="form-card">
        <h2>My Profile</h2>
        <c:if test="${not empty param.msg and param.msg == 'updated'}">
            <div class="alert alert-success">Profile updated successfully!</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        <form action="${pageContext.request.contextPath}/user" method="post">
            <input type="hidden" name="action" value="update">
            <div class="form-group">
                <label>Full Name</label>
                <input type="text" name="name" class="form-control"
                       value="${sessionScope.user.name}" required>
            </div>
            <div class="form-group">
                <label>Email (cannot change)</label>
                <input type="email" class="form-control" value="${sessionScope.user.email}" disabled>
            </div>
            <div class="form-group">
                <label>Phone</label>
                <input type="text" name="phone" class="form-control"
                       value="${sessionScope.user.phone}">
            </div>
            <div class="form-group">
                <label>Default Delivery Address</label>
                <textarea name="address" class="form-control" rows="3">${sessionScope.user.address}</textarea>
            </div>
            <button type="submit" class="btn btn-primary btn-block">Update Profile</button>
        </form>
    </div>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>
