<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="application.*" %>
<%@ page import="presentation.Controller" %>
<%@ page import="java.util.List" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null || user.getPermissionlevel() != PermissionLevel.Customer) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
    List<ItemDTO> items = Controller.getAllItems();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Webbshop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <h1>Products</h1>
    <a href="cart.jsp">Show cart</a>
    <% for (ItemDTO item : items) { %>
        <div>
            <h2><%= item.getName() %></h2>
            <p>Pris: <%= item.getPrice() %> kr</p>
            <form method="post" action="shop.jsp">
                <input type="hidden" name="itemid" value="<%= item.getItemId() %>">
                <button type="submit">Lägg i korgen</button>
            </form>
        </div>
    <% } %>
</body>
</html>