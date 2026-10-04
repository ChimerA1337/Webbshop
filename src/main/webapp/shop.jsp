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
    List<Item> items = Controller.getAllItems();
    request.setAttribute("items", items);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Webshop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script src="https://kit.fontawesome.com/dd581d5599.js" crossorigin="anonymous"></script>
</head>
<body>
    <h1>Products</h1>
    <a href="${pageContext.request.contextPath}/controller?action=cart" type="link" class="link"><i class="fa-solid fa-cart-shopping"></i>Show Cart<i class="fa-solid fa-cart-shopping"></i></a>
    <c:if test="${empty requestScope.items}">
        <p>No products available.</p>
    </c:if>
    <c:forEach var="item" items="${requestScope.items}">
        <div>
            <form method="post" action="${pageContext.request.contextPath}/controller">
                <h2><c:out value="${item.name}"/></h2>
                <p>Price: <c:out value="${item.price}"/> kr</p>
                <p>Description: <c:out value="{item.description}"/></p>
                <p>Category: <c:out value="${item.category}"/></p>
                <p>In Stock: <c:out value="${item.amount}"/> items</p>
                <input type="hidden" name="action" value="addToCart">
                <input type="hidden" name="itemid" value="${item.itemid}">
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