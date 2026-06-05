<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar">
    <div class="nav-brand">
        <a href="${pageContext.request.contextPath}/index.jsp">🍽️ FoodDelivery</a>
    </div>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/restaurant?action=list">Restaurants</a>
        <c:choose>
            <c:when test="${not empty sessionScope.user}">
                <a href="${pageContext.request.contextPath}/order?action=myorders">My Orders</a>
                <a href="${pageContext.request.contextPath}/user?action=profile">${sessionScope.user.name}</a>
                <c:if test="${sessionScope.role == 'ADMIN'}">
                    <a href="${pageContext.request.contextPath}/admin/dashboard">Admin</a>
                </c:if>
                <a href="${pageContext.request.contextPath}/user?action=logout" class="btn btn-sm">Logout</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/user?action=login">Login</a>
                <a href="${pageContext.request.contextPath}/user?action=register" class="btn btn-sm">Register</a>
            </c:otherwise>
        </c:choose>
    </div>
</nav>
