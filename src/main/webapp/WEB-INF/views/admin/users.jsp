<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Users - Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="../navbar.jsp" %>
<div class="container">
    <h2>All Users</h2>
    <table class="table">
        <thead>
            <tr><th>#</th><th>Name</th><th>Email</th><th>Phone</th><th>Role</th></tr>
        </thead>
        <tbody>
        <c:forEach var="u" items="${users}">
            <tr>
                <td>${u.id}</td>
                <td>${u.name}</td>
                <td>${u.email}</td>
                <td>${u.phone}</td>
                <td><span class="badge">${u.role}</span></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%@ include file="../footer.jsp" %>
</body>
</html>
