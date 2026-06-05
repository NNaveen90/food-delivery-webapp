<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Orders - Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="../navbar.jsp" %>
<div class="container">
    <h2>All Orders</h2>
    <table class="table">
        <thead>
            <tr><th>#</th><th>Customer</th><th>Restaurant</th><th>Amount</th><th>Status</th><th>Date</th><th>Update</th></tr>
        </thead>
        <tbody>
        <c:forEach var="o" items="${orders}">
            <tr>
                <td>${o.id}</td>
                <td>${o.userName}</td>
                <td>${o.restaurantName}</td>
                <td>&#8377; <fmt:formatNumber value="${o.totalAmount}" pattern="#,##0.00"/></td>
                <td><span class="badge status-${o.status.toLowerCase()}">${o.status}</span></td>
                <td>${o.createdAt}</td>
                <td>
                    <form action="${pageContext.request.contextPath}/admin/updateStatus" method="post"
                          style="display:flex;gap:4px">
                        <input type="hidden" name="orderId" value="${o.id}">
                        <select name="status" class="form-control form-control-sm">
                            <option value="PENDING"          ${o.status=='PENDING'          ?'selected':''}>PENDING</option>
                            <option value="CONFIRMED"        ${o.status=='CONFIRMED'        ?'selected':''}>CONFIRMED</option>
                            <option value="PREPARING"        ${o.status=='PREPARING'        ?'selected':''}>PREPARING</option>
                            <option value="OUT_FOR_DELIVERY" ${o.status=='OUT_FOR_DELIVERY' ?'selected':''}>OUT FOR DELIVERY</option>
                            <option value="DELIVERED"        ${o.status=='DELIVERED'        ?'selected':''}>DELIVERED</option>
                            <option value="CANCELLED"        ${o.status=='CANCELLED'        ?'selected':''}>CANCELLED</option>
                        </select>
                        <button type="submit" class="btn btn-sm btn-primary">Update</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%@ include file="../footer.jsp" %>
</body>
</html>
