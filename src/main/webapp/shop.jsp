<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ page import="application.*" %>
<%@ page import="presentation.Controller" %>
<%@ page import="java.util.List" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null || user.getPermissionlevel() != PermissionLevel.Customer) {
        response.sendRedirect(request.getContextPath() + "/controller");
        return;
    }
    List<ItemDTO> items = Controller.getAllItems();
    request.setAttribute("items", items);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Webshop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <h1>Products</h1>
    <a href="${pageContext.request.contextPath}/controller?action=cart">Show cart</a>
    <c:if test="${empty requestScope.items}">
        <p>No products available.</p>
    </c:if>
    <c:forEach var="item" items="${requestScope.items}">
        <div>
            <h2><c:out value="${item.name}"/></h2>
            <p>Price: <c:out value="${item.price}"/> kr</p>
            <form method="post" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="action" value="addToCart">
                <input type="hidden" name="itemid" value="${item.itemId}">
                <button type="submit">Add to cart</button>
            </form>
        </div>
    </c:forEach>
    <form method="post" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="action" value="logout">
        <button type="submit">Log out</button>
    </form>
</body>
</html>