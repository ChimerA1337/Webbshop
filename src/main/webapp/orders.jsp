<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Menu - Webshop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script src="https://kit.fontawesome.com/dd581d5599.js" crossorigin="anonymous"></script>
</head>
<body>
    <c:if test="${not empty requestScope.message}">
        <p><c:out value="${requestScope.message}"/></p>
    </c:if>
    <h1>Items List</h1>
    <table>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Stock</th>
            <th>Restock</th>
        </tr>
        <c:forEach var="item" items="${requestScope.items}">
            <tr>
                <td><c:out value="${item.itemid}"/></td>
                <td><c:out value="${item.name}"/></td>
                <td><c:out value="${item.amount}"/></td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="action" value="restockItem">
                        <input type="hidden" name="itemid" value="${item.itemid}">
                        <input type="number" name="amount" min="1" step="1" value="1" required>
                        <button type="submit">Restock</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

    <h1>Orders List</h1>
    <table>
        <tr>
            <th>Order ID</th>
            <th>User ID</th>
            <th>Ordered</th>
            <th>Packed</th>
            <th>Pack order</th>
        </tr>
        <c:forEach var="order" items="${requestScope.orders}">
            <tr>
                <td><c:out value="${order.orderid}"/></td>
                <td><c:out value="${order.userid}"/></td>
                <td><c:out value="${order.ordered}"/></td>
                <td>
                    <c:choose>
                        <c:when test="${empty order.packed}">
                            Not packed
                        </c:when>
                        <c:otherwise>
                            <c:out value="${order.packed}"/>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:if test="${empty order.packed}">
                        <form method="post" action="${pageContext.request.contextPath}/controller">
                            <input type="hidden" name="action" value="packOrder">
                            <input type="hidden" name="orderid" value="${order.orderid}">
                            <button type="submit">Pack order</button>
                        </form>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>

    <c:if test="${empty requestScope.orders}">
        <p>No orders found.</p>
    </c:if>
</body>
</html>