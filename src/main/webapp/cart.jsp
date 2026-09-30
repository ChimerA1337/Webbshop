<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Cart - Webshop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <h1>Cart</h1>
    <c:choose>
        <c:when test="${empty requestScope.items}">
            <p>Cart is empty.</p>
        </c:when>
        <c:otherwise>
            <c:forEach var="item" items="${requestScope.items}">
                <div>
                    <h2><c:out value="${item.name}"/></h2>
                    <p>Pris: <c:out value="${item.price}"/> kr</p>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>
    <c:if test="${not empty requestScope.items}">
        <form method="post" action="${pageContext.request.contextPath}/controller">
            <input type="hidden" name="action" value="placeOrder">
            <button type="submit">Place order</button>
        </form>
    </c:if>
    <c:if test="${sessionScope.user.permissionlevel == 'Customer'}">
        <form method="get" action="${pageContext.request.contextPath}/controller">
            <input type="hidden" name="action" value="shop">
            <button type="submit">Back</button>
        </form>
    </c:if>
</body>
</html>