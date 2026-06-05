<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Restaurants - FoodDelivery</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="navbar.jsp" %>
<div class="container">
    <h2>Restaurants</h2>

    <!-- Search -->
    <form action="${pageContext.request.contextPath}/restaurant" method="get" class="search-form">
        <input type="hidden" name="action" value="search">
        <input type="text" name="keyword" class="form-control" placeholder="Search by name or cuisine..."
               value="${keyword}">
        <button type="submit" class="btn btn-primary">Search</button>
    </form>

    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <div class="card-grid">
        <c:forEach var="r" items="${restaurants}">
            <div class="card">
                <div class="card-body">
                    <h3>${r.name}</h3>
                    <p class="text-muted">${r.cuisine}</p>
                    <p>${r.address}</p>
                    <p>📞 ${r.phone}</p>
                    <p>⭐ ${r.rating}</p>
                    <a href="${pageContext.request.contextPath}/restaurant?action=menu&id=${r.id}"
                       class="btn btn-primary btn-sm">View Menu</a>
                </div>
            </div>
        </c:forEach>
        <c:if test="${empty restaurants}">
            <p>No restaurants found.</p>
        </c:if>
    </div>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>
