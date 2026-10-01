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
            <p class="darkGreenContainer">Cart is empty.</p>
        </c:when>
        <c:otherwise>
            <c:forEach var="line" items="${requestScope.items}">
                <div class="darkGreenContainer">
                    <h2><c:out value="${line.item.name}"/></h2>
                    <div class="lightGreenContainer">
                        <p>Pris: <c:out value="${line.item.price}"/> kr</p>
                        <p>Amount: <c:out value="${line.quantity}"/> item(s)</p>
                        <p>Total: <c:out value="${line.item.price * line.quantity}"/> kr</p>
                    </div>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>
    <c:if test="${not empty requestScope.items}">
        <form method="post" action="${pageContext.request.contextPath}/controller">
            <p>Order Total Cost: <c:out value="${requestScope.total}"/></p><br> <!-- works? -->
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